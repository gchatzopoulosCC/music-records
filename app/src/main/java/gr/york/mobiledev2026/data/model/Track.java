package gr.york.mobiledev2026.data.model;

import java.util.List;

public class Track {
    int id;
    String name;
    String url;
    String imageUrl;
    int duration;
    Stats stats;
    Artist artist;
    Album album;
    List<Tag> tags;
    Description wiki;

    public Track(String name, Artist artist, String imageUrl) {
        this.name = name;
        this.artist = artist;
        this.imageUrl = imageUrl;
    }

    public Track(int id, String name, String url, String imageUrl, int duration, Stats stats, Artist artist, Album album, List<Tag> tags, Description wiki) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.imageUrl = imageUrl;
        this.duration = duration;
        this.stats = stats;
        this.artist = artist;
        this.album = album;
        this.tags = tags;
        this.wiki = wiki;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Description getWiki() {
        return wiki;
    }

    public void setWiki(Description wiki) {
        this.wiki = wiki;
    }
}
