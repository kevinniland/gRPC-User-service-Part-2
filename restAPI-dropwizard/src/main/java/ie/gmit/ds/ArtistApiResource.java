package ie.gmit.ds;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Path("/artists")
@Produces(MediaType.APPLICATION_JSON)
public class ArtistApiResource {
    List<Artist> artists = Arrays.asList(new Artist(1, "The GZA", "HipHop", 2));


    @GET
    public List<Artist> getArtists() {
        return artists;
    }
}
