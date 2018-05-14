package com.example.r.lifehelper.utils;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupTest {

    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect("http://zhannei.baidu.com/cse/site/?cc=qisuu.la&s=6107665092019918800&q=%E6%9C%88").timeout(3000).get();
            Elements results = doc.select("div[id=results]");
            Elements h3 = results.select("h3.c-title");
            Elements a = h3.select("a[href~=.+?/Shtml]");
            for (int i = 0; i < a.size(); i++) {
                System.out.println(a.get(i).text());
                System.out.println(a.get(i).absUrl("href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public static void main(String[] args) {
//        try {
//            Document doc = Jsoup.connect("https://www.qisuu.la/Shtml33638.html").timeout(3000).get();
//            Elements links = doc.select("div.show");
//            Elements detail = links.select("div.detail");
//            Elements title = detail.select("h1");
//            Elements author = detail.select("li:contains(作者)");
//            Elements date = detail.select("li:contains(日期)");
//            Elements imgs = detail.select("img[src]");
//            Elements intro = links.select("div.showInfo");
//            Elements p = intro.select("p");
//            Elements button = links.select("div.showDown");
//            Elements a = button.select("a.downButton");
//
//            System.out.println(title.get(0).text());
//            System.out.println(author.get(0).ownText().replace("书籍作者：",""));
//            System.out.println(date.get(0).ownText().replace("更新日期：",""));
//            System.out.println(imgs.get(0).absUrl("src"));
//            System.out.println(p.get(0).text());
//            System.out.println(a.get(0).absUrl("href"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
