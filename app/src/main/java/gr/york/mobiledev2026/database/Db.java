package gr.york.mobiledev2026.database;

import androidx.room3.Database;
import androidx.room3.RoomDatabase;

@Database(entities = {ArtistEntity.class, ArtistFts.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract ArtistDao artistDao();
}
