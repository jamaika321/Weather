package com.kamaz.weatherApp.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponseParent {
    @SerializedName("cnt")
    public Integer cnt;
    @SerializedName("list")
    public List<WeatherResponse> list;
}
