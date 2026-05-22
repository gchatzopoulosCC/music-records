package gr.york.mobiledev2026.data.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Fts4;

@Entity
@Fts4(contentEntity = CollectionEntity.class)
public class CollectionFts {
    @NonNull
    public String name;

    public CollectionFts(@NonNull String name) {
        this.name = name;
    }
}
