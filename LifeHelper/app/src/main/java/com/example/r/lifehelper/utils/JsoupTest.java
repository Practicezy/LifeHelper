package com.example.r.lifehelper.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupTest {

    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect("https://www.qisuu.la/soft/sort010/").get();
            Elements links = doc.select("div.list");
            Elements lists = links.select("li");
            Elements books = lists.select("a[href~=^/Shtml]");
            Elements imgs = lists.select("img");
            for (int i = 0; i < books.size(); i++) {
                System.out.println(books.get(i).attr("href"));
                System.out.println(imgs.get(i).attr("src"));
                System.out.println(books.get(i).text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
