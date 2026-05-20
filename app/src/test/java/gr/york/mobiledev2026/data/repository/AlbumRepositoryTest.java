package gr.york.mobiledev2026.data.repository;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import gr.york.mobiledev2026.data.api.AlbumService;
import gr.york.mobiledev2026.data.enumeration.Status;
import gr.york.mobiledev2026.data.model.Album;
import gr.york.mobiledev2026.data.model.Artist;
import gr.york.mobiledev2026.data.model.Resource;
import gr.york.mobiledev2026.data.remote.ApiResponse;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.atLeastOnce;

import java.lang.reflect.Field;

public class AlbumRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private Observer<Resource<List<Album>>> listObserver;

    @Mock
    private Observer<Resource<Album>> singleObserver;

    @Mock
    private AlbumService albumService;

    private AlbumRepository repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());

        repository = AlbumRepository.getInstance();

        // Inject the mock service into the Singleton repository using reflection
        Field serviceField = AlbumRepository.class.getDeclaredField("service");
        serviceField.setAccessible(true);
        serviceField.set(repository, albumService);
    }

    @After
    public void tearDown() {
        RxJavaPlugins.reset();
    }

    @Test
    public void testGetAlbums_Success() {
        ApiResponse mockResponse = mock(ApiResponse.class);
        List<Album> albums = new ArrayList<>();
        albums.add(new Album(new Artist("Kendrick"), "DAMN."));

        when(mockResponse.isSuccess()).thenReturn(true);
        when(mockResponse.getData()).thenReturn(albums);
        when(albumService.getAlbums()).thenReturn(Observable.just(mockResponse));

        LiveData<Resource<List<Album>>> liveData = repository.getAlbums();
        liveData.observeForever(listObserver);

        ArgumentCaptor<Resource<List<Album>>> captor = ArgumentCaptor.forClass(Resource.class);
        verify(listObserver, atLeastOnce()).onChanged(captor.capture());

        assertEquals(Status.SUCCESS, captor.getValue().status);
        assertEquals(1, captor.getValue().data.size());
        assertEquals("DAMN.", captor.getValue().data.get(0).getName());
    }

    @Test
    public void testGetAlbumByArtistAndName_InvalidData() {
        ApiResponse mockResponse = mock(ApiResponse.class);
        // Artist is valid, but the name is null, so it becomes invalid
        Album invalidAlbum = new Album(new Artist("Kendrick"), null);

        when(mockResponse.isSuccess()).thenReturn(true);
        when(mockResponse.getData()).thenReturn(invalidAlbum);
        when(albumService.getAlbumByArtistAndName("Kendrick", "Unknown")).thenReturn(Observable.just(mockResponse));

        LiveData<Resource<Album>> liveData = repository.getAlbumByArtistAndName("Kendrick", "Unknown");
        liveData.observeForever(singleObserver);

        ArgumentCaptor<Resource<Album>> captor = ArgumentCaptor.forClass(Resource.class);
        verify(singleObserver, atLeastOnce()).onChanged(captor.capture());

        assertEquals(Status.ERROR, captor.getValue().status);
        assertEquals("Invalid data received", captor.getValue().message);
    }
}
