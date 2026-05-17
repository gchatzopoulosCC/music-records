package gr.york.mobiledev2026.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Upsert;

@Dao
public interface UserDao {

    @Insert
    void insert(UserEntity user) throws Exception;

    @Update
    void update(UserEntity user) throws Exception;

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void save(UserEntity user);

    @Upsert
    void save(UserEntity user);

    @Delete
    void delete(UserEntity user);

    @Query("SELECT * FROM UserEntity")
    UserEntity[] readAll();

    @Query("SELECT * FROM UserEntity LIMIT 1")
    UserEntity readFirst();

    @Query("SELECT * FROM UserEntity WHERE id = :id")
    UserEntity readById(int id);

    @Query("SELECT * FROM UserEntity WHERE username LIKE :name")
    UserEntity[] readByName(String name);
}
