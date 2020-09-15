package com.example.novelapplication.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novelapplication.R;
import com.example.novelapplication.adapter.BookSearchTagAdapter;
import com.example.novelapplication.dao.db.BookSearchTagDb;

import com.example.novelapplication.fragment.BookSearchFragment;
import com.example.novelapplication.fragment.BookSearchResultFragment;
import com.example.novelapplication.model.BookList;
import com.example.novelapplication.model.BookSearchTag;
import com.example.novelapplication.util.OkHttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class BookSearchActivity extends BaseActivity implements View.OnClickListener {
    private TextView searchBookTextView;
    private ImageView backSearchLastImageView;
    private EditText searchContentEditText;

    BookSearchTagDb bookSearchTagDb=new BookSearchTagDb();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content,new BookSearchFragment()).commit();
        searchBookTextView=(TextView)findViewById(R.id.search_book_text_view);
        searchContentEditText=(EditText)findViewById(R.id.search_content_edit_text);
        backSearchLastImageView=(ImageView)findViewById(R.id.back_search_last);
        searchBookTextView.setOnClickListener(this);
        backSearchLastImageView.setOnClickListener(this);
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_book_text_view:
                searchBookLikekeyWord();
                break;
            case R.id.back_search_last:
                backByFragmentView();
                break;
            default:
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backByFragmentView();

        }
        return super.onKeyDown(keyCode, event);
    }
    //根据关键词搜索书籍信息
    public void searchBookLikekeyWord() {
        final String keyword = searchContentEditText.getText().toString();
        if (keyword == null || keyword.equals("")) {
            Toast.makeText(this, "请输入关键字搜索", Toast.LENGTH_SHORT).show();
            return;
        }


        //查询相关书籍信息
        OkHttpUtil.httpGet("http://api.zhuishushenqi.com/book/fuzzy-search?query=" + keyword, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BookSearchActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (bookSearchTagDb.queryBookSearchTagByName(keyword) == null) {
                    //添加搜索标签历史
                    bookSearchTagDb.addBookSearchTag(keyword);
                }
                String responseData = response.body().string();
                Gson gson = new Gson();
                BookList bookList = gson.fromJson(responseData, new TypeToken<BookList>() {
                }.getType());
//                Intent intent=new Intent(getContext(),BookSearchResultActivity.class);
//                intent.putExtra("bookList",bookList);
//
//                activity.startActivityForResult(intent,1);

                //向书籍搜索结果页面传递搜索的书籍信息
                BookSearchResultFragment bookSearchResultFragment = new BookSearchResultFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("bookList", bookList);
                bookSearchResultFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(android.R.id.content,bookSearchResultFragment).commit();
            }
        });


    }


    //不同的fragment进行不同返回处理
    public void backByFragmentView(){
        View bookSearchResultView=findViewById(R.id.book_search_result_fragment);
        View bookSearchView=findViewById(R.id.book_search_fragment);
        if (bookSearchView==null &&bookSearchResultView!=null){

            getSupportFragmentManager().beginTransaction().replace(android.R.id.content,new BookSearchFragment()).commit();
        } else  if (bookSearchView!=null &&bookSearchResultView==null){
            finish();
        } else{
            Log.d("System error","System error");
        }
    }
}


