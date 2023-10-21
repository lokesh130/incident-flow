package com.flipkart.fdsg.planning.ip.core.clients;

import com.flipkart.fdsg.planning.ip.core.config.IFGenericConfigurationProvider.AuthNProperties;
import com.flipkart.kloud.authn.AuthTokenService;
import com.flipkart.kloud.config.ConfigClient;
import com.flipkart.kloud.config.ConfigClientBuilder;
import com.flipkart.security.cryptex.CryptexClient;
import com.flipkart.security.cryptex.CryptexClientBuilder;

public class IPConfigClient {

    private static AuthTokenService authTokenService;

    public static ConfigClient getConfigClient(AuthNProperties authNProperties) {
        createAuthNInstance(authNProperties.getAuthNURL(), authNProperties.getClientId(),
                authNProperties.getClientSecretKey());
        CryptexClient cryptexClient = new CryptexClientBuilder(authTokenService)
                .maxConnections(authNProperties.getMaxConnection()).readTimeOut(authNProperties.getReadTimeOut())
                .connectTimeOut(authNProperties.getConnectTimeOut()).build();
        return new ConfigClientBuilder().cryptexClient(cryptexClient).build();
    }

    public static void createAuthNInstance(String authNURL, String clientId, String secretKey) {
        if (authTokenService == null) {
            AuthTokenService.init(authNURL, clientId, secretKey);
            authTokenService = AuthTokenService.getInstance();
        }
    }
}
