package com.example.android.sqliteweather.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.sqliteweather.MainActivity;
import com.example.android.sqliteweather.R;
import com.example.android.sqliteweather.data.BookEntity;
import com.example.android.sqliteweather.utils.GoogleBooksUtils;

import java.util.List;


public class HomeFragment extends Fragment implements
        LibraryAdapter.OnBookItemClickListener,
        LoadBookTask.AsyncCallback {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mBookItemsRV;
    private LibraryViewModel mLibraryViewModel;
    private LibraryAdapter mLibraryAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        /* SQLITE CHANGES */
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
                }
            }
        });

        String u = GoogleBooksUtils.buildGBurl("9780605039070");
        new LoadBookTask(u, this).execute();
        /*END CHANGES*/

        return root;
    }

    @Override
    public void onBookItemClick(BookEntity book) {

    }

    @Override
    public void onBookFinished(BookEntity res) {
        mLibraryViewModel.insertBook(res);
    }
}
