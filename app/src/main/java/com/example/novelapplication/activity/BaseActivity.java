package com.example.novelapplication.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.novelapplication.R;
import com.example.novelapplication.util.MyStatusBarUtil;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);

        Log.d("BaseActivity",getClass().getSimpleName());
        //MyStatusBarUtil.fullScreen(this);
        MyStatusBarUtil.makeStatusBarTransparent(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        AndroidBug5497Workaround.assistActivity(this);
       // makeStatusBarTransparent(this);
        //StatusBarUtil.setTranslucent(this);

        // 部分机型的statusbar会有半透明的黑色背景
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().setStatusBarColor(   R.color.colorRed);// SDK21

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }
    //活动管理器
    static class ActivityController {
        private static List<Activity> activityList=new ArrayList<>();
        public static void addActivity(Activity activity){
            activityList.add(activity);
        }
        public static void removeActivity(Activity activity){
            activityList.remove(activity);
        }
        public static void finishAll(){
            for (Activity activity:activityList){
                if (!activity.isFinishing()){
                    activity.finish();
                }
            }
        }
    }
}
