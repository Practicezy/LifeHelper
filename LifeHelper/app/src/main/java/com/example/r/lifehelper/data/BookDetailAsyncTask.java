package com.example.r.lifehelper.data;

import android.os.AsyncTask;

import com.example.r.lifehelper.bean.Book;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class BookDetailAsyncTask extends AsyncTask<String, Void, Book> {

    @Override
    protected Book doInBackground(String... strings) {
        String url = strings[0];
        return parseUrl(url);
    }

    private static Book parseUrl(String urlSpec) {
        Book book = new Book();
        Document doc = null;
        try {
            doc = Jsoup.connect(urlSpec).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements links = doc.select("div.show");
        Elements detail = links.select("div.detail");
        Elements author = detail.select("li:contains(作者)");
        Elements date = detail.select("li:contains(日期)");
        Elements imgs = detail.select("img[src]");
        Elements intro = links.select("div.showInfo");
        Elements p = intro.select("p");
        Elements button = links.select("div.showDown");
        Elements a = button.select("a.downButton");

        book.setAuthor(author.get(0).ownText().replace("书籍作者：", ""));
        book.setDate(date.get(0).ownText().replace("更新日期：", ""));
        book.setImageUrl(imgs.get(0).absUrl("src"));
        book.setIntro(p.get(0).text());
        book.setDetailUrl(a.get(0).absUrl("href"));
        return book;
    }
}
