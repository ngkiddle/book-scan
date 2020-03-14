package com.example.android.sqliteweather.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.sqliteweather.utils.GoogleBooksUtils;

public class BookRepository{

    private MutableLiveData<BooksResponse> mCurrBook;
    private MutableLiveData<Status> mLoadingStatus;


    public BookRepository(){
        mCurrBook = new MutableLiveData<>();
        mCurrBook.setValue(null);
        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);
    }



    public LiveData<BooksResponse> getBook() {
        return mCurrBook;
    }
    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

}
