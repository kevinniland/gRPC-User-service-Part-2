package ie.gmit.ds;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class ArtistApiApplication extends Application<ArtistApiConfig> {

    public static void main(String[] args) throws Exception {
        new ArtistApiApplication().run(args);
    }

    public void run(ArtistApiConfig artistApiConfig, Environment environment) throws Exception {

        final ArtistApiResource resource =
                new ArtistApiResource();

        final ExampleHealthCheck healthCheck =
                new ExampleHealthCheck();

        environment.healthChecks().register("example", healthCheck);
        environment.jersey().register(resource);
    }

}


