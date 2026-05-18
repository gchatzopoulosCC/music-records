package gr.york.mobiledev2026.database;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ArtistViewModel extends AndroidViewModel {
    private final ArtistRepository repository;
    private final LiveData<List<ArtistEntity>> allArtists;

    public ArtistViewModel(Application application) {
        super(application);
        repository = new ArtistRepository(application);
        allArtists = repository.getAllArtists();
    }

    public LiveData<List<ArtistEntity>> getAllArtists() {
        return allArtists;
    }

    public LiveData<List<ArtistEntity>> getArtistsUpTo(int lim) {
        return repository.getArtistsUpTo(lim);
    }

    public LiveData<List<ArtistEntity>> searchArtistsByName(String query) {
        return repository.searchArtistsByName(query);
    }

    public LiveData<ArtistEntity> findArtistByName(String name) {
        return repository.findArtistByName(name);
    }

    public String getImagePathByName(String name) {
        return repository.getImagePathByName(name);
    }

    public LiveData<Bitmap> loadArtistImage(String name) {
        String imagePath = repository.getImagePathByName(name);
        MutableLiveData<Bitmap> imageLiveData = new MutableLiveData<>();
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imageLiveData.postValue(bitmap);
        } else {
            imageLiveData.postValue(null);
        }
        return imageLiveData;
    }

    public void insert(ArtistEntity artist) {
        repository.insert(artist);
    }
}
