package com.example.r.lifehelper.bean;

import java.util.Date;
import java.util.UUID;

public class Note {
    private UUID mId;
    private Date mDate;
    private String mTitle,mContent;

    public Note() {
       this(UUID.randomUUID());
    }

    public Note(UUID id){
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
