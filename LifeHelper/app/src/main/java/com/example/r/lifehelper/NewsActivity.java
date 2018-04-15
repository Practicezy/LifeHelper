package com.example.r.lifehelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

public class NewsActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private WebView wvNews;
    private ProgressDialog mProgressDialog;
    private static final String EXTRA_NEWS = "news_url";
    private static final String TAG = "NewsActivity";

    public static Intent newIntent(Context context, String urlSpec){
        Intent intent = new Intent(context, NewsActivity.class);
        intent.putExtra(EXTRA_NEWS,urlSpec);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        mToolbar = findViewById(R.id.news_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        wvNews = findViewById(R.id.wv_news);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getResources().getString(R.string.loading));

        String url = getIntent().getStringExtra(EXTRA_NEWS);
        Log.i(TAG, "onCreate: " + url);
        wvNews.loadUrl(url);

        wvNews.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        wvNews.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (!isFinishing()){
                    mProgressDialog.show();
                }
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mProgressDialog.cancel();
                super.onPageFinished(view, url);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mProgressDialog != null){
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        if (wvNews != null){
            wvNews.loadDataWithBaseURL(null,"","text/html", "utf-8",null);
            wvNews.clearHistory();

            ((ViewGroup)wvNews.getParent()).removeView(wvNews);
            wvNews.destroy();
            wvNews = null;
        }
        super.onDestroy();
    }
}
