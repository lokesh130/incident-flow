package com.flipkart.fdsg.planning.ip.ws;

import com.flipkart.fdsg.planning.ip.core.config.GuiceInjectorProvider;
import com.flipkart.fdsg.planning.ip.core.config.ObjectMapperProvider;
import com.flipkart.fdsg.planning.ip.core.helpers.EntityHelper;
import com.flipkart.fdsg.planning.ip.ws.config.IFSubstitutingSourceProvider;
import com.flipkart.fdsg.planning.ip.ws.config.IncidentFlowConfiguration;
import com.flipkart.fdsg.planning.ip.ws.resources.DefaultResource;
import com.flipkart.fdsg.planning.ip.ws.utils.IFConfigServiceBundle;
import com.google.inject.Stage;
import com.hubspot.dropwizard.guicier.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;


public class IncidentFlowApplication extends Application<IncidentFlowConfiguration> {

    private GuiceBundle<IncidentFlowConfiguration> guiceBundle;

    @Override
    public void initialize(Bootstrap<IncidentFlowConfiguration> bootstrap) {
        bootstrap.setObjectMapper(new ObjectMapperProvider().get());

        String[] entityPackageArrays = new String[]{EntityHelper.getEntityPackage(),"com.flipkart.restbus.hibernate.models"};
        final HibernateBundle<IncidentFlowConfiguration> hibernateBundle = new
                ScanningHibernateBundle<IncidentFlowConfiguration>(entityPackageArrays, new SessionFactoryFactory()) {
            @Override
            public PooledDataSourceFactory getDataSourceFactory(IncidentFlowConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }

        };
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(new IFConfigServiceBundle());

        IncidentFlowModule incidentFlowModule = new IncidentFlowModule(hibernateBundle::getSessionFactory);
        guiceBundle = GuiceBundle.defaultBuilder(IncidentFlowConfiguration.class)
                .modules(incidentFlowModule)
                .enableGuiceEnforcer(false)
                .stage(Stage.PRODUCTION)
                .build();
        bootstrap.addBundle(guiceBundle);

        bootstrap.addBundle(new SwaggerBundle<IncidentFlowConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(IncidentFlowConfiguration configuration) {
                SwaggerBundleConfiguration swaggerBundleConfiguration = new SwaggerBundleConfiguration();
                swaggerBundleConfiguration.setResourcePackage(DefaultResource.class.getPackage().getName());
                return swaggerBundleConfiguration;
            }
        });

        bootstrap.setConfigurationSourceProvider(
                new IFSubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false))
        );
    }

    @Override
    public void run(IncidentFlowConfiguration configuration, Environment environment) {
        environment.jersey().register(MultiPartFeature.class);

        //TODO: This is not very intuitive. See how to simplify this part
        GuiceInjectorProvider.getInstance().setInjector(guiceBundle.getInjector());
        enableCors(environment);
    }

    private void enableCors(Environment environment) {
        // Enable CORS headers
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }

    public static void main(String[] args) throws Exception {
            new IncidentFlowApplication().run(args);
    }
}
