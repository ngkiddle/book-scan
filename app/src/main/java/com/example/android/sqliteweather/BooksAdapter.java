package com.example.android.sqliteweather;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.sqliteweather.data.BooksResponse;

import java.util.ArrayList;
import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    private List<BooksResponse> mBookItems;
    private BooksAdapter.OnBookItemClickListener mLocationItemClickListener;

    public interface OnBookItemClickListener {
        void onBookItemClick(BooksResponse forecastItem);
    }

    public BooksAdapter(BooksAdapter.OnBookItemClickListener clickListener) {
        mLocationItemClickListener = clickListener;
        mBookItems = new ArrayList<>();
    }

    public void updateBookItems(BooksResponse book) {
        mBookItems.add(book);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mBookItems != null) {
            return mBookItems.size();
        } else {
            return 0;
        }
    }

    @Override
    public BooksAdapter.BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.book_list_item, parent, false);
        return new BooksAdapter.BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.bind(mBookItems.get(position));
    }



    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitle;
        private TextView mAuthor;
        private ImageView mBookIcon;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.tv_book_title);
            mAuthor = itemView.findViewById(R.id.tv_book_author);
            mBookIcon = itemView.findViewById(R.id.iv_book_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            BooksResponse book = mBookItems.get(getAdapterPosition());
            mLocationItemClickListener.onBookItemClick(book);
        }

        public void bind(BooksResponse book) {
            mTitle.setText(book.items[0].volumeInfo.title);
            mAuthor.setText(book.items[0].volumeInfo.authors[0]);
            Log.d("Nice", book.items[0].volumeInfo.imageLinks.thumbnail);
            Glide.with(mBookIcon).load(book.items[0].volumeInfo.imageLinks.thumbnail).into(mBookIcon);
        }
    }
}
