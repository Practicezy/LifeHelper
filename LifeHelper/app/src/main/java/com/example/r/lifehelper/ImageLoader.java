package com.example.r.lifehelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class ImageLoader {
    private static final String TAG = "ImageLoader";
    private ImageView mImageView;
    private String urlSpec;
    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mImageView.getTag().equals(urlSpec)){
                mImageView.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };

    public void loadBitmapByThread(final ImageView imageView, final String urlSpec, final int reqWidth, final int reqHeight){
        mImageView = imageView;
        this.urlSpec = urlSpec;

        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] imgStream = HttpUnitils.getByteArrayFromUrl(urlSpec);
                Bitmap bitmap = getBitmapFromUrl(imgStream,reqWidth, reqHeight);
                Message msg = myHandler.obtainMessage();
                msg.obj = bitmap;
                myHandler.sendMessage(msg);
            }
        }).start();
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
        int inSampleSize = 2;

        if (width > reqWidth || height > reqHeight){
            while ((width/inSampleSize) > reqWidth  && (height/inSampleSize) > reqHeight){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
