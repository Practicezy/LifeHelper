package com.example.r.lifehelper.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.r.lifehelper.bean.Movie;
import com.example.r.lifehelper.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieAsyncTask extends AsyncTask<String, Void, List<Movie>> {

    @Override
    protected List<Movie> doInBackground(String... strings) {
        String url = strings[0];
        List<Movie> movieList = parseUrl(url);
        return movieList;
    }

    private static List<Movie> parseUrl(String urlSpec) {
        String urlString = HttpUtils.getUrlString(urlSpec);
        List<Movie> movieList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(urlString);
            JSONArray jsonArray = jsonObject.getJSONArray("itemList");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String type = object.getString("type");
                if (type.equals("video")) {
                    JSONObject data = object.getJSONObject("data");
                    String title = data.getString("title");
                    int id = data.getInt("id");
                    String description = data.getString("description");

                    JSONArray tags = data.getJSONArray("tags");
                    String tagsString = "";
                    for (int j = 0; j < tags.length(); j++) {
                        JSONObject tag = tags.getJSONObject(j);
                        String tagName = tag.getString("name");
                        tagsString += tagName+" ";
                    }
                    String category = data.getString("category");
                    JSONObject author = data.getJSONObject("author");
                    String avatar = author.getString("icon");
                    String authorName = author.getString("name");

                    JSONObject cover = data.getJSONObject("cover");
                    String coverUrl = cover.getString("feed");

                    String url = data.getString("playUrl");

                    Movie movie = new Movie();
                    movie.setId(id);
                    movie.setTitle(title);
                    movie.setDescription(description);
                    movie.setTags(tagsString);
                    movie.setCategory(category);
                    movie.setAuthor(authorName);
                    movie.setAvatarUrl(avatar);
                    movie.setCoverUrl(coverUrl);
                    movie.setUrl(url);
                    movieList.add(movie);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieList;
    }
}
