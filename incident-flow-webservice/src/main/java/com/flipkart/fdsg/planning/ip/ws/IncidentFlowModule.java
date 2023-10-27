package com.flipkart.fdsg.planning.ip.ws;

import com.codahale.metrics.health.HealthCheck;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.flipkart.fdsg.planning.ip.core.clients.DefaultGmailClient;
import com.flipkart.fdsg.planning.ip.core.clients.GmailClient;
import com.flipkart.fdsg.planning.ip.core.services.*;
import com.flipkart.fdsg.planning.ip.ws.config.IncidentFlowConfiguration;
import com.google.inject.Binder;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.hubspot.dropwizard.guicier.DropwizardAwareModule;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.setup.Environment;
import lombok.RequiredArgsConstructor;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.hibernate.SessionFactory;
import org.reflections.Reflections;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.ext.ExceptionMapper;
import java.lang.reflect.Modifier;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Set;
import java.util.function.Supplier;

import static org.glassfish.jersey.client.ClientProperties.CONNECT_TIMEOUT;
import static org.glassfish.jersey.client.ClientProperties.READ_TIMEOUT;

@RequiredArgsConstructor
public class IncidentFlowModule extends DropwizardAwareModule<IncidentFlowConfiguration> {
    private static final Reflections reflections = new Reflections(IncidentFlowModule.class.getPackage().getName());
    private final Supplier<SessionFactory> sessionFactorySupplier;

    @Override
    public void configure(Binder binder) {
        registerResources(binder);
        binder.bind(ActiveOncallGroupService.class).to(DefaultActiveOncallGroupService.class);
        binder.bind(CurrentUserService.class).to(DefaultCurrentUserService.class);
        binder.bind(HistoricalUserService.class).to(DefaultHistoricalUserService.class);
        binder.bind(OncallSuggestionService.class).to(DefaultOncallSuggestionService.class);
        binder.bind(OncallTrackerService.class).to(DefaultOncallTrackerService.class);
        binder.bind(OncallUserService.class).to(DefaultOncallUserService.class);
        binder.bind(AlertService.class).to(DefaultAlertService.class);
        binder.bind(FollowupService.class).to(DefaultFollowupService.class);
        binder.bind(WatchEmailService.class).to(DefaultWatchEmailService.class);
        binder.bind(MessageConfigurationService.class).to(DefaultMessageConfigurationService.class);
        binder.bind(RawEmailService.class).to(DefaultRawEmailService.class);
        binder.bind(RefreshTimestampLogService.class).to(DefaultRefreshTimestampLogService.class);
        binder.bind(GmailClient.class).to(DefaultGmailClient.class);
        binder.bind(RefreshService.class).to(DefaultRefreshService.class);
        binder.bind(SyncService.class).to(DefaultSyncService.class);
        binder.bind(ChatGptService.class).to(DefaultChatGptService.class);
    }

    private void registerResources(Binder binder) {
        // Bind REST resources:
        reflections.getTypesAnnotatedWith(Path.class).forEach(binder::bind);
        // Bind exception mappers:
        reflections.getSubTypesOf(ExceptionMapper.class).stream()
                .filter(c -> !Modifier.isAbstract(c.getModifiers()))
                .forEach(binder::bind);
        // Register health checks:
        registerHealthChecks(reflections.getSubTypesOf(HealthCheck.class));
    }

    private void registerHealthChecks(Set<Class<? extends HealthCheck>> healthCheckClasses) {
        for (Class<? extends HealthCheck> healthCheckClass : healthCheckClasses) {
            try {
                getEnvironment().healthChecks().register(healthCheckClass.getSimpleName(), healthCheckClass.newInstance());
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException("Class " + healthCheckClass + " couldn't be instantiated as it lacked empty constructor");
            }
        }
    }

    @Provides
    public SessionFactory provideSessionFactory() {
        return sessionFactorySupplier.get();
    }

    @Provides
    @Singleton
    private Client providesJerseyClient(Environment environment, JerseyClientConfiguration clientConfiguration,
                                        IncidentFlowConfiguration configuration) {

        Client client = getClient(environment, clientConfiguration, "JerseyClient");
        client.property(CONNECT_TIMEOUT, configuration.getHttpConfig().getConnectTimeout());
        client.property(READ_TIMEOUT, configuration.getHttpConfig().getReadTimeout());

        JacksonJsonProvider jacksonJsonProvider =
                new JacksonJaxbJsonProvider()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        client.register(jacksonJsonProvider);
        return client;
    }

    private Client getClient(Environment environment,
                             JerseyClientConfiguration clientConfiguration, String name) {
        Registry<ConnectionSocketFactory> sslRegistry = this.getConnectionSocketRegistry();
        JerseyClientBuilder clientBuilder = (new JerseyClientBuilder(environment)).using(clientConfiguration).using(sslRegistry);
        return clientBuilder.build(name);
    }

    private Registry<ConnectionSocketFactory> getConnectionSocketRegistry() {
        RegistryBuilder<ConnectionSocketFactory> schemeRegistry = RegistryBuilder.create();
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(this.getSslContext(), (host, session) -> true);
        schemeRegistry.register("https", sslConnectionSocketFactory);
        schemeRegistry.register("http", PlainConnectionSocketFactory.INSTANCE);
        return schemeRegistry.build();
    }

    protected SSLContext getSslContext() {
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] arg0, String arg1) {
                }

                public void checkServerTrusted(X509Certificate[] arg0, String arg1) {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            return sslcontext;
        } catch (KeyManagementException | NoSuchAlgorithmException var2) {
            throw new RuntimeException(var2);
        }
    }

}
