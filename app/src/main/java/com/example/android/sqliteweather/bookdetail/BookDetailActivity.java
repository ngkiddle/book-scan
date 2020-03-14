package com.example.android.sqliteweather.bookdetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.sqliteweather.R;
import com.example.android.sqliteweather.data.BookEntity;
import com.example.android.sqliteweather.home.LibraryViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class BookDetailActivity extends AppCompatActivity {
    public static final String EXTRA_ISBN = "barcodetest.main2activity.extra_isbn";
    public static final String EXTRA_FETCH_FROM_API = "barcodetest.main2activity.extra_fetch_from_api";
    private Button mActionB;
    private BookDetailViewModel mViewModel;
    private LinearLayout mDetailsLL;
    private ProgressBar mLoadingPB;
    private LibraryViewModel mLibraryViewModel;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_book_detail);
        mViewModel = new ViewModelProvider(this).get(BookDetailViewModel.class);
        mLibraryViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(LibraryViewModel.class);

        mActionB = findViewById(R.id.action_b);
        mLoadingPB = findViewById(R.id.book_loading_pb);
        mDetailsLL = findViewById(R.id.book_details_rl);

        Bundle extras = getIntent().getExtras();
        String isbn = "";
        boolean fetchFromApi = false;

        if (extras != null) {
            isbn = extras.getString(EXTRA_ISBN);
            fetchFromApi = extras.getBoolean(EXTRA_FETCH_FROM_API, false);
        }

        if(!fetchFromApi) {
            // From list activity, get from database

            mViewModel.setButtonText("Share Book");
            mActionB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "foo");
                    shareIntent.setType("text/plain");

                    Intent chooserIntent = Intent.createChooser(shareIntent, null);
                    startActivity(chooserIntent);
                }
            });
        } else {
            // From ScanActivity, get from API
            mViewModel.setButtonText("Save to Library");
            mActionB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO save to database
                    onBackPressed();
                }
            });
            mViewModel.fetchBook("9780060531041");
            toggleProgressBar(true);
        }

        mViewModel.getButtonText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mActionB.setText(s);
            }
        });

        mViewModel.getBookEntity().observe(this, new Observer<BookEntity>() {
            @Override
            public void onChanged(BookEntity bookEntity) {
                if(bookEntity != null) {
                    populateViews(bookEntity);
                    toggleProgressBar(false);
                    mLibraryViewModel.insertBook(bookEntity);
                }
            }
        });

        mViewModel.getError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean err) {
                if(err) {
                    toggleProgressBar(false);
                    mActionB.setVisibility(View.GONE);
                    Toast.makeText(BookDetailActivity.this, "Book not found.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void toggleProgressBar(boolean isVisible) {
        if(isVisible) {
            mDetailsLL.setVisibility(View.GONE);
            mLoadingPB.setVisibility(View.VISIBLE);
        } else {
            mDetailsLL.setVisibility(View.VISIBLE);
            mLoadingPB.setVisibility(View.GONE);
        }
    }

    private void populateViews(BookEntity book) {
        TextView titleTV, authorTV, descriptionTV, isbnTV;
        ImageView coverIV;

        titleTV = findViewById(R.id.book_title_tv);
        authorTV = findViewById(R.id.book_author_tv);
        descriptionTV = findViewById(R.id.book_description_tv);
        isbnTV = findViewById(R.id.book_isbn_tv);
        coverIV = findViewById(R.id.book_cover_iv);

        titleTV.setText(book.title);
        isbnTV.setText(book.isbn);
        descriptionTV.setText(book.Description);

        // Format author set
        /*
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : book.authors) {
            stringBuilder.append(s);
            stringBuilder.append(",");
        }
        if(stringBuilder.length() > 0) stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        authorTV.setText(stringBuilder);
        */
        authorTV.setText(book.authors);

        Glide.with(this)
                .load(book.imageLinks)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder_cover_2)
                        .fitCenter()
                )
                .into(coverIV);
    }
}
