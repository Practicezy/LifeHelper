package com.example.r.lifehelper.bean;

import android.content.Context;


import com.example.r.lifehelper.utils.BookAsyncTask;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BookLab {
    private static BookLab sBookLab;
    private List<Book> mBooks;
    private BookAsyncTask mTask;
    private Context mContext;

    public static BookLab getBookLab(Context context){
        if (sBookLab == null){
            sBookLab = new BookLab(context);
        }
        return sBookLab;
    }

    private BookLab(Context context) {
        mContext = context.getApplicationContext();
        mTask = new BookAsyncTask();
        mTask.execute("https://api.douban.com/v2/book/search?q=python");
        try {
            mBooks = mTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public List<Book> getBooks(){
        return mBooks;
    }

    public Book getBook(int id){
        for (Book book:mBooks
             ) {
            if (book.getId() == id){
                return book;
            }
        }
        return null;
    }

    public void updateBookLab(List<Book> bookList){
        mBooks = bookList;
    }

}
