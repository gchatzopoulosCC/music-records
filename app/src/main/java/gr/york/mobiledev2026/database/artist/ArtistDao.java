package gr.york.mobiledev2026.database.artist;

import androidx.lifecycle.LiveData;
import androidx.room3.Dao;
import androidx.room3.Delete;
import androidx.room3.Insert;
import androidx.room3.OnConflictStrategy;
import androidx.room3.Query;
import androidx.room3.Update;
import androidx.room3.Upsert;

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
