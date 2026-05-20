package gr.york.mobiledev2026.data.repository;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import gr.york.mobiledev2026.data.enumeration.Status;
import gr.york.mobiledev2026.data.model.Artist;
import gr.york.mobiledev2026.data.model.Resource;
import gr.york.mobiledev2026.data.model.Track;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class ArtistRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private ArtistRepository repository;

    @Before
    public void setUp() {
        repository = ArtistRepository.getInstance();
    }

    @Test
    public void testGetArtistsApi() {
        try {
            LiveData<Resource<List<Artist>>> liveData = repository.getArtists();
            Resource<List<Artist>> result = LiveDataTestUtil.getOrAwaitValue(liveData);
            assertNotNull(result);
            assertEquals("API returned error: " + result.message, Status.SUCCESS, result.status);
            assertNotNull(result.data);
            assertFalse(result.data.isEmpty());
        } catch (Exception e) {
            throw new RuntimeException("ERROR WAS: " + e.getMessage());
        }
    }

    @Test
    public void testGetArtistByNameApi() {
        try {
            LiveData<Resource<Artist>> liveData = repository.getArtistByName("Cher");
            Resource<Artist> result = LiveDataTestUtil.getOrAwaitValue(liveData);
            assertNotNull(result);
            assertEquals("API returned error: " + result.message, Status.SUCCESS, result.status);
            assertNotNull(result.data);
            assertEquals("Cher", result.data.getName());
        } catch (Exception e) {
            throw new RuntimeException("ERROR WAS: " + e.getMessage());
        }
    }

    @Test
    public void testGetTopTracksByArtistApi() {
        try {
            LiveData<Resource<List<Track>>> liveData = repository.getTopTracksByArtist("Cher");
            Resource<List<Track>> result = LiveDataTestUtil.getOrAwaitValue(liveData);
            assertNotNull(result);
            assertEquals("API returned error: " + result.message, Status.SUCCESS, result.status);
            assertNotNull(result.data);
            assertFalse(result.data.isEmpty());
        } catch (Exception e) {
            throw new RuntimeException("ERROR WAS: " + e.getMessage());
        }
    }

    @Test
    public void testSearchArtistsApi() {
        try {
            LiveData<Resource<List<Artist>>> liveData = repository.searchArtists("Cher");
            Resource<List<Artist>> result = LiveDataTestUtil.getOrAwaitValue(liveData);
            assertNotNull(result);
            assertEquals("API returned error: " + result.message, Status.SUCCESS, result.status);
            assertNotNull(result.data);
            assertFalse(result.data.isEmpty());
        } catch (Exception e) {
            throw new RuntimeException("ERROR WAS: " + e.getMessage());
        }
    }
}

