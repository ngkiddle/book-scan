package com.example.android.sqliteweather.home;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.sqliteweather.MainActivity;
import com.example.android.sqliteweather.R;
import com.example.android.sqliteweather.bookdetail.BookDetailActivity;
import com.example.android.sqliteweather.bookdetail.BookDetailViewModel;
import com.example.android.sqliteweather.data.BookEntity;
import com.example.android.sqliteweather.utils.GoogleBooksUtils;
import com.example.android.sqliteweather.utils.NetworkUtils;

import java.io.IOException;
import java.util.List;


public class HomeFragment extends Fragment implements LibraryAdapter.OnBookItemClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mBookItemsRV;
    public LibraryViewModel mLibraryViewModel;
    private LibraryAdapter mLibraryAdapter;
    private Button mNoBooksB;
    private FrameLayout mNoBooksFL;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mNoBooksFL = root.findViewById(R.id.home_nobooks_fl);
        mNoBooksB = root.findViewById(R.id.home_nobooks_b);

        mBookItemsRV = root.findViewById(R.id.rv_book_items);
        Log.d("setAdapter", "9780605039070");
        mLibraryAdapter = new LibraryAdapter(this);
        mBookItemsRV.setAdapter(mLibraryAdapter);
        mBookItemsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mBookItemsRV.setHasFixedSize(true);
        mLibraryViewModel =
                new ViewModelProvider(
                        this,
                        new ViewModelProvider.AndroidViewModelFactory(
                                getActivity().getApplication()
                        )
                ).get(LibraryViewModel.class);

        mLibraryViewModel.getAllBooks().observe(getViewLifecycleOwner(), new Observer<List<BookEntity>>() {
            @Override
            public void onChanged(List<BookEntity> books) {
                if(books != null) {
                    Log.d(TAG, "adding nav items: " + books.size());
                    mLibraryAdapter.updateBookItems(books);

                    if(books.size() == 0) {
                        mNoBooksFL.setVisibility(View.VISIBLE);
                    } else {
                        mNoBooksFL.setVisibility(View.GONE);
                    }
                }
            }
        });

        mNoBooksB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = getActivity();
                if(activity != null) ((MainActivity)activity).navigate(R.id.navigation_scan);
            }
        });

        return root;
    }

    @Override
    public void onBookItemClick(BookEntity book) {
        Intent intent = new Intent(getContext(), BookDetailActivity.class);
        intent.putExtra(BookDetailActivity.EXTRA_ISBN, book.isbn);
        getContext().startActivity(intent);
    }
}
