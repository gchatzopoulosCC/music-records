package gr.york.mobiledev2026.data.local;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"name"})
public class CollectionEntity {
    @NonNull
    @ColumnInfo(name = "name", collate = ColumnInfo.NOCASE)
    private String name;

    @ColumnInfo(name = "image_path")
    private String imagePath;

    public CollectionEntity(@NonNull String name) {
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
        return "CollectionEntity{" +
                "name='" + name + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
