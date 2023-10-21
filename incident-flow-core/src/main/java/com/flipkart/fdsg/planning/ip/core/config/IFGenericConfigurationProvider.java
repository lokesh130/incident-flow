package com.flipkart.fdsg.planning.ip.core.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.configsvc.clients.ConfigurationProvider;
import com.flipkart.configsvc.clients.exception.InvalidConfigurationPathException;
import com.flipkart.fdsg.planning.ip.core.clients.IPConfigClient;
import com.flipkart.kloud.config.DynamicBucket;
import com.flipkart.kloud.config.error.ConfigServiceException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

import static com.flipkart.fdsg.planning.ip.core.Constants.*;

// Needed to create a similar class to GenericConfigurationProvider which has been already implemented by ConfigsvcClient
// because for decryption of the config bucket needed to use and override some the methods but these methods are declared private to the existing class.

@Slf4j
public class IFGenericConfigurationProvider implements ConfigurationProvider {

    private static final String EXT_JSON = ".json";
    private static final String CONFIG_SVC_URI_SCHEME = "config-svc";
    private static final int CONFIG_SVC_API_VERSION = 1;

    private final ObjectMapper jsonObjectMapper;
    private final ObjectMapper ymlObjectMapper;
    private char jsonFlattenSeparator;

    public IFGenericConfigurationProvider(char jsonFlattenSeparator, ObjectMapper jsonObjectMapper,
                                          ObjectMapper ymlObjectMapper) {
        this.jsonFlattenSeparator = jsonFlattenSeparator;
        this.jsonObjectMapper = jsonObjectMapper;
        this.ymlObjectMapper = ymlObjectMapper;
    }

    private static URL getConfigServiceUrl(String authority, String path) {
        try {
            String fullPath = String.format("/v%d/buckets%s", CONFIG_SVC_API_VERSION, path);
            URI httpURI = new URI("http", authority, fullPath, null, null);
            return httpURI.toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new IllegalStateException("This won't happen");
        }
    }

    @Override
    public InputStream open(String path) throws IOException {
        URI uri = URI.create(path);
        if (CONFIG_SVC_URI_SCHEME.equals(uri.getScheme())) {
            try {

                return readConfigurationFromIPConfigService(uri);
            } catch (ConfigServiceException e) {
                e.printStackTrace();
            }
        } else if (path.endsWith(EXT_JSON)) {
            final File file = new File(path);
            if (!file.exists()) {
                throw new FileNotFoundException("File " + file + " not found");
            }
            return new FileInputStream(file);
        }
        throw new InvalidConfigurationPathException(path);
    }
    private DynamicBucket readConfigurationFromFkConfigService(URI uri) throws IOException, ConfigServiceException {
        URL configServiceUrl = getConfigServiceUrl(uri.getAuthority(), uri.getPath());
        JsonNode jsonNode = jsonObjectMapper.readValue(configServiceUrl, JsonNode.class);
        String bucketName = uri.getPath().substring(1);
        JsonNode keyValues = jsonNode.get("keys");
        AuthNProperties authNProperties = new AuthNProperties(keyValues);
        return IPConfigClient.getConfigClient(authNProperties).getDynamicBucket(bucketName);

    }

    private InputStream readConfigurationFromIPConfigService(URI uri) throws IOException, ConfigServiceException {
        System.setProperty("sun.net.client.defaultConnectTimeout", "5000");
        System.setProperty("sun.net.client.defaultReadTimeout", "5000");
        DynamicBucket dynamicBucket = readConfigurationFromFkConfigService(uri);
        String bucket = jsonObjectMapper.writeValueAsString(dynamicBucket);
        JsonNode jsonNode = jsonObjectMapper.readValue(bucket, JsonNode.class);
        return IOUtils.toInputStream(jsonNode.get("keys").toString(), Charset.defaultCharset());
    }

    @Data
    public class AuthNProperties {

        private String authNURL;
        private String clientId;
        private String clientSecretKey;
        private int maxConnection;
        private int connectTimeOut;
        private int readTimeOut;

        AuthNProperties(JsonNode keyValues) {
            setData(keyValues);
        }

        private void setData(JsonNode keyValues) {
            this.authNURL = keyValues.get(AUTHN_URL).textValue();
            this.clientId = keyValues.get(AUTHN_CLIENT_ID).textValue();
            this.clientSecretKey = keyValues.get(AUTHN_CLIENT_SECRET_KEY).textValue();
            this.maxConnection = keyValues.get(AUTHN_MAX_CONNECTION).intValue();
            this.connectTimeOut = keyValues.get(AUTHN_CONNECTION_TIMEOUT).intValue();
            this.readTimeOut = keyValues.get(AUTHN_READ_TIMEOUT).intValue();
        }
    }
}




