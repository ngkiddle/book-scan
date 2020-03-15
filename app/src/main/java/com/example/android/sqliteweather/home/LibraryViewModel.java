package com.example.android.sqliteweather.home;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.sqliteweather.data.BookEntity;
import com.example.android.sqliteweather.data.SavedBooksRepository;

import java.util.List;

public class LibraryViewModel extends AndroidViewModel {
    private SavedBooksRepository mSavedBookRepository;

    public LibraryViewModel(Application application) {
        super(application);
        mSavedBookRepository =
                new SavedBooksRepository(application);
    }

    public void insertBook(BookEntity b) {
        mSavedBookRepository.insertBook(b);
        Log.d("LibraryViewModel", "Storing in DB");

    }

    public LiveData<List<BookEntity>> getAllBooks() {
        return mSavedBookRepository.getAllBooks();
    }

    public void deleteBook(BookEntity b) {
        mSavedBookRepository.deleteBook(b);
    }
}
