package com.example.android.sqliteweather.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Books")
public class BookEntity implements Serializable {
    @PrimaryKey
    @NonNull
    public String isbn;

    public String title;
    public String authors;
    public String Description;
    public String imageLinks;
    public BookEntity(@NonNull String isbn, String title, String authors, String Description, String imageLinks){
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.Description = Description;
        this.imageLinks = imageLinks;
    }

    @Ignore
    public BookEntity(){

    }
}
