package com.example.novelapplication.sqlite;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface RoomDao {
    @Insert
    void addUser(User... users);

    @Query("SELECT * FROM User where username= (:username)")
    User findUser(String... username);

    @Update
    void updateUser(User... users);
}
