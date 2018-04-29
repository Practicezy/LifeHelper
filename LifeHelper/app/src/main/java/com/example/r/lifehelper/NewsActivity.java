package com.example.r.lifehelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class NewsActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private WebView wvNews;
    private ProgressDialog mProgressDialog;
    private static final String EXTRA_NEWS = "news_url";

    /*传递网址来启动该Activity */
    public static Intent newIntent(Context context, String urlSpec) {
        Intent intent = new Intent(context, NewsActivity.class);
        intent.putExtra(EXTRA_NEWS, urlSpec);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        /*初始化标题栏*/
        initToolbar();

        /*初始化加载中对话框*/
        initProgressDialog();

        /*实例化webview */
        wvNews = findViewById(R.id.wv_news);
        /*根据传来的链接加载网页*/
        String url = getIntent().getStringExtra(EXTRA_NEWS);
        wvNews.loadUrl(url);
        /*防止唤醒浏览器*/
        wvNews.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        /*在页面加载完毕前显示加载中对话框*/
        wvNews.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (!isFinishing()) {
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

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getResources().getString(R.string.loading));
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        mToolbarTitle = findViewById(R.id.toolbar_title);
        mToolbarTitle.setText(getResources().getString(R.string.app_name));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        if (wvNews != null) {
            wvNews.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            wvNews.clearHistory();

            ((ViewGroup) wvNews.getParent()).removeView(wvNews);
            wvNews.destroy();
            wvNews = null;
        }
        super.onDestroy();
    }
}
