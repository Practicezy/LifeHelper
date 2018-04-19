package com.example.r.lifehelper.utils;

import android.os.AsyncTask;
import com.example.r.lifehelper.bean.Book;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class BookAsyncTask extends AsyncTask<String,Void,List<Book>> {

    @Override
    protected List<Book> doInBackground(String... strings) {
        String url = strings[0];
        List<Book> books = parseUrl(url);
        return books;
    }

    private static List<Book> parseUrl(String urlSpec){
        String url = HttpUtils.getUrlString(urlSpec);
        List<Book> books = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(url);
            JSONArray jsonArray = jsonObject.getJSONArray("books");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Book book = new Book();
                book.setId(object.getInt("id"));
                book.setTitle(object.getString("title"));
                JSONArray authorArray = object.getJSONArray("author");
                JSONObject authorObject = authorArray.getJSONObject(0);
                book.setAuthor(authorObject.toString());
                JSONObject scoreObject = object.getJSONObject("rating");
                book.setScore(Float.parseFloat(scoreObject.getString("average")));
                book.setAuthorIntro(object.getString("author_intro"));
                book.setSummary(object.getString( "summary"));
                book.setImageUrl(object.getString("image"));
                books.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return books;
    }
}
