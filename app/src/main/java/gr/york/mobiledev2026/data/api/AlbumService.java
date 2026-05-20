package gr.york.mobiledev2026.data.api;

import java.util.List;

import gr.york.mobiledev2026.data.model.Album;
import gr.york.mobiledev2026.data.remote.ApiResponse;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AlbumService {
    @GET("album")
    Observable<ApiResponse<List<Album>>> getAlbums();

    @GET("album/{artist}")
    Observable<ApiResponse<List<Album>>> getAlbumsByArtist(@Path("artist") String artistName);

    @GET("album/{artist}/{name}")
    Observable<ApiResponse<Album>> getAlbumByArtistAndName(@Path("artist") String artistName, @Path("name") String name);
}
