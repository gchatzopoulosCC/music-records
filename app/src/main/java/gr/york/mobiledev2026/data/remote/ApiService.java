package gr.york.mobiledev2026.data.remote;

import java.util.List;

import gr.york.mobiledev2026.data.model.Album;
import gr.york.mobiledev2026.data.model.Artist;
import gr.york.mobiledev2026.data.model.Chart;
import gr.york.mobiledev2026.data.model.Track;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("album")
    Observable<ApiResponse<List<Album>>> getAlbums();

    @GET("album/{artist}")
    Observable<ApiResponse<List<Album>>> getAlbumsByArtist(@Path("artist") String artistName);

    @GET("album/{artist}/{name}")
    Observable<ApiResponse<Album>> getAlbumByArtistAndName(@Path("artist") String artistName, @Path("name") String name);

    @GET("artist")
    Observable<ApiResponse<List<Artist>>> getArtists();

    @GET("artist/{name}")
    Observable<ApiResponse<Artist>> getArtistByName(@Path("name") String name);

    @GET("chart")
    Observable<ApiResponse<Chart>> getChart();

    @GET("track")
    Observable<ApiResponse<List<Track>>> getTracks();

    @GET("track/{artist}")
    Observable<ApiResponse<List<Track>>> getTracksByArtist(@Path("artist") String artistName);

    @GET("track/{artist}/{name}")
    Observable<ApiResponse<Track>> getTrackByArtistAndName(@Path("artist") String artistName, @Path("name") String name);
}
