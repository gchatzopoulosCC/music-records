package gr.york.mobiledev2026.data.model;

import java.util.List;

public class Artist {
    private String name;
    private String url;
    private String imageUrl;
    private Stats stats;
    private List<Artist> similarArtists;
    private List<Tag> tags;
    private Biography biography;

    public Artist(String name) {
        this.name = name;
    }

    public Artist(String name, String url, String imageUrl, Stats stats, List<Artist> similarArtists, List<Tag> tags, Biography biography) {
        this.name = name;
        this.url = url;
        this.imageUrl = imageUrl;
        this.stats = stats;
        this.similarArtists = similarArtists;
        this.tags = tags;
        this.biography = biography;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public List<Artist> getSimilarArtists() {
        return similarArtists;
    }

    public void setSimilarArtists(List<Artist> similarArtists) {
        this.similarArtists = similarArtists;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Biography getBiography() {
        return biography;
    }

    public void setBiography(Biography biography) {
        this.biography = biography;
    }
}
