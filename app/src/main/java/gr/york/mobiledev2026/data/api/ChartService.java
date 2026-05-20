package gr.york.mobiledev2026.data.api;

import java.util.List;

import gr.york.mobiledev2026.data.model.Artist;
import gr.york.mobiledev2026.data.model.Tag;
import gr.york.mobiledev2026.data.model.Track;
import gr.york.mobiledev2026.data.remote.ApiResponse;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ChartService {
    @GET("?method=chart.gettopartists")
    Observable<ApiResponse<List<Artist>>> getTopArtists();

    @GET("?method=chart.gettoptracks")
    Observable<ApiResponse<List<Track>>> getTopTracks();

    @GET("?method=tag.gettoptags")
    Observable<ApiResponse<List<Tag>>> getTopTags();
}
