package gr.york.mobiledev2026.data.remote;

import static java.util.concurrent.TimeUnit.SECONDS;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import gr.york.mobiledev2026.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;


public class RetrofitClient {
    private static final String BASE_URL = "https://ws.audioscrobbler.com/2.0/";
    private static Retrofit retrofit = null;

    static class LastFmDateAdapter {
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.US);

        @FromJson
        public Date fromJson(String dateString) {
            try {
                return dateFormat.parse(dateString);
            } catch (ParseException e) {
                // fallback to try another format or return null
                return null;
            }
        }

        @ToJson
        public String toJson(Date date) {
            return dateFormat.format(date);
        }
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            Interceptor apiKeyInterceptor = chain -> {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key", BuildConfig.API_KEY)
                        .addQueryParameter("format", "json")
                        .build();

                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            };

            OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor)
                .addInterceptor(logging)
                .connectTimeout(30, SECONDS)
                .readTimeout(30, SECONDS)
                .writeTimeout(30, SECONDS)
                .build();

            Moshi moshi = new Moshi.Builder()
                .add(new ApiResponseAdapterFactory.ImageUrlAdapter())
                .add(new ApiResponseAdapterFactory.StatsAdapter())
                .add(new ApiResponseAdapterFactory.TrackStatsAdapter())
                .add(new ApiResponseAdapterFactory.ArtistListFallbackAdapter())
                .add(new ApiResponseAdapterFactory.ArtistFallbackAdapter())
                .add(new ApiResponseAdapterFactory.TagListFallbackAdapter())
                .add(new ApiResponseAdapterFactory.TrackListFallbackAdapter())
                .add(new ApiResponseAdapterFactory())
                .add(new LastFmDateAdapter())
                .build();

            retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        }
        return retrofit;
    }
}
