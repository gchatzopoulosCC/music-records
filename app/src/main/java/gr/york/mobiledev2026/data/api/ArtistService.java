package gr.york.mobiledev2026.data.api;

import java.util.List;

import gr.york.mobiledev2026.data.model.Artist;
import gr.york.mobiledev2026.data.model.Track;
import gr.york.mobiledev2026.data.remote.ApiResponse;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArtistService {
    @GET("?method=chart.gettopartists")
    Observable<ApiResponse<List<Artist>>> getArtists();

    @GET("?method=artist.getinfo")
    Observable<ApiResponse<Artist>> getArtistByName(@Query("artist") String name);

    @GET("?method=artist.gettoptracks")
    Observable<ApiResponse<List<Track>>> getTopTracksByArtist(@Query("artist") String name);
}
