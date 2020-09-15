package com.example.novelapplication.model;

import java.io.Serializable;
import java.util.List;

public class BookList implements Serializable {
    private Integer total;
    private List<Book> books;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public BookList() {
    }

    @Override
    public String toString() {
        return "BookList{" +
                "total=" + total +
                ", books=" + books +
                '}';
    }

    public BookList(Integer total, List<Book> books) {
        this.total = total;
        this.books = books;
    }
}
