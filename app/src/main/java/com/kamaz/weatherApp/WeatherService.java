package com.kamaz.weatherApp;

import com.kamaz.weatherApp.pojo.WeatherResponse;
import com.kamaz.weatherApp.pojo.WeatherResponseParent;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("data/2.5/weather?")
    Single<WeatherResponse> getCurrentWeatherData(@Query("q") String cityName, @Query("APPID") String app_id , @Query("units") String metric);

    @GET("data/2.5/group?&units=metric")
    Single<WeatherResponseParent> getCurrentWeathersForCities(@Query("id") String cityIds, @Query("APPID") String app_id);
}
