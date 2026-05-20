package gr.york.mobiledev2026.data.repository;

import androidx.lifecycle.LiveData;

import gr.york.mobiledev2026.data.api.ChartService;
import gr.york.mobiledev2026.data.model.Chart;
import gr.york.mobiledev2026.data.model.Resource;
import gr.york.mobiledev2026.data.remote.ApiResponse;
import gr.york.mobiledev2026.data.remote.NetworkUtils;
import gr.york.mobiledev2026.data.remote.RetrofitClient;
import io.reactivex.rxjava3.core.Observable;

public class ChartRepository {

    private static ChartRepository instance;
    private final ChartService service;

    private ChartRepository() {
        service = RetrofitClient.getClient().create(ChartService.class);
    }

    public static synchronized ChartRepository getInstance() {
        if (instance == null) {
            instance = new ChartRepository();
        }
        return instance;
    }

    public LiveData<Resource<Chart>> getChart() {
        Observable<ApiResponse<Chart>> combined = Observable.zip(
                service.getTopArtists(),
                service.getTopTracks(),
                service.getTopTags(),
                (artistsResponse, tracksResponse, tagsResponse) -> {
                    ApiResponse<Chart> combinedResponse = new ApiResponse<>();
                    if (!artistsResponse.isSuccess()) {
                        combinedResponse.setError(artistsResponse.getError());
                        return combinedResponse;
                    }
                    if (!tracksResponse.isSuccess()) {
                        combinedResponse.setError(tracksResponse.getError());
                        return combinedResponse;
                    }
                    if (!tagsResponse.isSuccess()) {
                        combinedResponse.setError(tagsResponse.getError());
                        return combinedResponse;
                    }

                    Chart chart = new Chart(
                            artistsResponse.getData(),
                            tracksResponse.getData(),
                            tagsResponse.getData()
                    );
                    combinedResponse.setData(chart);
                    return combinedResponse;
                }
        );
        return NetworkUtils.processObservable(combined, Chart::isValid);
    }
}
