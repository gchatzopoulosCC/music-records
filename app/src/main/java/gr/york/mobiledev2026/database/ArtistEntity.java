package gr.york.mobiledev2026.database;

import android.graphics.Bitmap;

public class ArtistEntity {
    private String title;
    private Bitmap image;

    public ArtistEntity(String t, Bitmap i) {
        this.title = t;
        this.image = i;
    }

    public String getTitle() {
        return this.title;
    }

    public Bitmap getImage() {
        return this.image;
    }
}
