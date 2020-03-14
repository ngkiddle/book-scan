package com.example.android.sqliteweather.home;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.sqliteweather.data.BookEntity;
import com.example.android.sqliteweather.data.LoadBookTask;
import com.example.android.sqliteweather.data.SavedBooksRepository;
import com.example.android.sqliteweather.utils.GoogleBooksUtils;

import java.util.List;

public class LibraryViewModel extends AndroidViewModel
    implements LoadBookTask.AsyncCallback{
    private SavedBooksRepository mSavedBookRepository;

    public LibraryViewModel(Application application) {
        super(application);
        mSavedBookRepository =
                new SavedBooksRepository(application);
    }

    public void insertBook(BookEntity b) {
        mSavedBookRepository.insertBook(b);
    }

    public LiveData<List<BookEntity>> getAllBooks() {
        return mSavedBookRepository.getAllBooks();
    }

    public void deleteBook(BookEntity b) {
        mSavedBookRepository.deleteBook(b);
    }

    public void loadBook(String isbn){
        String u = GoogleBooksUtils.buildGBurl(isbn);
        new LoadBookTask(u, this).execute();
    }

    @Override
    public void onBookFinished(BookEntity res) {
        insertBook(res);
    }
}
