package gr.york.mobiledev2026.ui;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import gr.york.mobiledev2026.database.ArtistEntity;
import gr.york.mobiledev2026.database.ArtistRepository;

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

    public LiveData<String> getImagePathLiveDataByName(String name) {
        return repository.getImagePathLiveDataByName(name);
    }

    public LiveData<Bitmap> loadArtistImage(String name) {
        return repository.loadImageByName(name);
    }

    public void insert(ArtistEntity artist) {
        repository.insert(artist);
    }
}
