package com.example.r.lifehelper.data;

import android.os.AsyncTask;

import com.example.r.lifehelper.bean.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookListAsyncTask extends AsyncTask<String,Void,List<Book>> {
    @Override
    protected List<Book> doInBackground(String... strings) {
        String url = strings[0];
        List<Book> books = parseUrl(url);
        return books;
    }

    /*根据网址的js内容解析得到数据*/
    private static List<Book> parseUrl(String urlSpec){
        List<Book> books = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(urlSpec).get();
            Elements links = doc.select("div.list");
            Elements lis = links.select("li");
            Elements authors = lis.select("div.s");
            Elements summary = lis.select("div.u");
            Elements lists = lis.select("a[href~=^/Shtml]");
            Elements imgs = lis.select("img");
            for (int i = 0; i < lists.size(); i++) {
                Book book = new Book();
                book.setTitle(lists.get(i).text());
                book.setImageUrl(imgs.get(i).absUrl("src"));
                book.setUrl(lists.get(i).absUrl("href"));
                book.setAuthor(authors.get(i).textNodes().get(0).text());
                book.setSummary(summary.get(i).text());
                books.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }
}
