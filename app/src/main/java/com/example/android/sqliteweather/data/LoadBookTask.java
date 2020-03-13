package com.example.android.sqliteweather.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.sqliteweather.utils.NetworkUtils;
import com.example.android.sqliteweather.utils.OpenWeatherMapUtils;

import java.io.IOException;
import java.util.ArrayList;

/*
 * This is our AsyncTask for loading forecast data from OWM.  It mirrors the AsyncTask we used
 * previously for loading the forecast, except here, we specify an interface AsyncCallback to
 * provide the functionality to be performed in the main thread when the task is finished.
 * This is needed because, to avoid memory leaks, the AsyncTask class is no longer an inner class,
 * so it can no longer directly access the fields it needs to update when loading is finished.
 * Instead, we provide a callback function (using AsyncCallback) to perform those updates.
 */
class LoadBookTask extends AsyncTask<Void, Void, String> {

    public interface AsyncCallback {
        void onBookFinished(GoodreadsResponse res);
    }

    private String mURL;
    private AsyncCallback mCallback;

    LoadBookTask(String url, AsyncCallback callback) {
        mURL = url;
        mCallback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String resXML = null;
        try {
            resXML = NetworkUtils.doHTTPGet(mURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resXML;
    }

    @Override
    protected void onPostExecute(String s) {

        GoodreadsResponse res = null;
        try {
            res = OpenWeatherMapUtils.parseBookXML(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        if (s != null) {
                //res = OpenWeatherMapUtils.parseForecastJSON(s);
            try {
                res = OpenWeatherMapUtils.parseBookXML(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        Log.d("woot", res.getBook());
        mCallback.onBookFinished(res);
    }
}
