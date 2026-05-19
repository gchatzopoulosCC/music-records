package gr.york.mobiledev2026.data.model;

import java.util.List;

public class Chart {
    List<Artist> topArtists;
    List<Track> topTracks;
    List<Tag> topTags;

    public Chart(List<Artist> topArtists, List<Track> topTracks, List<Tag> topTags) {
        this.topArtists = topArtists;
        this.topTracks = topTracks;
        this.topTags = topTags;
    }

    public List<Artist> getTopArtists() {
        return topArtists;
    }

    public void setTopArtists(List<Artist> topArtists) {
        this.topArtists = topArtists;
    }

    public List<Track> getTopTracks() {
        return topTracks;
    }

    public void setTopTracks(List<Track> topTracks) {
        this.topTracks = topTracks;
    }

    public List<Tag> getTopTags() {
        return topTags;
    }

    public void setTopTags(List<Tag> topTags) {
        this.topTags = topTags;
    }
}
