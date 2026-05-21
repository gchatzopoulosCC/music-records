package gr.york.mobiledev2026.data.repository;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import gr.york.mobiledev2026.data.enumeration.Status;
import gr.york.mobiledev2026.data.model.Chart;
import gr.york.mobiledev2026.data.model.Resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class ChartRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private ChartRepository repository;

    @Before
    public void setUp() {
        repository = ChartRepository.getInstance();
    }

    @Test
    public void testGetChartApi() {
        try {
            LiveData<Resource<Chart>> liveData = repository.getChart();
            Resource<Chart> result = LiveDataTestUtil.getOrAwaitValue(liveData);
            assertNotNull(result);
            assertEquals("API returned error: " + result.message, Status.SUCCESS, result.status);
            assertNotNull(result.data);

            assertNotNull(result.data.getTopArtists());
            assertFalse(result.data.getTopArtists().isEmpty());

            assertNotNull(result.data.getTopTracks());
            assertFalse(result.data.getTopTracks().isEmpty());

            assertNotNull(result.data.getTopTags());
            assertFalse(result.data.getTopTags().isEmpty());
        } catch (Exception e) {
            throw new RuntimeException("ERROR WAS: " + e.getMessage());
        }
    }
}

