package com.example.android.sqliteweather.data;

import android.os.AsyncTask;

import com.example.android.sqliteweather.utils.NetworkUtils;
import com.example.android.sqliteweather.utils.GoogleBooksUtils;

import java.io.IOException;


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
