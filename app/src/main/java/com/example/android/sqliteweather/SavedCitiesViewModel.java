package com.example.android.sqliteweather;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.sqliteweather.data.ForecastLocation;
import com.example.android.sqliteweather.data.SavedCityRepository;

import java.util.List;

public class SavedCitiesViewModel extends AndroidViewModel {
    private SavedCityRepository mSavedCityRepository;

    public SavedCitiesViewModel(Application application) {
        super(application);
        mSavedCityRepository =
                new SavedCityRepository(application);
    }

    public void insertLocation(ForecastLocation loc) {
        mSavedCityRepository.insertCity(loc);
    }

    public LiveData<List<ForecastLocation>> getAllLocations() {
        return mSavedCityRepository.getAllLocations();
    }

    public void deleteLocation(ForecastLocation loc) {
        mSavedCityRepository.deleteCity(loc);
    }
}
