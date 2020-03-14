package com.example.android.sqliteweather.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.android.sqliteweather.R;
import com.example.android.sqliteweather.data.BooksResponse;
import com.google.gson.Gson;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class GoogleBooksUtils {

    public static final String EXTRA_FORECAST_ITEM = "com.example.android.sqliteweather.utils.ForecastItem";

    private final static String OWM_FORECAST_BASE_URL = "https://api.openweathermap.org/data/2.5/forecast";

    private final static String GB_BASE_URL = "https://www.googleapis.com/books/v1/volumes";
    private final static String GB_QUERY_PARAM= "q";

    private final static String OWM_ICON_URL_FORMAT_STR = "https://openweathermap.org/img/w/%s.png";
    private final static String OWM_FORECAST_QUERY_PARAM = "q";
    private final static String OWM_FORECAST_UNITS_PARAM = "units";
    private final static String OWM_FORECAST_APPID_PARAM = "appid";
    private final static String OWM_FORECAST_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final static String OWM_FORECAST_TIME_ZONE = "UTC";

    /*
     * Set your own APPID here.
     */
    private final static String OWM_FORECAST_APPID = "a8a364951c0c69ab49bd111b6c4f0756";


    /*
     * The below several classes are used only for JSON parsing with Gson.  The main class that's
     * used to represent a single forecast item throughout the rest of the app is the ForecastItem
     * class in the data package.
     */

    static class OWMForecastResults {
        OWMForecastListItem[] list;
    }

    static class OWMForecastListItem {
        String dt_txt;
        OWMForecastItemMain main;
        OWMForecastItemWeather[] weather;
        OWMForecastItemWind wind;
    }

    static class OWMForecastItemMain {
        float temp;
        float temp_min;
        float temp_max;
        float humidity;
    }

    static class OWMForecastItemWeather {
        String description;
        String icon;
    }

    static class OWMForecastItemWind {
        float speed;
        float deg;
    }

    public static String buildGBurl(String isbn) {
        return Uri.parse(GB_BASE_URL).buildUpon()
                .appendQueryParameter(GB_QUERY_PARAM, "isbn:" + isbn)
                .build()
                .toString();
    }

        public static String buildForecastURL(String forecastLocation, String temperatureUnits) {
        return Uri.parse(OWM_FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(OWM_FORECAST_QUERY_PARAM, forecastLocation)
                .appendQueryParameter(OWM_FORECAST_UNITS_PARAM, temperatureUnits)
                .appendQueryParameter(OWM_FORECAST_APPID_PARAM, OWM_FORECAST_APPID)
                .build()
                .toString();
    }

    public static String buildIconURL(String icon) {
        return String.format(OWM_ICON_URL_FORMAT_STR, icon);
    }


    public static BooksResponse parseBookJSON(String bookJSON) throws Exception {
        Gson gson = new Gson();
        BooksResponse res = gson.fromJson(bookJSON, BooksResponse.class);
        return res;
    }

}
