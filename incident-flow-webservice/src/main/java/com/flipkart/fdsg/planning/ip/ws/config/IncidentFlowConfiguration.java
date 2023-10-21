package com.flipkart.fdsg.planning.ip.ws.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flipkart.fdsg.planning.ip.core.config.*;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.util.Duration;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Getter
@Setter
public class IncidentFlowConfiguration extends Configuration implements IFCommonConfiguration {
    @NotNull
    @JsonProperty
    private String env;

    @Valid
    @NotNull
    private PoolProperties database = new PoolProperties();

    public DataSourceFactory getDataSourceFactory() {
        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        dataSourceFactory.setUrl(database.getUrl());
        dataSourceFactory.setPassword(database.getPassword());
        dataSourceFactory.setUser(database.getUsername());
        dataSourceFactory.setDriverClass(database.getDriverClassName());
        dataSourceFactory.setInitialSize(database.getInitialSize());
        dataSourceFactory.setMinSize(database.getMinIdle());
        dataSourceFactory.setMaxSize(database.getMaxActive());
        dataSourceFactory.setCheckConnectionWhileIdle(true);
        dataSourceFactory.setMaxWaitForConnection(Duration.milliseconds(database.getMaxWait()));
        dataSourceFactory.setMinIdleTime(Duration.milliseconds(database.getMinIdle()));
        dataSourceFactory.setValidationInterval(Duration.milliseconds(database.getValidationInterval()));
        dataSourceFactory.setValidationQuery(database.getValidationQuery());
        dataSourceFactory.setCheckConnectionOnBorrow(true);
        Map<String, String> dbProperties = database.getDbProperties().stringPropertyNames().stream()
                .collect(toMap(Function.identity(), p -> (String) database.getDbProperties().get(p)));
        dataSourceFactory.setProperties(dbProperties);

        return dataSourceFactory;
    }

    @Valid
    @NotNull
    @JsonProperty("httpconfiguration")
    private HttpConfig httpConfig;

    @Valid
    @NotNull
    @JsonProperty("jerseyClient")
    private JerseyClientConfiguration jerseyClientConfiguration = new JerseyClientConfiguration();

    @Valid
    @NotNull
    @JsonProperty("ipServiceConfiguration")
    private IFServiceConfiguration IFServiceConfiguration;
}
