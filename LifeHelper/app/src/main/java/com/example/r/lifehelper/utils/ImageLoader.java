package com.example.r.lifehelper.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.example.r.lifehelper.R;


public class ImageLoader {
    private Context mContext;
    private ImageView mImageView;
    private String urlSpec;
    private static final String TAG = "ImageLoader";

    private LruCache<String, Bitmap> mMemoryCache;

    public ImageLoader(Context context) {
        mContext = context;

        /*初始化内存缓存*/
        initLruCache();
    }

    private void initLruCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 4;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
    }

    /*添加图片到内存缓存*/
    private void addBitmaptoMemoryCache(String key, Bitmap value) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, value);
        }
    }

    /*根据给定的url值来获得缓存中的图片*/
    private Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    /*通过子线程来加载圆角图片*/
    public void loadRoundBitmapByThread(ImageView imageView, final String urlSpec, final int reqWidth, final int reqHeight) {
        mImageView = imageView;
        this.urlSpec = urlSpec;
        Bitmap bitmap = getBitmapFromMemoryCache(urlSpec);
        if (bitmap != null) {
            mImageView.setImageBitmap(bitmap);
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    byte[] imgStream = HttpUtils.getByteArrayFromUrl(urlSpec);
                    Bitmap bitmap = getBitmapFromUrl(imgStream, reqWidth, reqHeight);;
                    if (bitmap == null) {
                        bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.nocover);
                    }
                    addBitmaptoMemoryCache(urlSpec, bitmap);
                    Message msg = myHandler.obtainMessage();
                    msg.what = 0;
                    msg.obj = bitmap;
                    myHandler.sendMessage(msg);
                }
            }).start();
        }
    }

    /*通过子线程来加载原图片*/
    public void loadBitmapByThread(ImageView imageView, final String urlSpec, final int reqWidth, final int reqHeight) {
        mImageView = imageView;
        this.urlSpec = urlSpec;
        Bitmap bitmap = getBitmapFromMemoryCache(urlSpec);
        if (bitmap != null) {
            mImageView.setImageBitmap(bitmap);
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    byte[] imgStream = HttpUtils.getByteArrayFromUrl(urlSpec);
                    Bitmap bitmap = getBitmapFromUrl(imgStream, reqWidth, reqHeight);
                    if (bitmap == null) {
                        bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_loading);
                    }
                    addBitmaptoMemoryCache(urlSpec, bitmap);
                    Message msg = myHandler.obtainMessage();
                    msg.what = 1;
                    msg.obj = bitmap;
                    myHandler.sendMessage(msg);
                }
            }).start();
        }
    }

    /*从子线程获得的图片在主线程更新UI*/
    @SuppressLint("HandlerLeak")
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (mImageView.getTag() != null && mImageView.getTag().equals(urlSpec)) {
                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(mContext.getResources(), (Bitmap) msg.obj);
                        drawable.setCornerRadius(25);
                        mImageView.setImageDrawable(drawable);
                    }
                    break;
                case 1:
                    if (mImageView.getTag() != null && mImageView.getTag().equals(urlSpec)) {
                        Bitmap bitmap = (Bitmap) msg.obj;
                        mImageView.setImageBitmap(bitmap);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /*从URL中加载图片*/
    private static Bitmap getBitmapFromUrl(byte[] data, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);

        options.inSampleSize = caculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    /*按需对图片进行裁剪*/
    private static int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        if (width > reqWidth || height > reqHeight) {
            inSampleSize = 2;
            while ((width / inSampleSize) > reqWidth && (height / inSampleSize) > reqHeight) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
