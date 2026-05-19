package gr.york.mobiledev2026.data.service;

import gr.york.mobiledev2026.data.model.Chart;
import gr.york.mobiledev2026.data.remote.ApiResponse;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ChartService {
    @GET("chart")
    Observable<ApiResponse<Chart>> getChart();
}
