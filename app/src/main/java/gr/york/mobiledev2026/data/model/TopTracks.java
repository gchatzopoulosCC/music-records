package gr.york.mobiledev2026.data.model;

import java.util.List;

public class TopTracks {
    Artist artist;
    List<Track> tracks;

    public TopTracks(Artist artist, List<Track> tracks) {
        this.artist = artist;
        this.tracks = tracks;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
