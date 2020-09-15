package com.example.novelapplication.sqlite;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

@Database(entities = {User.class},version = 1,exportSchema = false)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {
    private static RoomDatabase mRoomDatabase;
    public static RoomDatabase get(Context context){
        if (mRoomDatabase == null){
            synchronized (RoomDatabase.class){
                if (mRoomDatabase == null){
                    mRoomDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDatabase.class,
                            "user").build();
                }
            }
        }
        return mRoomDatabase;
    }
    public abstract RoomDao getRoomDao();
}
