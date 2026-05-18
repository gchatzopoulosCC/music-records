package gr.york.mobiledev2026.database.artist;

import androidx.annotation.NonNull;
import androidx.room3.Entity;
import androidx.room3.Fts5;

@Entity
@Fts5(contentEntity = ArtistEntity.class)
public class ArtistFts {
    @NonNull
    public String name;

    public ArtistFts(@NonNull String name) {
        this.name = name;
    }
}
