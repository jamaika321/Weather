package com.kamaz.weatherApp.pojo;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

@Entity
public class Rain {
    @SerializedName("3h")
    public float h3;
}
