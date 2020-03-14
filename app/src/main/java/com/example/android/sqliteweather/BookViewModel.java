package com.example.android.sqliteweather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.sqliteweather.data.BookRepository;
import com.example.android.sqliteweather.data.BooksResponse;
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

    public void loadBook(String isbn) {
        mRepository.loadBooks(isbn);
    }

    public LiveData<BooksResponse> getBook() {
        return mBook;
    }

    public String getTitle() { return mBook.getValue().items[0].volumeInfo.title; }
    public String getAuthor() { return mBook.getValue().items[0].volumeInfo.authors[0]; }
    public String getDescription() { return mBook.getValue().items[0].volumeInfo.Description; }
    public String getIconURL() { return mBook.getValue().items[0].volumeInfo.imageLinks.thumbnail; }


    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }
}
