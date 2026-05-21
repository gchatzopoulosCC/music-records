package gr.york.mobiledev2026.ui.collection;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import gr.york.mobiledev2026.data.local.CollectionEntity;
import gr.york.mobiledev2026.data.repository.CollectionRepository;

public class CollectionViewModel extends AndroidViewModel {
    private final CollectionRepository repository;
    private final LiveData<List<CollectionEntity>> allArtists;

    public CollectionViewModel(Application application) {
        super(application);
        repository = new CollectionRepository(application);
        allArtists = repository.getAllArtists();
    }

    public LiveData<List<CollectionEntity>> getAllArtists() {
        return allArtists;
    }

    public LiveData<List<CollectionEntity>> getArtistsUpTo(int lim) {
        return repository.getArtistsUpTo(lim);
    }

    public LiveData<List<CollectionEntity>> searchArtistsByName(String query) {
        return repository.searchArtistsByName(query);
    }

    public LiveData<CollectionEntity> findArtistByName(String name) {
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

    public String saveImage(String name, Bitmap image) {
        return repository.saveImage(getApplication(), name, image);
    }

    public void insert(CollectionEntity artist) {
        repository.insert(artist);
    }

    public void update(CollectionEntity artist) {
        repository.update(artist);
    }

    public void save(CollectionEntity artist) {
        repository.save(artist);
    }

    public void delete(CollectionEntity artist) {
        repository.delete(artist);
    }
}
