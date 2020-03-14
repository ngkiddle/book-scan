package com.example.android.sqliteweather.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SavedCityRepository {
    private SavedCityDao mSavedCityDao;

    public SavedCityRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
//        mSavedCityDao = db.savedCitiesDao();
    }

    public void insertCity(ForecastLocation loc) {
        new InsertLocAsyncTask(mSavedCityDao).execute(loc);
    }

    public LiveData<List<ForecastLocation>> getAllLocations() {
        return mSavedCityDao.getAllCities();
        }

public void deleteCity(ForecastLocation loc) {
        new DeleteLocAsyncTask(mSavedCityDao).execute(loc);
        }

private static class InsertLocAsyncTask
        extends AsyncTask<ForecastLocation, Void, Void> {
    private SavedCityDao mAsyncTaskDao;

    InsertLocAsyncTask(SavedCityDao dao) {
        mAsyncTaskDao = dao;
    }
    @Override
    protected Void doInBackground(ForecastLocation... locs) {
        mAsyncTaskDao.insert(locs[0]);
        return null;
    }
}

private static class DeleteLocAsyncTask
        extends AsyncTask<ForecastLocation, Void, Void> {
    private SavedCityDao mAsyncTaskDao;

    DeleteLocAsyncTask(SavedCityDao dao) {
        mAsyncTaskDao = dao;
    }
    @Override
    protected Void doInBackground(ForecastLocation... locs) {
        mAsyncTaskDao.delete(locs[0]);
        return null;
    }
}
}
