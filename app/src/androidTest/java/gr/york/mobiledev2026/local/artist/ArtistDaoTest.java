package gr.york.mobiledev2026.local.artist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import gr.york.mobiledev2026.LiveDataTestUtil;
import gr.york.mobiledev2026.data.local.AppDatabase;
import gr.york.mobiledev2026.data.local.ArtistDao;
import gr.york.mobiledev2026.data.local.ArtistEntity;

@RunWith(AndroidJUnit4.class)
public class ArtistDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase appDatabase;
    private ArtistDao artistDao;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        artistDao = appDatabase.artistDao();
    }

    @After
    public void closeDb() {
        if (appDatabase != null) {
            appDatabase.close();
        }
    }

    @Test
    public void insertAndFindByNameSync() {
        ArtistEntity artist = new ArtistEntity("Test Artist");
        artist.setImagePath("path/to/image");
        artistDao.insert(artist);

        ArtistEntity loaded = artistDao.findByNameSync("Test Artist");
        assertNotNull(loaded);
        assertEquals("Test Artist", loaded.getName());
        assertEquals("path/to/image", loaded.getImagePath());
    }

    @Test
    public void updateArtist() {
        ArtistEntity artist = new ArtistEntity("Update Me");
        artistDao.insert(artist);

        ArtistEntity loaded = artistDao.findByNameSync("Update Me");
        loaded.setImagePath("new/path");
        artistDao.update(loaded);

        ArtistEntity updated = artistDao.findByNameSync("Update Me");
        assertEquals("new/path", updated.getImagePath());
    }

    @Test
    public void deleteArtist() {
        ArtistEntity artist = new ArtistEntity("Delete Me");
        artistDao.insert(artist);

        ArtistEntity loaded = artistDao.findByNameSync("Delete Me");
        artistDao.delete(loaded);

        ArtistEntity afterDelete = artistDao.findByNameSync("Delete Me");
        assertNull(afterDelete);
    }

    @Test
    public void readAllArtistsLiveData() throws Exception {
        artistDao.insert(new ArtistEntity("Artist 1"));
        artistDao.insert(new ArtistEntity("Artist 2"));

        List<ArtistEntity> allArtists = LiveDataTestUtil.getOrAwaitValue(artistDao.readAll());
        assertNotNull(allArtists);
        assertEquals(2, allArtists.size());
    }
}
