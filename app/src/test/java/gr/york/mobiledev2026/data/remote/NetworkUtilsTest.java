package gr.york.mobiledev2026.data.remote;

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

import gr.york.mobiledev2026.data.enumeration.Status;
import gr.york.mobiledev2026.data.model.Resource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NetworkUtilsTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private Observer<Resource<String>> observer;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @After
    public void tearDown() {
        RxJavaPlugins.reset();
    }

    @Test
    public void testProcessObservable_SuccessValidData() {
        ApiResponse mockResponse = mock(ApiResponse.class);
        when(mockResponse.isSuccess()).thenReturn(true);
        when(mockResponse.getData()).thenReturn("ValidData");

        Observable observable = Observable.just(mockResponse);

        LiveData liveData = NetworkUtils.processObservable(observable, data -> true);
        liveData.observeForever(observer);

        ArgumentCaptor<Resource<String>> captor = ArgumentCaptor.forClass(Resource.class);
        verify(observer, org.mockito.Mockito.atLeastOnce()).onChanged(captor.capture());

        assertEquals(Status.SUCCESS, captor.getValue().status);
        assertEquals("ValidData", captor.getValue().data);
    }

    @Test
    public void testProcessObservable_SuccessInvalidData() {
        ApiResponse mockResponse = mock(ApiResponse.class);
        when(mockResponse.isSuccess()).thenReturn(true);
        when(mockResponse.getData()).thenReturn("InvalidData");

        Observable observable = Observable.just(mockResponse);

        LiveData liveData = NetworkUtils.processObservable(observable, data -> false);
        liveData.observeForever(observer);

        ArgumentCaptor<Resource<String>> captor = ArgumentCaptor.forClass(Resource.class);
        verify(observer, org.mockito.Mockito.atLeastOnce()).onChanged(captor.capture());

        assertEquals(Status.ERROR, captor.getValue().status);
        assertEquals("Invalid data received", captor.getValue().message);
    }

    @Test
    public void testProcessObservable_ApiError() {
        ApiResponse mockResponse = mock(ApiResponse.class);
        when(mockResponse.isSuccess()).thenReturn(false);
        when(mockResponse.getError()).thenReturn("Not Found");

        Observable observable = Observable.just(mockResponse);

        LiveData liveData = NetworkUtils.processObservable(observable);
        liveData.observeForever(observer);

        ArgumentCaptor<Resource<String>> captor = ArgumentCaptor.forClass(Resource.class);
        verify(observer, org.mockito.Mockito.atLeastOnce()).onChanged(captor.capture());

        assertEquals(Status.ERROR, captor.getValue().status);
        assertEquals("Not Found", captor.getValue().message);
    }

    @Test
    public void testProcessObservable_NetworkError() {
        Observable<ApiResponse<String>> observable = Observable.error(new RuntimeException("Connection timeout"));

        LiveData<Resource<String>> liveData = NetworkUtils.processObservable(observable);
        liveData.observeForever(observer);

        ArgumentCaptor<Resource<String>> captor = ArgumentCaptor.forClass(Resource.class);
        verify(observer, org.mockito.Mockito.atLeastOnce()).onChanged(captor.capture());

        assertEquals(Status.ERROR, captor.getValue().status);
        assertEquals("Connection timeout", captor.getValue().message);
    }
}
