package ie.gmit.ds;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Artist {

    private int artistId;
    private String artistName;
    private String artistGenre;
    private int albumsRecorded;


    public Artist() {
        // Needed for Jackson deserialisation
    }

    public Artist(int artistId, String artistName, String artistGenre, int albumsRecorded) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistGenre = artistGenre;
        this.albumsRecorded = albumsRecorded;
    }

    @JsonProperty
    public String getArtistName() {
        return artistName;
    }

    @JsonProperty
    public String getArtistGenre() {
        return artistGenre;
    }

    @JsonProperty
    public int getAlbumsRecorded() {
        return albumsRecorded;
    }

    @JsonProperty
    public int getArtistId() {
        return artistId;
    }
}
