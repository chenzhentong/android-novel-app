package com.example.novelapplication.common;
import android.util.Log;

import com.example.novelapplication.model.User;
import com.example.novelapplication.sqlite.OpDatabase;

import org.litepal.LitePalApplication;
//自定义Application类,Application对象只会实例化一次,存储一些公共数据
/*
支持litepal
存储账号信息
 */

public class MyApplication extends LitePalApplication {



    @Override
    public void onCreate() {
        super.onCreate();
        OpDatabase.init(this);
    }

    private User loginUser=null;
    public void userLogin(User user){
        loginUser=new User();
        loginUser.setId(user.getId());
        loginUser.setUsername(user.getUsername());
        loginUser.setPassword(user.getPassword());
        loginUser.setEmail(user.getEmail());

        Log.d("loginUser",loginUser.toString());

    }
    public void userLogout(){
        loginUser=null;
    }
    public User getLoginUser() {
        return loginUser;
    }


}
