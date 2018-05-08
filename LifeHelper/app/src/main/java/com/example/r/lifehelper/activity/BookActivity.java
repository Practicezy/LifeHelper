package com.example.r.lifehelper.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.r.lifehelper.R;
import com.example.r.lifehelper.fragment.BookDetailFragment;

import java.util.UUID;

public class BookActivity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar mToolbar;
    private TextView mToolbarTitle;
    private static final String BOOK_EXTRA = "com.example.r.lifehelper.fragment.book_extra";

    public static Intent newIntent(Context context, UUID uuid) {
        Intent intent = new Intent(context, BookActivity.class);
        intent.putExtra(BOOK_EXTRA, uuid);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        initToolbar();

        UUID uuid = (UUID) getIntent().getSerializableExtra(BOOK_EXTRA);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = BookDetailFragment.newInstance(uuid);
        fm.beginTransaction()
                .add(R.id.book_fragment_container, fragment)
                .show(fragment)
                .commit();
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        mToolbarTitle = findViewById(R.id.toolbar_title);
        mToolbarTitle.setText(R.string.book_label);
        mToolbarTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/yang.ttf"));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
