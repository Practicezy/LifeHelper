package com.example.r.lifehelper.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.r.lifehelper.fragment.BookContentFragment;
import com.example.r.lifehelper.R;
import com.example.r.lifehelper.bean.BookChapter;

import java.util.List;

public class BookChapterAdapter extends RecyclerView.Adapter<BookChapterAdapter.ChapterHolder> {
    private List<BookChapter> mBookChapterList;
    private Context mContext;
    private String mUrl;

    public BookChapterAdapter(List<BookChapter> bookChapterList, Context context,String url) {
        mBookChapterList = bookChapterList;
        mContext = context;
        mUrl = url;
    }

    @NonNull
    @Override
    public ChapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1,parent,false);
        ChapterHolder holder = new ChapterHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterHolder holder, final int position) {
        final BookChapter bookChapter = mBookChapterList.get(position);
        holder.mTextView.setText(bookChapter.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((AppCompatActivity)mContext).getSupportFragmentManager();
                Fragment fragment = BookContentFragment.newInstance(mUrl,position);
                fm.beginTransaction()
                        .replace(R.id.book_fragment_container,fragment)
                        .addToBackStack(null)
                        .show(fragment)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBookChapterList.size();
    }

    class ChapterHolder extends RecyclerView.ViewHolder{
        private TextView mTextView;
        public ChapterHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }
    }
}
