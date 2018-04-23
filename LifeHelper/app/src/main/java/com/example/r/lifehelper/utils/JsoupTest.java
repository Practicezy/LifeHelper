package com.example.r.lifehelper.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Pattern;

public class JsoupTest {

    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect("https://www.qisuu.la/soft/sort02/").timeout(3000).get();
            Elements links = doc.select("div.list");
            Elements lists = links.select("li");
            Elements authors = lists.select("div.s");
            Elements summary = lists.select("div.u");
            Elements books = lists.select("a[href~=^/Shtml]");
            Elements imgs = lists.select("img[src]");
            for (int i = 0; i < books.size(); i++) {
                System.out.println(books.get(i).absUrl("href"));
                System.out.println(books.get(i).text());
                System.out.println(authors.get(i).textNodes().get(0));
                System.out.println(summary.get(i).text());
                System.out.println(imgs.get(i).absUrl("src"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
