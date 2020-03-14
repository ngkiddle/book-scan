package com.example.android.sqliteweather;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.sqliteweather.data.BookEntity;
import com.example.android.sqliteweather.data.SavedBooksRepository;

import java.util.List;

public class SavedBooksViewModel extends AndroidViewModel {
    private SavedBooksRepository mSavedBookRepository;

    public SavedBooksViewModel(Application application) {
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
}
