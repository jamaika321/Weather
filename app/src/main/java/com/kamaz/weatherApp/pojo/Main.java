package com.kamaz.weatherApp.pojo;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

@Entity
public class Main {
    @SerializedName("temp")
    public float temp;
    @SerializedName("humidity")
    public float humidity;
    @SerializedName("pressure")
    public float pressure;
    @SerializedName("temp_min")
    public float temp_min;
    @SerializedName("temp_max")
    public float temp_max;
}
