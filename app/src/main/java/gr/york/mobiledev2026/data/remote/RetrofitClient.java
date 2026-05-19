package gr.york.mobiledev2026.data.remote;

import com.squareup.moshi.Moshi;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;


public class RetrofitClient {
    private static final String BASE_URL = "http://ws.audioscrobbler.com/2.0/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(new Moshi.Builder().build()))
                .build();
        }
        return retrofit;
    }
}
