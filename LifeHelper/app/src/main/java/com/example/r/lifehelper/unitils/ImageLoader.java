package com.example.r.lifehelper.unitils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;


public class ImageLoader {
    private Context mContext;
    private ImageView mImageView;
    private String urlSpec;

    private LruCache<String,Bitmap> mMemoryCache;

    public ImageLoader(Context context) {
        mContext = context;
        int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
        int cacheSize = maxMemory/4;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount()/1024;
            }
        };
    }

    private void addBitmaptoMemoryCache(String key,Bitmap value){
        if (getBitmapFromMemoryCache(key) == null){
            mMemoryCache.put(key, value);
        }
    }

    private Bitmap getBitmapFromMemoryCache(String key){
        return mMemoryCache.get(key);
    }

    @SuppressLint("HandlerLeak")
    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
                mImageView.setImageBitmap((Bitmap) msg.obj);
        }
    };

    public void loadBitmapByThread(final ImageView imageView, final String urlSpec, final int reqWidth, final int reqHeight){
        mImageView = imageView;
        this.urlSpec = urlSpec;
        if (mImageView.getTag().equals(urlSpec)){
            Bitmap bitmap = getBitmapFromMemoryCache(urlSpec);
            if (bitmap != null){
                mImageView.setImageBitmap(bitmap);
            }else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        byte[] imgStream = HttpUnitils.getByteArrayFromUrl(urlSpec);
                        Bitmap bitmap = getBitmapFromUrl(imgStream,reqWidth, reqHeight);
                        addBitmaptoMemoryCache(urlSpec,bitmap);
                        Message msg = myHandler.obtainMessage();
                        msg.obj = bitmap;
                        myHandler.sendMessage(msg);
                    }
                }).start();
            }
        }

    }

    private static Bitmap getBitmapFromUrl(byte[] data, int reqWidth, int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data,0,data.length,options);

        options.inSampleSize = caculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, 0,data.length,options);
    }

    private static int caculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        if (width > reqWidth || height > reqHeight){
            inSampleSize = 2;
            while ((width/inSampleSize) > reqWidth  && (height/inSampleSize) > reqHeight){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
