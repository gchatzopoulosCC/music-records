package gr.york.mobiledev2026.data.api;

import java.util.List;

import gr.york.mobiledev2026.data.model.Album;
import gr.york.mobiledev2026.data.remote.ApiResponse;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AlbumService {
    @GET("?method=album.search&album=best")
    Observable<ApiResponse<List<Album>>> getAlbums();

    @GET("?method=artist.gettopalbums")
    Observable<ApiResponse<List<Album>>> getAlbumsByArtist(@Query("artist") String artistName);

    @GET("?method=album.getinfo")
    Observable<ApiResponse<Album>> getAlbumByArtistAndName(@Query("artist") String artistName, @Query("album") String name);

    @GET("?method=album.search")
    Observable<ApiResponse<List<Album>>> searchAlbums(@Query("album") String query);
}
