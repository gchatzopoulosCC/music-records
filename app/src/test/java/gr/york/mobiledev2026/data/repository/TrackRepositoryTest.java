package gr.york.mobiledev2026.data.repository;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import gr.york.mobiledev2026.data.enumeration.Status;
import gr.york.mobiledev2026.data.model.Resource;
import gr.york.mobiledev2026.data.model.Track;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class TrackRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private TrackRepository repository;

    @Before
    public void setUp() {
        repository = TrackRepository.getInstance();
    }

    @Test
    public void testGetTracksApi() {
        try {
            LiveData<Resource<List<Track>>> liveData = repository.getTracks();
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
    public void testGetTracksByArtistApi() {
        try {
            LiveData<Resource<List<Track>>> liveData = repository.getTracksByArtist("Cher");
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
    public void testGetTrackByArtistAndNameApi() {
        try {
            LiveData<Resource<Track>> liveData = repository.getTrackByArtistAndName("Cher", "Believe");
            Resource<Track> result = LiveDataTestUtil.getOrAwaitValue(liveData);
            assertNotNull(result);
            assertEquals("API returned error: " + result.message, Status.SUCCESS, result.status);
            assertNotNull(result.data);
            assertEquals("Believe", result.data.getName());
        } catch (Exception e) {
            throw new RuntimeException("ERROR WAS: " + e.getMessage());
        }
    }
}

