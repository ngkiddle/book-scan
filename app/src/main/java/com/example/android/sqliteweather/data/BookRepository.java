package com.example.android.sqliteweather.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.sqliteweather.utils.OpenWeatherMapUtils;

public class BookRepository implements LoadBookTask.AsyncCallback {

    private MutableLiveData<BooksResponse> mCurrBook;
    private MutableLiveData<Status> mLoadingStatus;


    public BookRepository(){
        mCurrBook = new MutableLiveData<>();
        mCurrBook.setValue(null);
        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);
    }

    public void loadBooks(String isbn){
        String u = OpenWeatherMapUtils.buildGBurl(isbn);
        new LoadBookTask(u, this).execute();
    }

    public LiveData<BooksResponse> getBook() {
        return mCurrBook;
    }
    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    @Override
    public void onBookFinished(BooksResponse res) {
        mCurrBook.setValue(res);
        if (mCurrBook != null) {
            mLoadingStatus.setValue(Status.SUCCESS);
        } else {
            mLoadingStatus.setValue(Status.ERROR);
        }
    }
}
