package gr.york.mobiledev2026.data.local;

import static androidx.room.Room.databaseBuilder;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(
        entities = {
                ArtistEntity.class,
                ArtistFts.class
        },
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {
    public static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class, "db"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract ArtistDao artistDao();

}
