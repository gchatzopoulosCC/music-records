package gr.york.mobiledev2026.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import gr.york.mobiledev2026.data.api.TrackService;
import gr.york.mobiledev2026.data.model.Resource;
import gr.york.mobiledev2026.data.model.Track;
import gr.york.mobiledev2026.data.remote.NetworkUtils;
import gr.york.mobiledev2026.data.remote.RetrofitClient;

public class TrackRepository {

    private static TrackRepository instance;
    private final TrackService service;

    private TrackRepository() {
        service = RetrofitClient.getClient().create(TrackService.class);
    }

    public static synchronized TrackRepository getInstance() {
        if (instance == null) {
            instance = new TrackRepository();
        }
        return instance;
    }

    public LiveData<Resource<List<Track>>> getTracks() {
        return NetworkUtils.processObservable(service.getTracks());
    }

    public LiveData<Resource<List<Track>>> getTracksByArtist(String artistName) {
        return NetworkUtils.processObservable(service.getTracksByArtist(artistName));
    }

    public LiveData<Resource<Track>> getTrackByArtistAndName(String artistName, String name) {
        return NetworkUtils.processObservable(service.getTrackByArtistAndName(artistName, name), Track::isValid);
    }

    public LiveData<Resource<List<Track>>> searchTracks(String query) {
        return NetworkUtils.processObservable(service.searchTracks(query));
    }
}
