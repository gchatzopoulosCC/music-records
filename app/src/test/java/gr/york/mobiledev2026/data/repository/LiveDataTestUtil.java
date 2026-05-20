package gr.york.mobiledev2026.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import gr.york.mobiledev2026.data.model.Resource;
import gr.york.mobiledev2026.data.enumeration.Status;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LiveDataTestUtil {
    public static <T> T getOrAwaitValue(final LiveData<T> liveData) throws InterruptedException, TimeoutException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(T o) {
                // Return when it's no longer LOADING
                if (o instanceof Resource) {
                    if (((Resource<?>) o).status != Status.LOADING) {
                        data[0] = o;
                        latch.countDown();
                        liveData.removeObserver(this);
                    }
                } else {
                    data[0] = o;
                    latch.countDown();
                    liveData.removeObserver(this);
                }
            }
        };
        liveData.observeForever(observer);
        if (!latch.await(10, TimeUnit.SECONDS)) {
            liveData.removeObserver(observer);
            throw new TimeoutException("LiveData value was never set.");
        }
        @SuppressWarnings("unchecked")
        T result = (T) data[0];
        return result;
    }
}

