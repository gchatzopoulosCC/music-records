package gr.york.mobiledev2026.database.track;

public class Track {
    private String name;
    private String artist;
    private String image; // or URL

    public Track(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }

    public String getName() { return name; }
    public String getArtist() { return artist; }
    public String getImage() { return image;}
}
