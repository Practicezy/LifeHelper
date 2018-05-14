package com.example.r.lifehelper.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.r.lifehelper.R;
import com.example.r.lifehelper.bean.Book;
import com.example.r.lifehelper.bean.BookLab;
import com.example.r.lifehelper.data.SearchBookAsyncTask;
import com.example.r.lifehelper.utils.EmptyRecyclerView;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchActivity extends AppCompatActivity {
    private EmptyRecyclerView mRecyclerView;
    private List<Book> mBookList;
    private static final String EXTRA_SEARCH = "com.example.r.lifehelper.search";
    private static final String TAG = "SearchActivity";

    public static Intent newIntent(Context context,String query){
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(EXTRA_SEARCH,query);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initToolbar();
        String query = getIntent().getStringExtra(EXTRA_SEARCH);
        try {
            mBookList = new SearchBookAsyncTask().execute(query).get();
            Log.i(TAG, "onCreate: " + mBookList.get(0).getTitle());
            BookLab.getBookLab(this).updateBookList(mBookList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        mRecyclerView = findViewById(R.id.erv_search);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        View emptyView = findViewById(R.id.empty_view);
        mRecyclerView.setEmptyView(emptyView);
        SearchAdapter adapter = new SearchAdapter(mBookList);
        mRecyclerView.setAdapter(adapter);
    }

    private void initToolbar() {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.search_result);
        toolbarTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/yang.ttf"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder>{
        private List<Book> mBooks;

        public SearchAdapter(List<Book> books) {
            mBooks = books;
        }

        @NonNull
        @Override
        public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(SearchActivity.this).inflate(android.R.layout.simple_list_item_1,parent,false);
            SearchHolder holder = new SearchHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
            final Book book = mBooks.get(position);
            holder.textView.setText(book.getTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ObjectAnimator oa = ObjectAnimator.ofFloat(view,"alpha",1f,0f,1f);
                    oa.setDuration(1000);
                    oa.start();
                    oa.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            Intent intent = BookActivity.newIntent(SearchActivity.this, book.getId());
                            startActivity(intent);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                }
            });
        }

        @Override
        public int getItemCount() {
            return mBooks.size();
        }

        class SearchHolder extends RecyclerView.ViewHolder{
            TextView textView;
            public SearchHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView;
                textView.setGravity(Gravity.CENTER);
            }
        }
    }
}
