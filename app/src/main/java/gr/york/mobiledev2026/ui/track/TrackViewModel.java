package gr.york.mobiledev2026.ui.track;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import gr.york.mobiledev2026.data.model.Track;
import gr.york.mobiledev2026.data.service.TrackService;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class TrackViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Track>> tracks = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();
    private TrackService service;

    public TrackViewModel(Application app) {
        super(app);
//        TODO
//        this.service = RetrofitClient.getClient().create(TrackService.class);
        fetchTracks();
    }

    public LiveData<List<Track>> getTracksList() { return tracks; }

    private void fetchTracks() {
        disposables.add(
            service.getTracks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    response -> {
                        if (response.isSuccess()) {
                            tracks.setValue(response.getData());
                        }
                    }
                )
        );
    }

    // 4. Clean up to prevent memory leaks
    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
