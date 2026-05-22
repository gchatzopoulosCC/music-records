package gr.york.mobiledev2026.ui.artist;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import gr.york.mobiledev2026.data.enumeration.Status;
import gr.york.mobiledev2026.data.model.Artist;
import gr.york.mobiledev2026.data.model.Resource;
import gr.york.mobiledev2026.data.model.Track;
import gr.york.mobiledev2026.data.repository.ArtistRepository;

public class ArtistViewModel extends AndroidViewModel {
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>();
    private final LiveData<List<Artist>> artists;
    private final ArtistRepository repository;

    public ArtistViewModel(Application app) {
        super(app);
        this.repository = ArtistRepository.getInstance();

        artists = Transformations.switchMap(searchQuery, query -> {
            LiveData<Resource<List<Artist>>> source;
            if (query == null || query.isEmpty()) {
                source = repository.getArtists();
            } else {
                source = repository.searchArtists(query);
            }

            return Transformations.map(source, resource -> {
                if (resource != null && resource.status == Status.SUCCESS) {
                    return resource.data;
                }
                return null;
            });
        });

        search("");
    }

    public void search(String query) {
        searchQuery.setValue(query);
    }

    public LiveData<List<Artist>> getArtistsList() {
        return artists;
    }

    public LiveData<Artist> getArtistByName(String name) {
        return Transformations.map(repository.getArtistByName(name), resource -> {
            if (resource != null && resource.status == Status.SUCCESS) {
                return resource.data;
            }
            return null;
        });
    }

    public LiveData<List<Track>> getArtistTopTracks(String artistName) {
        return Transformations.map(repository.getTopTracksByArtist(artistName), resource -> {
            if (resource != null && resource.status == Status.SUCCESS) {
                return resource.data;
            }
            return null;
        });
    }
}
