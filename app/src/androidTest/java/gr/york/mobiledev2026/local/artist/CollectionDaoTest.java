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
import gr.york.mobiledev2026.data.local.CollectionDao;
import gr.york.mobiledev2026.data.local.CollectionEntity;

@RunWith(AndroidJUnit4.class)
public class CollectionDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase appDatabase;
    private CollectionDao collectionDao;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        collectionDao = appDatabase.artistDao();
    }

    @After
    public void closeDb() {
        if (appDatabase != null) {
            appDatabase.close();
        }
    }

    @Test
    public void insertAndFindByNameSync() {
        CollectionEntity artist = new CollectionEntity("Test Artist");
        artist.setImagePath("path/to/image");
        collectionDao.insert(artist);

        CollectionEntity loaded = collectionDao.findByNameSync("Test Artist");
        assertNotNull(loaded);
        assertEquals("Test Artist", loaded.getName());
        assertEquals("path/to/image", loaded.getImagePath());
    }

    @Test
    public void updateArtist() {
        CollectionEntity artist = new CollectionEntity("Update Me");
        collectionDao.insert(artist);

        CollectionEntity loaded = collectionDao.findByNameSync("Update Me");
        loaded.setImagePath("new/path");
        collectionDao.update(loaded);

        CollectionEntity updated = collectionDao.findByNameSync("Update Me");
        assertEquals("new/path", updated.getImagePath());
    }

    @Test
    public void deleteArtist() {
        CollectionEntity artist = new CollectionEntity("Delete Me");
        collectionDao.insert(artist);

        CollectionEntity loaded = collectionDao.findByNameSync("Delete Me");
        collectionDao.delete(loaded);

        CollectionEntity afterDelete = collectionDao.findByNameSync("Delete Me");
        assertNull(afterDelete);
    }

    @Test
    public void readAllArtistsLiveData() throws Exception {
        collectionDao.insert(new CollectionEntity("Artist 1"));
        collectionDao.insert(new CollectionEntity("Artist 2"));

        List<CollectionEntity> allArtists = LiveDataTestUtil.getOrAwaitValue(collectionDao.readAll());
        assertNotNull(allArtists);
        assertEquals(2, allArtists.size());
    }
}
