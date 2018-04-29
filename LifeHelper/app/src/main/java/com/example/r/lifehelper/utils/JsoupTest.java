package com.example.r.lifehelper.utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsoupTest {

    public static void main(String[] args) {
//        String urlString = HttpUtils.getUrlString("http://baobab.kaiyanapp.com/api/v4/tabs/selected");
//        try {
//            JSONObject jsonObject = new JSONObject(urlString);
//            int count = jsonObject.getInt("count");
//            JSONArray jsonArray = jsonObject.getJSONArray("itemList");
//            if (count == 15){
//                for (int i = 1; i < jsonArray.length(); i++) {
//                    JSONObject object = jsonArray.getJSONObject(i);
//                    JSONObject data = object.getJSONObject("data");
//                    String title = data.getString("title");
//                    int id = data.getInt("id");
//                    String description = data.getString("description");
//                    System.out.println("title = " + title);
//                    System.out.println("id = " + id);
//                    System.out.println("description = " + description);
//                }
//            }else {
//                for (int i = 0; i < jsonArray.length(); i++) {
//
//                }
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        try {
//            Document doc = Jsoup.connect("https://www.qisuu.la/du/33/33961/").timeout(3000).get();
//            Elements info = doc.select("div[id=info]");
//            Elements des = info.select("div.info_des");
//            Elements title = des.select("h1");
//            System.out.println(title.get(0).ownText());
//            Elements txt = doc.select("div.txt_cont");
//            Elements chapter = txt.select("h1");
//            Elements content = txt.select("div[id=content1]");
//            System.out.println(chapter.get(0).ownText());
//            System.out.println(content.get(0).html().replace("<br>","\t").replace("&nbsp"," "));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
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
}
