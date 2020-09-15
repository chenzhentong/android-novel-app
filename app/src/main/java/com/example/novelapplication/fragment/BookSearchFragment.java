package com.example.novelapplication.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novelapplication.R;
import com.example.novelapplication.activity.BookSearchActivity;
import com.example.novelapplication.adapter.BookSearchTagAdapter;
import com.example.novelapplication.dao.db.BookSearchTagDb;
import com.example.novelapplication.model.BookList;
import com.example.novelapplication.model.BookSearchTag;
import com.example.novelapplication.util.LayoutUtil;
import com.example.novelapplication.util.OkHttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class BookSearchFragment extends Fragment implements View.OnClickListener {

    private TextView clearSearchHistory;
    private BookSearchTagDb bookSearchTagDb;
    private List<BookSearchTag> bookSearchTagList;

    private List<String> tagList = new ArrayList<>();
    private RecyclerView bookSearchTagRecyclerView;
    private Activity activity;
    public static final String LOCAL_BROADCAST = "com.example.novelapplication.LOCAL_BROADCAST";

    private LocalBroadcastManager localBroadcastManager;

    private LocalReceiver localReceiver;
    private IntentFilter intentFilter;
    public BookSearchFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BookSearchFragment","BookSearchFragment");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_book_search, container, false);
        activity=getActivity();
        view.setPadding(20,200,20,0);

        localBroadcastManager=LocalBroadcastManager.getInstance(getContext());
        localReceiver=new LocalReceiver();
        intentFilter=new IntentFilter();
        intentFilter.addAction(BookSearchFragment.LOCAL_BROADCAST);//添加action
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);//注册本地广播


        clearSearchHistory = view.findViewById(R.id.clear_search_history_text_view);
        //hotSearchChangeTextView=(TextView)findViewById(R.id.hot_search_change_text_view);
        // hotSearchChangeTextView.setOnClickListener(this);
        clearSearchHistory.setOnClickListener(this);
        bookSearchTagDb = new BookSearchTagDb();


        bookSearchTagRecyclerView = (RecyclerView) view.findViewById(R.id.book_search_tag_recycle_view);
        refreshBookSearchTags();


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clear_search_history_text_view:
                bookSearchTagDb.deleteAllBookSearchTag();
                Toast.makeText(activity, "已清空所有搜索历史", Toast.LENGTH_SHORT).show();
                refreshBookSearchTags();
                break;
            /*case R.id.hot_search_change_text_view:
                break;*/
        }
    }

    //刷新书籍搜索标签历史数据
    public void refreshBookSearchTags(){
        bookSearchTagList=bookSearchTagDb.queryAllBookSearchTag();
        for (BookSearchTag bookSearchTag : bookSearchTagList) {
            tagList.add(bookSearchTag.getName());
            // Log.d("bookSearchTag", bookSearchTag.getName());
        }
        GridLayoutManager layoutManager = new GridLayoutManager(activity, 5);
        bookSearchTagRecyclerView.setLayoutManager(layoutManager);
        BookSearchTagAdapter bookSearchTagAdapter = new BookSearchTagAdapter(bookSearchTagList);
        bookSearchTagRecyclerView.setAdapter(bookSearchTagAdapter);
//        bookSearchTagRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//
//                searchBookLikekeyWord(rv.get)
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//            }
//        });
        bookSearchTagAdapter.notifyDataSetChanged();
    }
    //在进行在跳转前的界面接收标志符号,来做刷新判断.
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == activity.RESULT_OK) {
            refreshBookSearchTags();
            // bookSearchTagAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshBookSearchTags();

    }

    public void searchBookLikekeyWord(final String keyword){
        //查询相关书籍信息
        OkHttpUtil.httpGet("http://api.zhuishushenqi.com/book/fuzzy-search?query=" + keyword, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity,"网络错误",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //添加搜索标签历史
                if (bookSearchTagDb.queryBookSearchTagByName(keyword)==null){

                    bookSearchTagDb.addBookSearchTag(keyword);
                }
                String responseData=response.body().string();
                Gson gson=new Gson();
                BookList bookList=gson.fromJson(responseData,new TypeToken<BookList>(){}.getType());
                //向书籍搜索结果页面传递搜索的书籍信息
                BookSearchResultFragment bookSearchResultFragment = new BookSearchResultFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("bookList", bookList);
                bookSearchResultFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(android.R.id.content,bookSearchResultFragment).commit();

            }
        });

    }


    private class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if (!action.equals(BookSearchFragment.LOCAL_BROADCAST)){
                return;
            }
            String keyword=intent.getStringExtra("keyword");
            searchBookLikekeyWord(keyword);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }
}
