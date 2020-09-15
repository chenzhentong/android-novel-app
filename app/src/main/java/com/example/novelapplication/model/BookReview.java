package com.example.novelapplication.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BookReview {
    private int id;
    private String bookId;

    private String username;
    private String publishDate;
    private String icon;
    private String content;
    private int agreeCount;

    public BookReview() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public BookReview(String bookId, String username, String publishDate, String icon, String content, int agreeCount) {
        this.bookId = bookId;
        this.username = username;
        this.publishDate = publishDate;
        this.icon = icon;
        this.content = content;
        this.agreeCount = agreeCount;
    }



    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "BookReview{" +
                "id=" + id +
                ", bookId='" + bookId + '\'' +
                ", username='" + username + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", icon='" + icon + '\'' +
                ", content='" + content + '\'' +
                ", agreeCount=" + agreeCount +
                '}';
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }


    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAgreeCount() {
        return agreeCount;
    }

    public void setAgreeCount(int agreeCount) {
        this.agreeCount = agreeCount;
    }


}
