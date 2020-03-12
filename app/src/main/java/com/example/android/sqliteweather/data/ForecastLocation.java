package com.example.android.sqliteweather.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "locations")
public class ForecastLocation implements Serializable {
    @PrimaryKey
    @NonNull
    public String loc_name;

    public ForecastLocation(@NonNull String loc){
        loc_name = loc;
    }

    public ForecastLocation(){

    }
}
