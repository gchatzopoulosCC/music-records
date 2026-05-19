package gr.york.mobiledev2026.data.model;

import java.util.Date;

public class Album {
    private String artist;
    private String album;
    private Date releaseDate;

    public Album() {}

    public Album(String artist, String album) {
        this.artist = artist;
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum() {
        this.album = album;
    }
}
