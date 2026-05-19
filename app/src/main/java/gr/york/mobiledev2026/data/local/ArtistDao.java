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
public interface ArtistDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ArtistEntity artist);

    @Update
    void update(ArtistEntity artist);

    @Upsert
    void save(ArtistEntity artist);

    @Delete
    void delete(ArtistEntity artist);

    @Query("SELECT * FROM ArtistEntity")
    LiveData<List<ArtistEntity>> readAll();

    @Query("SELECT * FROM ArtistEntity LIMIT :lim")
    LiveData<List<ArtistEntity>> readUpTo(int lim);

    @Query("SELECT * FROM ArtistEntity WHERE name = :query")
    LiveData<ArtistEntity> findByName(String query);

    @Query("SELECT * FROM ArtistEntity WHERE name = :query")
    ArtistEntity findByNameSync(String query);

    @Query("SELECT ArtistEntity.* FROM ArtistEntity " +
            "JOIN ArtistFts ON ArtistEntity.rowid = ArtistFts.rowid " +
            "WHERE ArtistFts MATCH :query")
    LiveData<List<ArtistEntity>> searchByName(String query);

    @Query("SELECT image_path FROM ArtistEntity WHERE name = :name")
    String getImagePathByName(String name);

    @Query("SELECT image_path FROM ArtistEntity WHERE name = :name")
    LiveData<String> getImagePathLiveDataByName(String name);
}
