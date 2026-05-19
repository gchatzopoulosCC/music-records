package gr.york.mobiledev2026.data.service;

import java.util.List;

import gr.york.mobiledev2026.data.model.Artist;
import gr.york.mobiledev2026.data.remote.ApiResponse;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ArtistService {
    @GET("artist")
    Observable<ApiResponse<List<Artist>>> getArtists();

    @GET("artist/{name}")
    Observable<ApiResponse<Artist>> getArtistByName(@Path("name") String name);
}
