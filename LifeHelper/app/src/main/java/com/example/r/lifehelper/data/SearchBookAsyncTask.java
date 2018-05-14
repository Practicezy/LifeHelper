package com.example.r.lifehelper.data;

import android.os.AsyncTask;

import com.example.r.lifehelper.bean.Book;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchBookAsyncTask extends AsyncTask<String,Void,List<Book>> {

    @Override
    protected List<Book> doInBackground(String... strings) {
        String query = strings[0];
        List<Book> bookList = parseUrl(query);
        return bookList;
    }

    private static List<Book> parseUrl(String q) {
        List<Book> books = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("http://zhannei.baidu.com/cse/site/?cc=qisuu.la&s=6107665092019918800&q=" + q).get();
            Elements results = doc.select("div[id=results]");
            Elements h3 = results.select("h3.c-title");
            Elements a = h3.select("a[href~=.+?/Shtml]");
            for (int i = 0; i < a.size(); i++) {
                Book book = new Book();
                book.setTitle(a.get(i).text().replace("全集,txt全集下载,电子书-奇书网",""));
                book.setUrl(a.get(i).absUrl("href"));
                books.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }
}
