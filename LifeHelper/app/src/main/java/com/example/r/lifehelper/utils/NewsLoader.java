package com.example.r.lifehelper.utils;

import android.os.AsyncTask;

import com.example.r.lifehelper.bean.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NewsLoader {
    /*通过asyncTask加载新闻页*/
    public List<News> loadNewsByAsyncTask(String urlSpec) {
        List<News> newsList = new ArrayList<>();
        NewsAsyncTask newsAsyncTask = new NewsAsyncTask();
        newsAsyncTask.execute("http://api.avatardata.cn/TouTiao/Query?key=a00709490da64cf4ac51264d45f21c45&type=" + urlSpec);
        try {
            newsList = newsAsyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    private class NewsAsyncTask extends AsyncTask<String, Void, List<News>> {

        @Override
        protected List<News> doInBackground(String... strings) {
            String url = strings[0];
            String urlString = HttpUtils.getUrlString(url);
            List<News> newsList = parseUrl(urlString);
            return newsList;
        }

        /*根据调用的api的内容来获取数据*/
        private List<News> parseUrl(String urlString) {
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
