package com.example.android.sqliteweather;

import android.os.AsyncTask;

import com.example.android.sqliteweather.data.BookEntity;
import com.example.android.sqliteweather.utils.NetworkUtils;
import com.example.android.sqliteweather.utils.GoogleBooksUtils;

import java.io.IOException;

/*
 * This is our AsyncTask for loading forecast data from OWM.  It mirrors the AsyncTask we used
 * previously for loading the forecast, except here, we specify an interface AsyncCallback to
 * provide the functionality to be performed in the main thread when the task is finished.
 * This is needed because, to avoid memory leaks, the AsyncTask class is no longer an inner class,
 * so it can no longer directly access the fields it needs to update when loading is finished.
 * Instead, we provide a callback function (using AsyncCallback) to perform those updates.
 */
public class LoadBookTask extends AsyncTask<Void, Void, String> {

    public interface AsyncCallback {
        void onBookFinished(BookEntity res);
    }

    private String mURL;
    private AsyncCallback mCallback;

    public LoadBookTask(String url, AsyncCallback callback) {
        mURL = url;
        mCallback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String res = null;
        try {
            res = NetworkUtils.doHTTPGet(mURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    protected void onPostExecute(String s) {
        BookEntity res = null;
        try {
            res = GoogleBooksUtils.parseBookJSON(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mCallback.onBookFinished(res);
    }
}
