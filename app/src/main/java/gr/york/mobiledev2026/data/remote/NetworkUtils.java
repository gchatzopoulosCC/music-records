package gr.york.mobiledev2026.data.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import gr.york.mobiledev2026.data.model.Resource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NetworkUtils {

    public interface Validator<T> {
        boolean isValid(T data);
    }

    public static <T> LiveData<Resource<T>> processObservable(Observable<ApiResponse<T>> observable, Validator<T> validator) {
        MutableLiveData<Resource<T>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading());

        Disposable disposable = observable
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> {
                            if (response.isSuccess() && response.getData() != null) {
                                if (validator != null && !validator.isValid(response.getData())) {
                                    liveData.postValue(Resource.error("Invalid data received"));
                                } else {
                                    liveData.postValue(Resource.success(response.getData()));
                                }
                            } else {
                                liveData.postValue(Resource.error(response.getError() != null ? response.getError() : "Unknown error"));
                            }
                        },
                        error -> liveData.postValue(Resource.error(error.getMessage() != null ? error.getMessage() : "Network error"))
                );

        return liveData;
    }

    public static <T> LiveData<Resource<T>> processObservable(Observable<ApiResponse<T>> observable) {
        return processObservable(observable, null);
    }
}
