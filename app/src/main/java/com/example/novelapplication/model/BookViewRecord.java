package com.example.novelapplication.model;

import org.litepal.crud.DataSupport;

public class BookViewRecord extends DataSupport {
    private int id;
    private String bookId;
    private String title;
    private String cover;
    private String cat;
    private String author;
    private String longIntro;
    private String viewDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLongIntro() {
        return longIntro;
    }

    public void setLongIntro(String longIntro) {
        this.longIntro = longIntro;
    }

    public String getViewDate() {
        return viewDate;
    }

    public void setViewDate(String viewDate) {
        this.viewDate = viewDate;
    }

    public BookViewRecord(int id, String bookId, String title, String cover, String cat, String author, String longIntro, String viewDate) {
        this.id = id;
        this.bookId = bookId;
        this.title = title;
        this.cover = cover;
        this.cat = cat;
        this.author = author;
        this.longIntro = longIntro;
        this.viewDate = viewDate;
    }
    public BookViewRecord(String bookId, String title, String cover, String cat, String author, String longIntro, String viewDate) {

        this.bookId = bookId;
        this.title = title;
        this.cover = cover;
        this.cat = cat;
        this.author = author;
        this.longIntro = longIntro;
        this.viewDate = viewDate;
    }
    public BookViewRecord() {
    }

    @Override
    public String toString() {
        return "BookViewRecord{" +
                "id=" + id +
                ", bookId='" + bookId + '\'' +
                ", title='" + title + '\'' +
                ", cover='" + cover + '\'' +
                ", cat='" + cat + '\'' +
                ", author='" + author + '\'' +
                ", longIntro='" + longIntro + '\'' +
                ", viewDate='" + viewDate + '\'' +
                '}';
    }
}
