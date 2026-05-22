package gr.york.mobiledev2026.ui.track;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import gr.york.mobiledev2026.data.enumeration.Status;
import gr.york.mobiledev2026.data.model.Resource;
import gr.york.mobiledev2026.data.model.Track;
import gr.york.mobiledev2026.data.repository.TrackRepository;

public class TrackViewModel extends AndroidViewModel {
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>();
    private final LiveData<List<Track>> tracks;
    private final TrackRepository repository;

    public TrackViewModel(Application app) {
        super(app);
        this.repository = TrackRepository.getInstance();

        tracks = Transformations.switchMap(searchQuery, query -> {
            LiveData<Resource<List<Track>>> source;
            if (query == null || query.isEmpty()) {
                source = repository.getTracks();
            } else {
                source = repository.searchTracks(query);
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

    public LiveData<List<Track>> getTracksList() {
        return tracks;
    }
}
