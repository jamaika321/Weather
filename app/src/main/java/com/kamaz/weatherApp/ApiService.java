package com.kamaz.weatherApp;

        import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    public static String BaseUrl = "https://api.openweathermap.org/";

    static WeatherService service;

    static void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory
                        (GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        service = retrofit.create(WeatherService.class);
    }
}
