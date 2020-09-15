package com.example.novelapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novelapplication.R;
import com.example.novelapplication.adapter.BookChapterAdapter;

import com.example.novelapplication.model.Book;
import com.example.novelapplication.model.BookChapter;
import com.example.novelapplication.model.BookSource;
import com.example.novelapplication.util.OkHttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BookDirectoryActivity extends BaseActivity implements View.OnClickListener {




    private RecyclerView bookChapterRecycleView;

    private TextView directoryBookTitle;
    private String bookId;
    private String bookTitle;
    private List<BookChapter> bookChapterList;
    private ImageView reverseDirectoryImageView;
    private ImageView bookDirectoryBackImageView;
    private Boolean isPositive=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_directory);
        Intent intent=getIntent();
        bookId=intent.getStringExtra("book_id");
        bookTitle=intent.getStringExtra("book_title");
        bookChapterRecycleView=(RecyclerView)findViewById(R.id.book_chapter_recycle_view);
       // bookChapterListView=(ListView)findViewById(R.id.book_chapter_list_view);
        directoryBookTitle=(TextView) findViewById(R.id.directory_book_title);
        reverseDirectoryImageView=(ImageView)findViewById(R.id.reverse_directory);
        bookDirectoryBackImageView=(ImageView)findViewById(R.id.book_directory_back);
        reverseDirectoryImageView.setOnClickListener(this);
        bookDirectoryBackImageView.setOnClickListener(this);
        updateBookTitle();
        getBookSources(bookId);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);

        bookChapterRecycleView.setLayoutManager(layoutManager);
       // bookChapterRecycleView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

    }


    //更新章节界面
    public  void updateBookTitle(){

        directoryBookTitle.setText(bookTitle);

    }

    //根据书籍id获取书源
    public void getBookSources( String bookId){

        OkHttpUtil.httpGet("http://api.zhuishushenqi.com/btoc?view=summary&book="+bookId,new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData=response.body().string();
                Gson gson=new Gson();
                List<BookSource> bookSourceList =gson.fromJson(responseData,new TypeToken<List<BookSource>>(){}.getType());
                if (bookSourceList.size()==0){
                    Toast.makeText(BookDirectoryActivity.this,"未找到书源",Toast.LENGTH_SHORT).show();
                }
                for (BookSource bookSource1:bookSourceList){
                    if (bookSource1.getName().equals("优质书源")){
                        String bookSourceId=bookSource1.get_id();
                        // Log.d("bookSource",bookSource1.toString());
                        getBookChapters(bookSourceId);
                    }
                }
            }
        });
    }

    //根据书源id获取书籍章节
    public void getBookChapters(String  bookSourceId){
        OkHttpUtil.httpGet("http://api.zhuishushenqi.com/atoc/"+bookSourceId+"?view=chapters",new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData=response.body().string();
                //Log.d("responseData",responseData.toString());
                Gson gson=new Gson();
                BookSource bookSource =gson.fromJson(responseData,new TypeToken<BookSource>(){}.getType());
                Log.d("bookSource",bookSource.toString());
//                List<BookChapter> bookChapters=  bookSource.getChapters();
//                for (BookChapter bookChapter:bookChapters){
//                    //Log.d("bookChapter",bookChapter.getTitle());
//                    getBookChapterList().add(bookChapter.getTitle());
//                }

                bookChapterList=bookSource.getChapters();
                Log.d("bookChapterList",bookChapterList.toString());
                updateBookChaptersListUi();

            }
        });
    }

    public void updateBookChaptersListUi(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                BookChapterAdapter bookChapterAdapter=new BookChapterAdapter(bookChapterList);
                bookChapterRecycleView.setAdapter(bookChapterAdapter);
                if (isPositive){
                    reverseDirectoryImageView.setImageDrawable(getResources().getDrawable(R.drawable.reverseorder));
                } else {
                    reverseDirectoryImageView.setImageDrawable(getResources().getDrawable(R.drawable.positiveorder));
                }
            }
        });

    }
    public void reverseDirectory(){


        isPositive=!isPositive;
        List<BookChapter> bookChapterList2=new ArrayList<>();
        if (bookChapterList.size()>0){
            for (int i=bookChapterList.size()-1;i>=0;i--){
                bookChapterList2.add(bookChapterList.get(i));
            }
        }
        bookChapterList.clear();
        bookChapterList.addAll(bookChapterList2);
        updateBookChaptersListUi();
  }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reverse_directory:
                reverseDirectory();
                break;
            case R.id.book_directory_back:
                finish();
                break;
        }
    }
}
