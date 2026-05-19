package gr.york.mobiledev2026.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Upsert;

import java.util.List;

@Dao
public interface CollectionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CollectionEntity artist);

    @Update
    void update(CollectionEntity artist);

    @Upsert
    void save(CollectionEntity artist);

    @Delete
    void delete(CollectionEntity artist);

    @Query("SELECT * FROM CollectionEntity")
    LiveData<List<CollectionEntity>> readAll();

    @Query("SELECT * FROM CollectionEntity LIMIT :lim")
    LiveData<List<CollectionEntity>> readUpTo(int lim);

    @Query("SELECT * FROM CollectionEntity WHERE name = :query")
    LiveData<CollectionEntity> findByName(String query);

    @Query("SELECT * FROM CollectionEntity WHERE name = :query")
    CollectionEntity findByNameSync(String query);

    @Query("SELECT CollectionEntity.* FROM CollectionEntity " +
            "JOIN CollectionFts ON CollectionEntity.rowid = CollectionFts.rowid " +
            "WHERE CollectionFts MATCH :query")
    LiveData<List<CollectionEntity>> searchByName(String query);

    @Query("SELECT image_path FROM CollectionEntity WHERE name = :name")
    String getImagePathByName(String name);

    @Query("SELECT image_path FROM CollectionEntity WHERE name = :name")
    LiveData<String> getImagePathLiveDataByName(String name);
}
