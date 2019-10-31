# GMIT Distributed Systems
## Lab: Building a REST API in Java with the Dropwizard Microservice Framework



- pom contains dropwizard dependency, plus maven plugins for building
- Create a configuration class
    - only includes port
    - extends io.dropwizard.Configuration

```
package ie.gmit.ds.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class ArtistApiConfig extends Configuration {
    @NotEmpty
    private int port;

    @JsonProperty
    public int getPort() {
        return port;
    }
}
````

- Create a configuration file
- Add an application class
- Add a resource class for Artist
    - JavaBeans conventions:
        - no argument constructor
        - getters
        - constructor with parameters
- Create Resource class
    - PATH annotation
    - method for getArtists