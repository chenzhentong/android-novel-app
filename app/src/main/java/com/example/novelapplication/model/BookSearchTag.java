package com.example.novelapplication.model;

import org.litepal.crud.DataSupport;

public class BookSearchTag extends DataSupport {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BookSearchTag() {
    }

    @Override
    public String toString() {
        return "BookSearchTag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
