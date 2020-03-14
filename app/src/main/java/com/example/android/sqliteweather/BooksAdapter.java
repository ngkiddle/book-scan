package com.example.android.sqliteweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.sqliteweather.data.BookEntity;
import com.example.android.sqliteweather.data.BooksResponse;

import java.util.ArrayList;
import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    private List<BookEntity> mBookItems;
    private BooksAdapter.OnBookItemClickListener mBookItemClickListener;

    public interface OnBookItemClickListener {
        void onBookItemClick(BookEntity book);
    }

    public BooksAdapter(BooksAdapter.OnBookItemClickListener clickListener) {
        mBookItemClickListener = clickListener;
    }

    public void updateBookItems(List<BookEntity> books) {
            mBookItems = books;
            notifyDataSetChanged();
    }

    public void addBookItem(BookEntity book) {
        if(book != null) {
            mBookItems.add(book);
            notifyDataSetChanged();
        }
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
            BookEntity book = mBookItems.get(getAdapterPosition());
            mBookItemClickListener.onBookItemClick(book);
        }

        public void bind(BookEntity book) {
            if (book != null) {
                mTitle.setText(book.title);
                mAuthor.setText(book.authors);
                Glide.with(mBookIcon.getContext()).load(book.imageLinks).into(mBookIcon);
            }
        }
    }
}
