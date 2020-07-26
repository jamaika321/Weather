package com.kamaz.weatherApp.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.kamaz.weatherApp.room.Converters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class WeatherResponse {
    @SerializedName("coord")
    @TypeConverters({Converters.class})
    public Coord coord;
    @SerializedName("sys")
    @TypeConverters({Converters.class})
    public Sys sys;
    @SerializedName("weather")
    @TypeConverters({Converters.class})
    public List<Weather> weather = new ArrayList<Weather>();
    @SerializedName("main")
    @TypeConverters({Converters.class})
    public Main main;
    @SerializedName("wind")
    @TypeConverters({Converters.class})
    public Wind wind;
    @SerializedName("rain")
    @TypeConverters({Converters.class})
    public Rain rain;
    @SerializedName("clouds")
    @TypeConverters({Converters.class})
    public Clouds clouds;
    @SerializedName("dt")
    @TypeConverters({Converters.class})
    public float dt;
    @SerializedName("id")
    @PrimaryKey
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("cod")
    public float cod;
}

