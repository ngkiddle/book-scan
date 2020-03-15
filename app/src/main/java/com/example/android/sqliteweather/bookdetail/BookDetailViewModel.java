package com.example.android.sqliteweather.bookdetail;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.sqliteweather.MainActivity;
import com.example.android.sqliteweather.data.BookEntity;
import com.example.android.sqliteweather.home.HomeFragment;
import com.example.android.sqliteweather.utils.GoogleBooksUtils;
import com.example.android.sqliteweather.utils.NetworkUtils;
import com.example.android.sqliteweather.home.LibraryViewModel;

import java.io.IOException;

public class BookDetailViewModel extends ViewModel {
    private MutableLiveData<BookEntity> mBookEntity;
    private MutableLiveData<Boolean> mError;
    private MutableLiveData<String> mButtonText;


    public MutableLiveData<String> getButtonText() {
        return mButtonText;
    }

    public void setButtonText(String buttonText) {
        mButtonText.setValue(buttonText);
    }

    public BookDetailViewModel() {
        super();
        mBookEntity = new MutableLiveData<>();
        mError = new MutableLiveData<>();
        mButtonText = new MutableLiveData<>();
        mButtonText.setValue("");
        mError.setValue(false);

    }

    public MutableLiveData<BookEntity> getBookEntity() {
        return mBookEntity;
    }

    public MutableLiveData<Boolean> getError() {
        return mError;
    }

    public void fetchCachedBook(String isbn) {
        new FetchBookAsyncTask(isbn, new FetchBookAsyncTask.Callback() {
            @Override
            public void onFinish(BookEntity bookEntity) {
                mBookEntity.setValue(bookEntity);
            }
            public void onError() {
                mError.setValue(true);
            }
        }).execute();
    }

    public void fetchBook(String isbn, final LibraryViewModel mLibraryViewModel) {
        new FetchBookAsyncTask(isbn, new FetchBookAsyncTask.Callback() {
            @Override
            public void onFinish(BookEntity bookEntity) {
                mBookEntity.setValue(bookEntity);
                mLibraryViewModel.insertBook(bookEntity);
                Log.d("bookDetail", "Storing in DB");
            }
            public void onError() {
                mError.setValue(true);
            }
        }).execute();
    }

    private static class FetchBookAsyncTask extends AsyncTask<Void, Void, String> {
        interface Callback {
            void onFinish(BookEntity bookEntity);
            void onError();
        }

        private String mURL;
        private FetchBookAsyncTask.Callback mCallback;

        public FetchBookAsyncTask(String isbn, FetchBookAsyncTask.Callback callback) {
            mCallback = callback;
            mURL = GoogleBooksUtils.buildGBurl(isbn);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String res = null;
            try {
                res = NetworkUtils.doHTTPGet(mURL);
            } catch (IOException e) {
                e.printStackTrace();
                mCallback.onError();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                mCallback.onFinish(GoogleBooksUtils.parseBookJSON(s));
            } catch (Exception e) {
                e.printStackTrace();
                mCallback.onError();
            }
        }
    }
}
