package gr.york.mobiledev2026.ui.collection;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import gr.york.mobiledev2026.data.local.ArtistEntity;
import gr.york.mobiledev2026.data.repository.ArtistRepository;

public class CollectionViewModel extends AndroidViewModel {
    private final ArtistRepository repository;
    private final LiveData<List<ArtistEntity>> allArtists;

    public CollectionViewModel(Application application) {
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

    public void update(ArtistEntity artist) {
        repository.update(artist);
    }

    public void save(ArtistEntity artist) {
        repository.save(artist);
    }

    public void delete(ArtistEntity artist) {
        repository.delete(artist);
    }
}
