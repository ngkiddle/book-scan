package com.example.android.sqliteweather.bookdetail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.sqliteweather.R;

import java.util.ArrayList;
import java.util.List;

public class BookDetailActivity extends AppCompatActivity {
    public static final String EXTRA_ISBN = "barcodetest.main2activity.extra_isbn";
    public static final String EXTRA_ID = "barcodetest.main2activity.extra_id";
    private Button mActionB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        mActionB = findViewById(R.id.action_b);

        Bundle extras = getIntent().getExtras();
        String isbn = "[barcode]";
        int id = -1;

        if (extras != null) {
            isbn = extras.getString(EXTRA_ISBN);
            id = extras.getInt(EXTRA_ID, -1);
        }

        if(id > 0) {
            // From list activity, get from database

            mActionB.setText("Share");
            mActionB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO share intent
                }
            });
        } else {
            // From ScanActivity, get from API
            mActionB.setText("Save to Library");
            testPop(isbn);
            mActionB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO save to database
                }
            });
        }


    }

    private void testPop(String isbn) {
        List<String> authors = new ArrayList<>();
        authors.add("Ian Collier");
        Book book = new Book(isbn, "Sample Book", "This is a sample book. Lorem ipsum.", "https://upload.asdasdasdasdsadasdasd.org/wikipedia/commons/a/ac/NewTux.png", authors);

        populateViews(book);
    }

    private void populateViews(Book book) {
        TextView titleTV, authorTV, descriptionTV, isbnTV;
        ImageView coverIV;

        titleTV = findViewById(R.id.book_title_tv);
        authorTV = findViewById(R.id.book_author_tv);
        descriptionTV = findViewById(R.id.book_description_tv);
        isbnTV = findViewById(R.id.book_isbn_tv);
        coverIV = findViewById(R.id.book_cover_iv);

        titleTV.setText(book.title);
        isbnTV.setText(book.isbn);
        descriptionTV.setText(book.description);

        // Format author set
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : book.authors) {
            stringBuilder.append(s);
            stringBuilder.append(",");
        }
        if(stringBuilder.length() > 0) stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        authorTV.setText(stringBuilder);

        Glide.with(this)
                .load(book.imageLink)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder_cover_2)
                        .fitCenter()
                )
                .into(coverIV);

    }
}
