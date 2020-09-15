package com.example.novelapplication.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novelapplication.R;
import com.example.novelapplication.activity.MainActivity;
import com.example.novelapplication.adapter.BookShelfItemAdapter;
import com.example.novelapplication.common.MyApplication;
import com.example.novelapplication.model.BookShelfItem;
import com.example.novelapplication.model.User;
import com.example.novelapplication.util.LayoutUtil;
import com.example.novelapplication.util.OkHttpUtil;
import com.example.novelapplication.util.ServerConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class BookshelfFragment extends Fragment {
    private Activity activity;
    private MyApplication myApplication;
    private List<BookShelfItem> bookShelfItemList;
    private RecyclerView bookShelfRecycleView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity= getActivity();
        View view = inflater.inflate(R.layout.fragment_bookshelf, container, false);
//        if (activity instanceof MainActivity){
//            LayoutUtil.setMargins(view,0,200,0,0);
//        }
        myApplication=(MyApplication)activity.getApplication();
        //Log.d("main",1+"");
        if (myApplication.getLoginUser()!=null){
            getBookShelfItemList();
        }
        bookShelfRecycleView=(RecyclerView)view.findViewById(R.id.book_shelf_recycle_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(activity);
        bookShelfRecycleView.setLayoutManager(layoutManager);
        //添加水平分割线
        bookShelfRecycleView.addItemDecoration(new DividerItemDecoration(activity,DividerItemDecoration.VERTICAL));
        return view;
    }
    private void getBookShelfItemList(){
        OkHttpUtil.httpGet(ServerConfig.getIp()+":8080/bookshelf/all/" + myApplication.getLoginUser().getId(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity,"内部服务器错误",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData=response.body().string();
                Gson gson=new Gson();
                bookShelfItemList=gson.fromJson(responseData,new TypeToken<List<BookShelfItem>>(){}.getType());
                Log.d("bookShelfItemList",bookShelfItemList.toString());
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BookShelfItemAdapter bookShelfItemAdapter=new BookShelfItemAdapter(bookShelfItemList);
                        bookShelfRecycleView.setAdapter(bookShelfItemAdapter);
                    }
                });
            }
        });
    }

}
