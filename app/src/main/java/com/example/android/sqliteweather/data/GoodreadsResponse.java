package com.example.android.sqliteweather.data;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="GoodreadsResponse",strict = false)
public class GoodreadsResponse {

    @Element(name="book")
    private String book;

    public GoodreadsResponse() {
        super();
    }

    public String getBook(){
        return book;
    }
    /*
    public Book getBook() {
        return book;
    }

    public String getTitle() {
        return book.work.original_title;
    }

    public String getDescription() {
        return book.description;
    }

    public String getImgURL() {
        return book.image_url;
    }

    private class Book {
        @Element
        private int id;

        @Element
        private String image_url;

        @Element
        private Work work;

        @Element
        private String description;
    }

    private class Work {
        @Element
        private String original_title;
    }
*/

}
