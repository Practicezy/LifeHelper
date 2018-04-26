package com.example.r.lifehelper.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.r.lifehelper.BookActivity;
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

    /*创建条目视图*/
    @NonNull
    @Override
    public BookListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_book,parent,false);
        BookListHolder holder = new BookListHolder(view);
        return holder;
    }

    /*绑定数据*/
    @Override
    public void onBindViewHolder(@NonNull BookListHolder holder, int position) {
        Book book = mBookList.get(position);
        holder.bindHolder(book);
        holder.bindHolderClick(book);
    }

    /*视图数目*/
    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    /*刷新列表内容和视图*/
    public void updateAdapter(List<Book> books){
        mBookList = books;
        BookLab.getBookLab(mContext).updateBookList(mBookList);
        notifyDataSetChanged();
    }

    /*添加列表内容和新视图*/
    public void insertItems(int position,List<Book> books){
        for (int i = 0; i < books.size(); i++) {
            mBookList.add(position + i,books.get(i));
            notifyItemInserted(position + i);
            notifyItemChanged(position,mBookList.size() - (position + i));
        }
    }

    class BookListHolder extends RecyclerView.ViewHolder{
        private ImageView ivImg;
        private TextView tvTile,tvAuthor,tvSummary;
        private BookListHolder(View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.item_book_image_large);
            tvTile = itemView.findViewById(R.id.item_book_title);
            tvAuthor = itemView.findViewById(R.id.item_book_author);
            tvSummary = itemView.findViewById(R.id.item_book_summary);
        }

        private void bindHolder(Book book){
            tvTile.setText(book.getTitle());
            tvAuthor.setText(book.getAuthor());
            tvSummary.setText(book.getSummary());
            ivImg.setTag(book.getImageUrl());
            ImageLoader imageLoader = new ImageLoader(mContext);
            imageLoader.loadBitmapByThread(ivImg,book.getImageUrl(),300,300);
        }

        private void bindHolderClick(final Book book){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = BookActivity.newIntent(mContext, book.getTitle());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
