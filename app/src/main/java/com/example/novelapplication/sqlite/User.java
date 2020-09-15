package com.example.novelapplication.sqlite;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;
    private String pwd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
