package com.example.r.lifehelper.bean;

public class Book {
    private int mId;
    private float mScore;
    private String mTitle,mAuthor,mSummary,mImageUrl,mAuthorIntro;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public float getScore() {
        return mScore;
    }

    public void setScore(float score) {
        mScore = score;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getAuthorIntro() {
        return mAuthorIntro;
    }

    public void setAuthorIntro(String authorIntro) {
        mAuthorIntro = authorIntro;
    }
}
