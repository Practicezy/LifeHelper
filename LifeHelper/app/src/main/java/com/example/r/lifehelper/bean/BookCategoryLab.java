package com.example.r.lifehelper.bean;

import com.example.r.lifehelper.R;

import java.util.ArrayList;
import java.util.List;

public class BookCategoryLab {
    private static BookCategoryLab sBookCategoryLab;
    private List<BookCategory> mBookCategories;

    public static BookCategoryLab getBookCategoryLab() {
        if (sBookCategoryLab == null){
            sBookCategoryLab = new BookCategoryLab();
        }
        return sBookCategoryLab;
    }

    private BookCategoryLab() {
        mBookCategories = new ArrayList<>();
        initBookCategories("玄幻奇幻",R.drawable.ic_hero_spiderman,"https://www.qisuu.la/soft/sort01/");
        initBookCategories("武侠仙侠",R.drawable.ic_hero_iron,"https://www.qisuu.la/soft/sort02/");
        initBookCategories("女频言情",R.drawable.ic_hero_hulk,"https://www.qisuu.la/soft/sort03/");
        initBookCategories("现代都市",R.drawable.ic_hero_greenlight,"https://www.qisuu.la/soft/sort04/");
        initBookCategories("历史军事",R.drawable.ic_hero_flash,"https://www.qisuu.la/soft/sort05/");
        initBookCategories("游戏竞技",R.drawable.ic_hero_death,"https://www.qisuu.la/soft/sort06/");
        initBookCategories("科幻灵异",R.drawable.ic_hero_captain,"https://www.qisuu.la/soft/sort07/");
        initBookCategories("美文同人",R.drawable.ic_hero_batman,"https://www.qisuu.la/soft/sort08/");
        initBookCategories("剧本教程",R.drawable.ic_hero_thor,"https://www.qisuu.la/soft/sort09/");
        initBookCategories("名著杂志",R.drawable.ic_hero_superman,"https://www.qisuu.la/soft/sort010/");
    }

    public List<BookCategory> getBookCategories(){
        return mBookCategories;
    }

    public BookCategory getBookCategory(String category){
        for (BookCategory bookCategory:mBookCategories
             ) {
            if (bookCategory.getCategory().equals(category)){
                return bookCategory;
            }
        }
        return null;
    }

    private void initBookCategories(String category,int IconId,String url) {
        BookCategory bookCategory = new BookCategory();
        bookCategory.setCategory(category);
        bookCategory.setIconId(IconId);
        bookCategory.setUrl(url);
        mBookCategories.add(bookCategory);
    }
}
