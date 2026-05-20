package gr.york.mobiledev2026.data.repository;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import gr.york.mobiledev2026.data.enumeration.Status;
import gr.york.mobiledev2026.data.model.Album;
import gr.york.mobiledev2026.data.model.Resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class AlbumRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private AlbumRepository repository;

    @Before
    public void setUp() {
        repository = AlbumRepository.getInstance();
    }

    @Test
    public void testGetAlbumsApi() {
        try {
            LiveData<Resource<List<Album>>> liveData = repository.getAlbums();
            Resource<List<Album>> result = LiveDataTestUtil.getOrAwaitValue(liveData);
            assertNotNull(result);
            assertEquals("API returned error: " + result.message, Status.SUCCESS, result.status);
            assertNotNull(result.data);
            assertFalse(result.data.isEmpty());
        } catch (Exception e) {
            throw new RuntimeException("ERROR WAS: " + e.getMessage() + " caused by: " + (e.getCause() != null ? e.getCause().getMessage() : "null"));
        }
    }

    @Test
    public void testGetAlbumByArtistAndNameApi() {
        try {
            LiveData<Resource<Album>> liveData = repository.getAlbumByArtistAndName("Cher", "Believe");
            Resource<Album> result = LiveDataTestUtil.getOrAwaitValue(liveData);

            assertNotNull(result);
            assertEquals("API returned error: " + result.message, Status.SUCCESS, result.status);
            assertNotNull(result.data);
            assertEquals("Believe", result.data.getName());
        } catch (Exception e) {
            throw new RuntimeException("ERROR WAS: " + e.getMessage() + " caused by: " + (e.getCause() != null ? e.getCause().getMessage() : "null"));
        }
    }
}
