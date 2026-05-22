package gr.york.mobiledev2026.data.model;

import java.util.Date;
import java.util.List;

public class Album {
    private String name;
    private Artist artist;
    private Date releaseDate;
    private String coverImageUrl;
    private Stats stats;
    private List<Tag> tags;
    private List<Track> tracks;

    public Album(Artist artist, String name) {
        this.artist = artist;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public boolean isValid() {
        return name != null && !name.trim().isEmpty() && artist != null && artist.isValid();
    }
}
