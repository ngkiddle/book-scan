package com.example.android.sqliteweather;

import androidx.annotation.NonNull;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.sqliteweather.data.BookEntity;
import com.example.android.sqliteweather.data.Status;
import com.example.android.sqliteweather.utils.OpenWeatherMapUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements
        BooksAdapter.OnBookItemClickListener,
        LoadBookTask.AsyncCallback {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mBookItemsRV;
    private SavedBooksViewModel mSavedBooksViewModel;
    private BooksAdapter mBooksAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* SQLITE CHANGES */
        mBookItemsRV = findViewById(R.id.rv_book_items);
        Log.d("setAdapter", "9780605039070");
        mBooksAdapter = new BooksAdapter (this);
        mBookItemsRV.setAdapter(mBooksAdapter);
        mBookItemsRV.setLayoutManager(new LinearLayoutManager(this));
        mBookItemsRV.setHasFixedSize(true);
        mSavedBooksViewModel =
                new ViewModelProvider(
                        this,
                        new ViewModelProvider.AndroidViewModelFactory(
                                getApplication()
                        )
                ).get(SavedBooksViewModel.class);

        mSavedBooksViewModel.getAllBooks().observe(this, new Observer<List<BookEntity>>() {
            @Override
            public void onChanged(List<BookEntity> books) {
                if(books != null) {
                    Log.d(TAG, "adding nav items: " + books.size());
                    mBooksAdapter.updateBookItems(books);
                }
            }
        });

        String u = OpenWeatherMapUtils.buildGBurl("9780605039070");
        new LoadBookTask(u, this).execute();
        /*END CHANGES*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBookItemClick(BookEntity book) {

    }

    @Override
    public void onBookFinished(BookEntity res) {
        mSavedBooksViewModel.insertBook(res);
    }
}
