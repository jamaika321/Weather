package com.kamaz.weatherApp.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.kamaz.weatherApp.pojo.WeatherResponse;

@Database(entities = {WeatherResponse.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CitiesDao citiesDao();
}
