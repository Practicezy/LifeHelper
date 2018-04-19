package com.example.r.lifehelper.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.r.lifehelper.R;
import com.example.r.lifehelper.bean.Book;
import com.example.r.lifehelper.bean.BookLab;
import com.example.r.lifehelper.utils.ImageLoader;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookListHolder> {
    private List<Book> mBookList;
    private Context mContext;

    public BookListAdapter(List<Book> bookList, Context context) {
        mBookList = bookList;
        mContext = context;
    }

    @NonNull
    @Override
    public BookListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_book,parent,false);
        BookListHolder holder = new BookListHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookListHolder holder, int position) {
        Book book = mBookList.get(position);
        holder.tvTile.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());
        holder.ivImg.setTag(book.getImageUrl());
        ImageLoader imageLoader = new ImageLoader(mContext);
        imageLoader.loadBitmapByThread(holder.ivImg,book.getImageUrl(),1000,1000);
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    public void updateAdapter(List<Book> books){
        mBookList = books;
        BookLab.getBookLab(mContext).updateBookLab(mBookList);
        notifyDataSetChanged();
    }

    class BookListHolder extends RecyclerView.ViewHolder{
        private ImageView ivImg;
        private TextView tvTile, tvAuthor;
        public BookListHolder(View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.book_image_large);
            tvTile = itemView.findViewById(R.id.book_title);
            tvAuthor = itemView.findViewById(R.id.book_author);
        }
    }
}
