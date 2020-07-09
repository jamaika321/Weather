package com.kamaz.weatherApp.pojo;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

@Entity
public class Clouds {
    @SerializedName("all")
    public float all;
}
