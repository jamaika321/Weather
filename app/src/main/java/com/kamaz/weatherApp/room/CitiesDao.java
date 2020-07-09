package com.kamaz.weatherApp.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kamaz.weatherApp.pojo.WeatherResponse;

import java.util.List;

@Dao
public interface CitiesDao {

    @Query("SELECT * FROM weatherresponse")
    List<WeatherResponse> getAll();

    @Query("SELECT * FROM weatherresponse WHERE id = :id")
    WeatherResponse getById(long id);

    @Insert
    void insert(WeatherResponse employee);

    @Update
    void update(WeatherResponse employee);

    @Delete
    void delete(WeatherResponse employee);

}
