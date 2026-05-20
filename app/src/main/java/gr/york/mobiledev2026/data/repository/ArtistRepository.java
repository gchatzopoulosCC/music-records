package gr.york.mobiledev2026.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import gr.york.mobiledev2026.data.api.ArtistService;
import gr.york.mobiledev2026.data.model.Artist;
import gr.york.mobiledev2026.data.model.Resource;
import gr.york.mobiledev2026.data.model.Track;
import gr.york.mobiledev2026.data.remote.NetworkUtils;
import gr.york.mobiledev2026.data.remote.RetrofitClient;

public class ArtistRepository {

    private static ArtistRepository instance;
    private final ArtistService service;

    private ArtistRepository() {
        service = RetrofitClient.getClient().create(ArtistService.class);
    }

    public static synchronized ArtistRepository getInstance() {
        if (instance == null) {
            instance = new ArtistRepository();
        }
        return instance;
    }

    public LiveData<Resource<List<Artist>>> getArtists() {
        return NetworkUtils.processObservable(service.getArtists());
    }

    public LiveData<Resource<Artist>> getArtistByName(String name) {
        return NetworkUtils.processObservable(service.getArtistByName(name), Artist::isValid);
    }

    public LiveData<Resource<List<Track>>> getTopTracksByArtist(String name) {
        return NetworkUtils.processObservable(service.getTopTracksByArtist(name));
    }

    public LiveData<Resource<List<Artist>>> searchArtists(String query) {
        return NetworkUtils.processObservable(service.searchArtists(query));
    }
}
