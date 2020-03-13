package com.example.android.sqliteweather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.sqliteweather.data.BookRepository;
import com.example.android.sqliteweather.data.BooksResponse;
import com.example.android.sqliteweather.data.ForecastItem;
import com.example.android.sqliteweather.data.ForecastRepository;
import com.example.android.sqliteweather.data.Status;

import java.util.List;

public class BookViewModel extends ViewModel {

    private LiveData<BooksResponse> mBook;
    private LiveData<Status> mLoadingStatus;

    private BookRepository mRepository;

    public BookViewModel() {
        mRepository = new BookRepository();
        mBook = mRepository.getBook();
        mLoadingStatus = mRepository.getLoadingStatus();
    }

    public void loadForecast(String isbn) {
        mRepository.loadBooks(isbn);
    }

    public LiveData<BooksResponse> getBook() {
        return mBook;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }
}
