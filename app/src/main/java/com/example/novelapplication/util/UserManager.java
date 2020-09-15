package com.example.novelapplication.util;


import com.example.novelapplication.sqlite.User;

public class UserManager {
    private static UserManager mUserManager;
    private UserManager(){}
    public synchronized static UserManager get(){
        if (mUserManager == null){
            mUserManager = new UserManager();
        }
        return mUserManager;
    }

    private static User mUser;

    public void saveUser(User user){
        mUser = user;
    }
    public User getUser(){
        if (mUser != null){
            return mUser;
        }
        return null;
    }
    public void removeUser(){
        mUser = null;
    }
}
