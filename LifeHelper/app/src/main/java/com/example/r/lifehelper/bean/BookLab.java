package com.example.r.lifehelper.bean;

import android.content.Context;


import com.example.r.lifehelper.data.BookListAsyncTask;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class BookLab {
    private static BookLab sBookLab;
    private List<Book> mBooks;
    private BookListAsyncTask mTask;
    private Context mContext;

    public static BookLab getBookLab(Context context) {
        if (sBookLab == null) {
            sBookLab = new BookLab(context);
        }
        return sBookLab;
    }

    private BookLab(Context context) {
        mContext = context;
        mTask = new BookListAsyncTask();
        mTask.execute("https://www.qisuu.la/soft/sort010/");
        try {
            mBooks = mTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public List<Book> getBooks() {
        return mBooks;
    }

    public Book getBook(UUID id) {
        for (Book book : mBooks
                ) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }

    public void updateBookList(List<Book> bookList) {
        mBooks = bookList;
    }

}
