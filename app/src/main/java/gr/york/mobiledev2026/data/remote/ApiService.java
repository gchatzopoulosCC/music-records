package gr.york.mobiledev2026.data.remote;

import gr.york.mobiledev2026.data.model.Album;
import gr.york.mobiledev2026.data.model.Artist;
import gr.york.mobiledev2026.data.model.Chart;
import gr.york.mobiledev2026.data.model.Track;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface ApiService {
    @GET("album")
    Call<List<Album>> getAlbums();
    @GET("album/{artist}")
    Call<List<Album>> getAlbumsByArtist(Artist artist);
    @GET("album/{artist}/{name}")
    Call<Album> getAlbumByArtistAndName(Artist artist, String name);

    @GET("artist")
    Call<List<Artist>> getArtists();
    @GET("artist/{name}")
    Call<Artist> getArtistByName(String name);

    @GET("chart")
    Call<Chart> getChart();

    @GET("track")
    Call<List<Track>> getTracks();
    @GET("track/{artist}")
    Call<List<Track>> getTracksByArtist(Artist artist);
    @GET("track/{artist}/{name}")
    Call<Track> getTrackByArtistAndName(Artist artist, String name);
}
