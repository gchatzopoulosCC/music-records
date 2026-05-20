package gr.york.mobiledev2026.data.repository;

import androidx.lifecycle.LiveData;

import gr.york.mobiledev2026.data.api.ChartService;
import gr.york.mobiledev2026.data.model.Chart;
import gr.york.mobiledev2026.data.model.Resource;
import gr.york.mobiledev2026.data.remote.NetworkUtils;
import gr.york.mobiledev2026.data.remote.RetrofitClient;

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
        return NetworkUtils.processObservable(service.getChart(), Chart::isValid);
    }
}
