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
public abstract class Db extends RoomDatabase {
    public static volatile Db INSTANCE;

    public static Db getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (Db.class) {
                if (INSTANCE == null) {
                    INSTANCE = databaseBuilder(
                            context.getApplicationContext(),
                            Db.class, "db"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract ArtistDao artistDao();

}
