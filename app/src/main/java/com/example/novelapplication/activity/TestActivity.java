package com.example.novelapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.novelapplication.R;
import com.example.novelapplication.common.MyApplication;
import com.example.novelapplication.model.Book;
import com.example.novelapplication.model.BookList;
import com.example.novelapplication.model.User;
import com.example.novelapplication.util.OkHttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    private MyApplication myApplication;
    private User loginUser=new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        // 获取整个应用的Application对象
        // 在不同的Activity中获取的对象是同一个
        myApplication= (MyApplication) getApplication();
        //getUserById(2);
        // myApplication.userLogin(new User(2,"xiaoming","12333","223332@qq.com"));
        //getBookByCategory();
        //getBookDetail("5106099abb1c67cf28000016");
        // getUserById(1);
        //bookViewRecordDb=new BookViewRecordDb();
        //bookViewRecordDb.createBookViewTable();
        //bookViewRecordDb.addBookViewRecord();
        //bookViewRecordDb.queryBookViewRecord();
        //bookViewRecordDb.updateBookViewRecord();
        //bookViewRecordDb.deleteBookViewRecord();

        //bookShelfItemDb.createBookShelfItemDb();
       /* BookSearchTagDb bookSearchTagDb=new BookSearchTagDb();
        bookSearchTagDb.addBookSearchTag("1");
        bookSearchTagDb.addBookSearchTag("222");*/
//        bookSearchTagDb.deleteBookShelfItem(2);
//        Button btn0=(Button)findViewById(R.id.btn0);
//        btn0.setOnClickListener(this);

    }

    private void getBookByCategory(){
        OkHttpUtil.httpGet(
                "https://api.zhuishushenqi.com/book/by-categories?gender=male&type=hot&major=%E5%A5%87%E5%B9%BB&minor=&start=0&limit=20",
                new Callback(){
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData=response.body().string();
                        Log.d("MainActivity",responseData);
                        Gson gson=new Gson();
                        BookList bookList=gson.fromJson(responseData,new TypeToken<BookList>(){}.getType());
                        //Log.d("MainActivity",bookList.toString());
                        for (Book book:bookList.getBooks()){
                            Log.d("book",book.toString());
                        }

                    }
                });
    }



    @Override
    public void onClick(View view) {

        switch (view.getId()){
//            case R.id.btn0:
//                getUserById(1);
//                break;
            default:break;
        }
    }


    public void getUserById(int id){

        OkHttpUtil.httpGet("http://192.168.1.2:8080/user/" + id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData=response.body().string();
                Log.d("user",responseData);
                Gson gson=new Gson();
                loginUser=gson.fromJson(responseData,new TypeToken<User>(){}.getType());
                Log.d("loginUser",loginUser+"");
                if (loginUser!=null){
                    myApplication.userLogin(loginUser);
                }

            }
        });
    }
}
