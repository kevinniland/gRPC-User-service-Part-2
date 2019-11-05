package ie.gmit.ds;

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

    public String getArtistName() {
        return artistName;
    }

    public String getArtistGenre() {
        return artistGenre;
    }

    public int getAlbumsRecorded() {
        return albumsRecorded;
    }

    public int getArtistId() {
        return artistId;
    }
}
