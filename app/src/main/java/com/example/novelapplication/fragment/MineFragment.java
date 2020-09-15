package com.example.novelapplication.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.novelapplication.R;


import com.example.novelapplication.activity.LoginActivity;


import com.example.novelapplication.activity.PersonalCenterActivity;
import com.example.novelapplication.adapter.MySettingAdapter;
import com.example.novelapplication.common.MyApplication;
import com.example.novelapplication.model.MySettingItem;
import com.example.novelapplication.util.MyStatusBarUtil;

import java.util.ArrayList;
import java.util.List;


public class MineFragment extends Fragment implements View.OnClickListener{

    private Activity activity;

    private List<MySettingItem> mySettingItemList = new ArrayList<>();
    private ListView mineSettingListView;
    private TextView myUsernameTextView;
    private MyApplication myApplication;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        activity= getActivity();
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
//        if (activity instanceof MainActivity){
//            LayoutUtil.setMargins(view,0,200,0,0);
//        }

        MyStatusBarUtil.setAndroidNativeLightStatusBar(activity,true);
        myApplication = (MyApplication) activity.getApplication();

        initMySettings();
        mineSettingListView = (ListView) view.findViewById(R.id.my_setting_list_view);
        myUsernameTextView = (TextView) view.findViewById(R.id.my_username);
        if (myApplication.getLoginUser() != null) {
            myUsernameTextView.setText(myApplication.getLoginUser().getUsername());
        } else {
            myUsernameTextView.setText("用户未登录");
        }
        myUsernameTextView.setOnClickListener(this);
        MySettingAdapter mySettingAdapter = new MySettingAdapter(activity, R.layout.my_setting_item, mySettingItemList);
        mineSettingListView.setAdapter(mySettingAdapter);
        return view;
    }

    public void initMySettings() {
        MySettingItem mySettingItem = new MySettingItem(R.drawable.messagenofity, "消息通知", null);

        mySettingItemList.add(mySettingItem);
        mySettingItem = new MySettingItem(R.drawable.recentview, "最近浏览", null);
        mySettingItemList.add(mySettingItem);
        mySettingItem = new MySettingItem(R.drawable.booklist, "我的书单", null);
        mySettingItemList.add(mySettingItem);
        mySettingItem = new MySettingItem(R.drawable.shareapplication, "分享应用", null);
        mySettingItemList.add(mySettingItem);
        mySettingItem = new MySettingItem(R.drawable.goodreview, "给个好评", null);
        mySettingItemList.add(mySettingItem);
        mySettingItem = new MySettingItem(R.drawable.cachemanage, "缓存管理", null);
        mySettingItemList.add(mySettingItem);
        mySettingItem = new MySettingItem(R.drawable.changeskin, "换肤", null);
        mySettingItemList.add(mySettingItem);
        mySettingItem = new MySettingItem(R.drawable.nightmode, "夜间模式", null);
        mySettingItemList.add(mySettingItem);
        mySettingItem = new MySettingItem(R.drawable.setting, "设置", null);
        mySettingItemList.add(mySettingItem);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_username:
                if (myApplication.getLoginUser() == null) {
                    startActivity(new Intent(activity, LoginActivity.class));
                } else {
                    startActivityForResult(new Intent(activity, PersonalCenterActivity.class),1);
                }
                break;
            default:break;

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (myApplication.getLoginUser() != null) {
            myUsernameTextView.setText(myApplication.getLoginUser().getUsername());
        } else {
            myUsernameTextView.setText("用户未登录");
        }
    }

}
