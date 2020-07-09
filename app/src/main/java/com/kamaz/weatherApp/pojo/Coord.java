package com.kamaz.weatherApp.pojo;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

@Entity
public class Coord {
    @SerializedName("lon")
    public float lon;
    @SerializedName("lat")
    public float lat;
}
