package com.example.r.lifehelper.data;

import android.os.AsyncTask;

import com.example.r.lifehelper.bean.BookChapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class BookContentAsyncTask extends AsyncTask<String, Void, BookChapter> {

    @Override
    protected BookChapter doInBackground(String... strings) {
        String url = strings[0];
        return parseUrl(url);
    }

    private static BookChapter parseUrl(String urlSpec) {
        BookChapter bookChapter = new BookChapter();
        try {
            Document doc = Jsoup.connect(urlSpec).get();
            Elements txt = doc.select("div.txt_cont");
            Elements chapter = txt.select("h1");
            Elements content = txt.select("div[id=content1]");
            bookChapter.setChapterTitle(chapter.get(0).ownText());
            bookChapter.setContent(content.get(0).html().replace("<br>", "\t").replace("&nbsp", " "));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookChapter;
    }
}
