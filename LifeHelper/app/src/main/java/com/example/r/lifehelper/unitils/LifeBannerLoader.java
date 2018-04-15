package com.example.r.lifehelper.unitils;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LifeBannerLoader {

    public static List<String> loadBannerByAsyncTask(){
        LifeBannerAsyncTask asyncTask = new LifeBannerAsyncTask();
        asyncTask.execute("https://pixabay.com/api/?key=8250871-a5cbd2dc769e63d6b5fe7138a&category=life&image_type=all&pretty=true");
        try {
            return asyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class LifeBannerAsyncTask extends AsyncTask<String,Void,List<String>>{

        @Override
        protected List<String> doInBackground(String... strings) {
            String urlSpec = strings[0];
            String url = HttpUnitils.getUrlString(urlSpec);
            return parseUrl(url);
        }

        private List<String> parseUrl(String urlSpec){
            List<String> imgList = new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(urlSpec);
                JSONArray jsonArray = jsonObject.getJSONArray("hits");
                for (int i = 0; i < 5; i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String imgUrl = object.getString("largeImageURL");
                    imgList.add(imgUrl);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return imgList;
        }
    }
}
