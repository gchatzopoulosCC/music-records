package gr.york.mobiledev2026.data.remote;

import java.util.List;

import gr.york.mobiledev2026.data.model.Album;
import gr.york.mobiledev2026.data.model.Artist;
import gr.york.mobiledev2026.data.model.Chart;
import gr.york.mobiledev2026.data.model.Track;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("album")
    Call<ApiResponse<List<Album>>> getAlbums();

    @GET("album/{artist}")
    Call<ApiResponse<List<Album>>> getAlbumsByArtist(@Path("artist") String artistName);

    @GET("album/{artist}/{name}")
    Call<ApiResponse<Album>> getAlbumByArtistAndName(@Path("artist") String artistName, @Path("name") String name);

    @GET("artist")
    Call<ApiResponse<List<Artist>>> getArtists();

    @GET("artist/{name}")
    Call<ApiResponse<Artist>> getArtistByName(@Path("name") String name);

    @GET("chart")
    Call<ApiResponse<Chart>> getChart();

    @GET("track")
    Call<ApiResponse<List<Track>>> getTracks();

    @GET("track/{artist}")
    Call<ApiResponse<List<Track>>> getTracksByArtist(@Path("artist") String artistName);

    @GET("track/{artist}/{name}")
    Call<ApiResponse<Track>> getTrackByArtistAndName(@Path("artist") String artistName, @Path("name") String name);
}
