package com.kamaz.weatherApp.pojo;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

@Entity
public class Sys {
    @SerializedName("country")
    public String country;
    @SerializedName("sunrise")
    public long sunrise;
    @SerializedName("sunset")
    public long sunset;
}
