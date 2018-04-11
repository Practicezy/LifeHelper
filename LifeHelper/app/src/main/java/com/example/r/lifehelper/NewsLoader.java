package com.example.r.lifehelper;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NewsLoader {
    private static final String TAG = "NewsLoader";

    public List<News> loadNewsByAsyncTask(String urlSpec){
        List<News> newsList = new ArrayList<>();
        NewsAasynctask newsAasynctask = new NewsAasynctask();
        newsAasynctask.execute("http://api.avatardata.cn/TouTiao/Query?key=3883d6c886b84f5fa8a10768282eccff&type=" + urlSpec);
        try {
            newsList = newsAasynctask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    private class NewsAasynctask extends AsyncTask<String,Void,List<News>> {

        @Override
        protected List<News> doInBackground(String... strings) {
            String url = strings[0];
            String urlString = HttpUnitils.getUrlString(url);
            List<News> newsList = parseUrl(urlString);
            return newsList;
        }

        private List<News> parseUrl(String urlString){
            List<News> newsList = new ArrayList<>();
            try {
                JSONObject jsonUrl = new JSONObject(urlString);
                JSONObject jsonObject = jsonUrl.getJSONObject("result");
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    News news = new News();
                    news.setTitle(object.getString("title"));
                    news.setContent(object.getString("url"));
                    news.setSrc(object.getString("author_name"));
                    news.setImageUrl(object.getString("thumbnail_pic_s"));
                    news.setDate(object.getString("date"));
                    newsList.add(news);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return newsList;
        }
    }
}
