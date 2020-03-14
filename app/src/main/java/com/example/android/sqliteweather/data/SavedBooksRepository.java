package com.example.android.sqliteweather.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.sqliteweather.home.LoadBookTask;
import com.example.android.sqliteweather.utils.GoogleBooksUtils;
import com.example.android.sqliteweather.utils.NetworkUtils;

import java.io.IOException;
import java.util.List;

public class SavedBooksRepository {
    private SavedBooksDao mSavedBooksDao;

    public SavedBooksRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mSavedBooksDao = db.savedBooksDao();
    }

    public void insertBook(BookEntity b) {
        new SavedBooksRepository.InsertBookAsyncTask(mSavedBooksDao).execute(b);
    }

    public LiveData<List<BookEntity>> getAllBooks() {
        return mSavedBooksDao.getAllBooks();
    }

    public void deleteBook(BookEntity b) {
        new SavedBooksRepository.DeleteBookAsyncTask(mSavedBooksDao).execute(b);
    }

    private static class InsertBookAsyncTask
            extends AsyncTask<BookEntity, Void, Void> {
        private SavedBooksDao mAsyncTaskDao;

        InsertBookAsyncTask(SavedBooksDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(BookEntity... bs) {
            mAsyncTaskDao.insert(bs[0]);
            return null;
        }
    }

    private static class DeleteBookAsyncTask
            extends AsyncTask<BookEntity, Void, Void> {
        private SavedBooksDao mAsyncTaskDao;

        DeleteBookAsyncTask(SavedBooksDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(BookEntity... bs) {
            mAsyncTaskDao.delete(bs[0]);
            return null;
        }
    }
}
