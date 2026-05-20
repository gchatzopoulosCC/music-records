package gr.york.mobiledev2026.data.api;

import java.util.List;

import gr.york.mobiledev2026.data.model.Track;
import gr.york.mobiledev2026.data.remote.ApiResponse;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TrackService {
    @GET("track")
    Observable<ApiResponse<List<Track>>> getTracks();

    @GET("track/{artist}")
    Observable<ApiResponse<List<Track>>> getTracksByArtist(@Path("artist") String artistName);

    @GET("track/{artist}/{name}")
    Observable<ApiResponse<Track>> getTrackByArtistAndName(@Path("artist") String artistName, @Path("name") String name);
}
