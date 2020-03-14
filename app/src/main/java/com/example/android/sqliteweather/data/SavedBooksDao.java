package com.example.android.sqliteweather.data;

import androidx.lifecycle.LiveData;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SavedBooksDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(BookEntity b);

    @Query("SELECT * FROM books")
    LiveData<List<BookEntity>> getAllBooks();

    @Delete
    void delete(BookEntity b);
}
