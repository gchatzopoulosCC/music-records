package gr.york.mobiledev2026.ui.artist;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import gr.york.mobiledev2026.data.model.Artist;
import gr.york.mobiledev2026.data.remote.ApiResponse;
import gr.york.mobiledev2026.data.service.ArtistService;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ArtistViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Artist>> artists = new MutableLiveData<>();
    private Disposable searchDisposable;
    private ArtistService service;

    public ArtistViewModel(Application app) {
        super(app);
        // this.service = RetrofitClient.getClient().create(ArtistService.class);
        search("");
    }

    public void search(String query) {
        // Cancel previous search if user is typing fast
        if (searchDisposable != null && !searchDisposable.isDisposed()) {
            searchDisposable.dispose();
        }

        Observable<ApiResponse<List<Artist>>> observable;
        if (query == null || query.isEmpty()) {
            observable = service.getArtists();
        } else {
//            TODO
            observable = service.searchArtists(query);
        }

        searchDisposable = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                response -> {
                    if (response.isSuccess()) {
                        artists.setValue(response.getData());
                    }
                }
            );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (searchDisposable != null) {
            searchDisposable.dispose();
        }
    }

    public LiveData<List<Artist>> getArtistsList() { return artists; }

    public Observable<ApiResponse<Artist>> getArtistByName(String name) {
        return service.getArtistByName(name);
    }
}
