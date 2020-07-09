package com.kamaz.weatherApp;

import com.kamaz.weatherApp.pojo.WeatherResponse;
import com.kamaz.weatherApp.pojo.WeatherResponseParent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("data/2.5/weather?")
    Call<WeatherResponse> getCurrentWeatherData(@Query("q") String cityName, @Query("APPID") String app_id);

    @GET("data/2.5/group?&units=metric")
    Call<WeatherResponseParent> getCurrentWeathersForCities(@Query("id") String cityIds, @Query("APPID") String app_id);
}
