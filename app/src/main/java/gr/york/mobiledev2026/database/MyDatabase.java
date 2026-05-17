package gr.york.mobiledev2026.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserEntity.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}
