package com.example.android.sqliteweather.activity;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable {
    public String title, description, imageLink, isbn;
    public List<String> authors;

    public Book(String isbn, String title, String description, String imageLink, List<String> authors) {
        this.title = title;
        this.description = description;
        this.imageLink = imageLink;
        this.isbn = isbn;
        this.authors = authors;
    }
}
