package gr.york.mobiledev2026.database.artist;

import androidx.annotation.NonNull;
import androidx.room3.ColumnInfo;
import androidx.room3.Entity;

@Entity(primaryKeys = {"name"})
public class ArtistEntity {
    @NonNull
    @ColumnInfo(name = "name", collate = ColumnInfo.NOCASE)
    private String name;

    @ColumnInfo(name = "image_path")
    private String imagePath;

    public ArtistEntity(@NonNull String name) {
        this.name = name.trim();
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @NonNull
    @Override
    public String toString() {
        return "ArtistEntity{" +
                "name='" + name + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
