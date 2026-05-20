package gr.york.mobiledev2026.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import gr.york.mobiledev2026.data.api.AlbumService;
import gr.york.mobiledev2026.data.model.Album;
import gr.york.mobiledev2026.data.model.Resource;
import gr.york.mobiledev2026.data.remote.NetworkUtils;
import gr.york.mobiledev2026.data.remote.RetrofitClient;

public class AlbumRepository {

    private static AlbumRepository instance;
    private final AlbumService service;

    private AlbumRepository() {
        service = RetrofitClient.getClient().create(AlbumService.class);
    }

    public static synchronized AlbumRepository getInstance() {
        if (instance == null) {
            instance = new AlbumRepository();
        }
        return instance;
    }

    public LiveData<Resource<List<Album>>> getAlbums() {
        return NetworkUtils.processObservable(service.getAlbums());
    }

    public LiveData<Resource<List<Album>>> getAlbumsByArtist(String artistName) {
        return NetworkUtils.processObservable(service.getAlbumsByArtist(artistName));
    }

    public LiveData<Resource<Album>> getAlbumByArtistAndName(String artistName, String name) {
        return NetworkUtils.processObservable(service.getAlbumByArtistAndName(artistName, name), Album::isValid);
    }

    public LiveData<Resource<List<Album>>> searchAlbums(String query) {
        return NetworkUtils.processObservable(service.searchAlbums(query));
    }
}
