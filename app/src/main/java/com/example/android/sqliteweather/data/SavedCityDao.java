package com.example.android.sqliteweather.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SavedCityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ForecastLocation loc);

    @Query("SELECT * FROM locations")
    LiveData<List<ForecastLocation>> getAllCities();

    @Delete
    void delete(ForecastLocation loc);
}
