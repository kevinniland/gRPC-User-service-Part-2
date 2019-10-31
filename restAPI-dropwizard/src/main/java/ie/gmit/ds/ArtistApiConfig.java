package ie.gmit.ds;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class ArtistApiConfig extends Configuration {
//    @NotEmpty
    private int port;

    @JsonProperty
    public int getPort() {
        return port;
    }
}