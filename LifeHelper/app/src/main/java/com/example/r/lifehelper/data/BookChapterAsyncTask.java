package com.example.r.lifehelper.data;

import android.os.AsyncTask;

import com.example.r.lifehelper.bean.BookChapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookChapterAsyncTask extends AsyncTask<String, Void, List<BookChapter>> {

    @Override
    protected List<BookChapter> doInBackground(String... strings) {
        String url = strings[0];
        return parseUrl(url);
    }

    private static List<BookChapter> parseUrl(String urlSpec) {
        List<BookChapter> bookChapterList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(urlSpec).get();
            Elements info = doc.select("div[id=info]");
            Elements des = info.select("div.info_des");
            Elements title = des.select("h1");
            Elements list = doc.select("div.pc_list:contains(正文)");
            Elements li = list.select("li");
            Elements a = li.select("a");
            for (int i = 0; i < a.size(); i++) {
                BookChapter bookChapter = new BookChapter();
                bookChapter.setBookTitle(title.get(0).ownText());
                bookChapter.setChapterTitle(a.get(i).ownText());
                bookChapter.setChapterUrl(a.get(i).absUrl("href"));
                bookChapterList.add(bookChapter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookChapterList;
    }
}
