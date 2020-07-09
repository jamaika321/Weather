package com.kamaz.weatherApp.room;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kamaz.weatherApp.pojo.Clouds;
import com.kamaz.weatherApp.pojo.Coord;
import com.kamaz.weatherApp.pojo.Main;
import com.kamaz.weatherApp.pojo.Rain;
import com.kamaz.weatherApp.pojo.Sys;
import com.kamaz.weatherApp.pojo.Weather;
import com.kamaz.weatherApp.pojo.WeatherResponse;
import com.kamaz.weatherApp.pojo.Wind;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Converters {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<Weather> stringToWeatherObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Weather>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String weatherObjectListToString(List<Weather> someObjects) {
        return gson.toJson(someObjects);
    }

    @TypeConverter
    public static List<WeatherResponse> stringToWeatherResponseObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Weather>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String weatherResponseObjectListToString(List<WeatherResponse> someObjects) {
        return gson.toJson(someObjects);
    }

    @TypeConverter
    public static Coord stringToCoordObjectList(String data) {
        if (data == null) {
            return new Coord();
        }

        Type type = new TypeToken<Coord>() {}.getType();

        return gson.fromJson(data, type);
    }

    @TypeConverter
    public static String coordObjectListToString(Coord someObjects) {
        return gson.toJson(someObjects);
    }

    @TypeConverter
    public static Sys stringToSysObjectList(String data) {
        if (data == null) {
            return new Sys();
        }

        Type type = new TypeToken<Sys>() {}.getType();

        return gson.fromJson(data, type);
    }

    @TypeConverter
    public static String sysObjectListToString(Sys someObjects) {
        return gson.toJson(someObjects);
    }

    @TypeConverter
    public static Main stringToMainObjectList(String data) {
        if (data == null) {
            return new Main();
        }

        Type type = new TypeToken<Main>() {}.getType();

        return gson.fromJson(data, type);
    }

    @TypeConverter
    public static String mainObjectListToString(Main someObjects) {
        return gson.toJson(someObjects);
    }

    @TypeConverter
    public static Wind stringToWindObjectList(String data) {
        if (data == null) {
            return new Wind();
        }

        Type type = new TypeToken<Wind>() {}.getType();

        return gson.fromJson(data, type);
    }

    @TypeConverter
    public static String windObjectListToString(Wind someObjects) {
        return gson.toJson(someObjects);
    }

    @TypeConverter
    public static Rain stringToRainObjectList(String data) {
        if (data == null) {
            return new Rain();
        }

        Type type = new TypeToken<Rain>() {}.getType();

        return gson.fromJson(data, type);
    }

    @TypeConverter
    public static String rainObjectListToString(Rain someObjects) {
        return gson.toJson(someObjects);
    }

    @TypeConverter
    public static Clouds stringToCloudsObjectList(String data) {
        if (data == null) {
            return new Clouds();
        }

        Type type = new TypeToken<Clouds>() {}.getType();

        return gson.fromJson(data, type);
    }

    @TypeConverter
    public static String cloudsObjectListToString(Clouds someObjects) {
        return gson.toJson(someObjects);
    }
}
