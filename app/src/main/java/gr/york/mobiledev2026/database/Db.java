package gr.york.mobiledev2026.database;

import static androidx.room3.Room.databaseBuilder;

import android.content.Context;

import androidx.room3.AutoMigration;
import androidx.room3.Database;
import androidx.room3.RoomDatabase;

@Database(
        entities = {
                ArtistEntity.class,
                ArtistFts.class
        },
        version = 2,
        autoMigrations = {
            @AutoMigration(from = 1, to = 2)
        }
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
