package com.example.android.sqliteweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.sqliteweather.data.BooksResponse;
import com.example.android.sqliteweather.data.Status;
import com.example.android.sqliteweather.utils.GoogleBooksUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements
        SharedPreferences.OnSharedPreferenceChangeListener,
        BooksAdapter.OnBookItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mForecastLocationTV;
    private RecyclerView mForecastItemsRV;
    private RecyclerView mLocationItemsRV;
    private RecyclerView mBookItemsRV;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;
    private DrawerLayout mDrawerLayout;

    private BooksAdapter mBooksAdapter;
    private BookViewModel mBookViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);



        /* SQLITE CHANGES */

        mBookItemsRV = findViewById(R.id.rv_book_items);
        mBooksAdapter = new BooksAdapter (this);
        mBookItemsRV.setAdapter(mBooksAdapter);
        mBookItemsRV.setLayoutManager(new LinearLayoutManager(this));
        mBookItemsRV.setHasFixedSize(true);

        mBookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        mBookViewModel.loadBook("9780590353427");
        mBookViewModel.loadBook("0735619670");



        LiveData<BooksResponse> book =
                mBookViewModel.getBook();

        book.observe(this, new Observer<BooksResponse>() {
            @Override
            public void
            onChanged(@Nullable BooksResponse book) {
                if(book != null){
                    mBooksAdapter.updateBookItems(book);
                }
            }
        });


        /*END CHANGES*/

        // Remove shadow under action bar.
        //getSupportActionBar().setElevation(0);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadForecast(SharedPreferences preferences) {
        String location = preferences.getString(
                getString(R.string.pref_location_key),
                getString(R.string.pref_location_default_value)
        );
        String units = preferences.getString(
                getString(R.string.pref_units_key),
                getString(R.string.pref_units_default_value)
        );

        mForecastLocationTV.setText(location);
       // mForecastViewModel.loadForecast(location, units);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        loadForecast(sharedPreferences);
    }


    @Override
    public void onBookItemClick(BooksResponse forecastItem) {

    }
}
