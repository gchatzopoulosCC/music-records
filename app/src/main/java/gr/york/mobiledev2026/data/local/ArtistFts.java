package gr.york.mobiledev2026.data.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Fts4;

@Entity
@Fts4(contentEntity = ArtistEntity.class)
public class ArtistFts {
    @NonNull
    public String name;

    public ArtistFts(@NonNull String name) {
        this.name = name;
    }
}
