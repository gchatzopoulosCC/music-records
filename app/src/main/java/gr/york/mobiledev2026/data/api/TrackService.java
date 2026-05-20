package gr.york.mobiledev2026.data.api;

import java.util.List;

import gr.york.mobiledev2026.data.model.Track;
import gr.york.mobiledev2026.data.remote.ApiResponse;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TrackService {
    @GET("?method=chart.gettoptracks")
    Observable<ApiResponse<List<Track>>> getTracks();

    @GET("?method=artist.gettoptracks")
    Observable<ApiResponse<List<Track>>> getTracksByArtist(@Query("artist") String artistName);

    @GET("?method=track.getinfo")
    Observable<ApiResponse<Track>> getTrackByArtistAndName(@Query("artist") String artistName, @Query("track") String name);

    @GET("?method=track.search")
    Observable<ApiResponse<List<Track>>> searchTracks(@Query("track") String query);
}
