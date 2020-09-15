package com.example.novelapplication.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.litepal.crud.DataSupport;

public class BookShelfItem  {
    private int id;
    private int userId;
    private String bookId;
    private String bookTitle;
    private String createDate;
    private String bookCover;
    private String bookLastChapter;
    private String bookUpdatedDate;
    public BookShelfItem(int id){
        this.id=id;
    }
    public BookShelfItem(int id, int userId, String bookId, String bookTitle,
                         String createDate, String bookCover, String bookLastChapter,
                         String bookUpdatedDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.createDate = createDate;
        this.bookCover = bookCover;
        this.bookLastChapter = bookLastChapter;
        this.bookUpdatedDate = bookUpdatedDate;
    }

    public BookShelfItem() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getBookCover() {
        return bookCover;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    public String getBookLastChapter() {
        return bookLastChapter;
    }

    public void setBookLastChapter(String bookLastChapter) {
        this.bookLastChapter = bookLastChapter;
    }

    public String getBookUpdatedDate() {
        return bookUpdatedDate;
    }

    public void setBookUpdatedDate(String bookUpdatedDate) {
        this.bookUpdatedDate = bookUpdatedDate;
    }

    @Override
    public String toString() {
        return "BookShelfItem{" +
                "id=" + id +
                ", userId=" + userId +
                ", bookId='" + bookId + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", createDate='" + createDate + '\'' +
                ", bookCover='" + bookCover + '\'' +
                ", bookLastChapter='" + bookLastChapter + '\'' +
                ", bookUpdatedDate='" + bookUpdatedDate + '\'' +
                '}';
    }
}
