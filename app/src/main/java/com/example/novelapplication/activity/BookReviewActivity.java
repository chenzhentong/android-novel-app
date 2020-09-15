package com.example.novelapplication.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.novelapplication.R;
import com.example.novelapplication.adapter.BookReviewAdapter;
import com.example.novelapplication.common.MyApplication;
import com.example.novelapplication.model.Book;
import com.example.novelapplication.model.BookReview;
import com.example.novelapplication.util.OkHttpUtil;
import com.example.novelapplication.util.ServerConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
public class BookReviewActivity extends BaseActivity implements View.OnClickListener{
    private ListView bookReviewListView;
    private List<BookReview> bookReviewList=new ArrayList<>();
    private TextView bookReviewBookTitleTextView;
    private Book book=new Book();
    private ImageView publishReviewImageView;
    private EditText reviewContentEditText;
    private MyApplication myApplication;
    private ImageView backBookDetailImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_review);
        myApplication=(MyApplication)getApplication();
        Intent intent=getIntent();
        book.set_id(intent.getStringExtra("book_id"));
        book.setTitle(intent.getStringExtra("book_title"));

        bookReviewListView=(ListView)findViewById(R.id.book_review_list_view);
        bookReviewBookTitleTextView=findViewById(R.id.book_review_book_title);
        publishReviewImageView=(ImageView)findViewById(R.id.publish_review_image_view);
        reviewContentEditText=(EditText)findViewById(R.id.review_content_edit_text);
        backBookDetailImageView=(ImageView)findViewById(R.id.book_review_back_book_detail);
        publishReviewImageView.setOnClickListener(this);
        backBookDetailImageView.setOnClickListener(this);
        updateBookTitle();
        getBookReviews();


    }
    private void updateBookTitle(){
        bookReviewBookTitleTextView.setText(book.getTitle());
    }

    //获取评论数据
    public void getBookReviews(){
        OkHttpUtil.httpGet(ServerConfig.getIp()+":8080/bookreview/all/" + book.get_id(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData=response.body().string();
//                Log.d("responseData",responseData);
                Gson gson=new Gson();
                bookReviewList=gson.fromJson(responseData,new TypeToken<List<BookReview>>(){}.getType());
                for (BookReview bookReview:bookReviewList){
                    Log.d("bookReview",bookReview.toString());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BookReviewAdapter adapter=new BookReviewAdapter(BookReviewActivity.this,
                                R.layout.book_review_item,bookReviewList);
                        bookReviewListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                });


            }
        });


    }


    public void addBookReview(){
        if (myApplication.getLoginUser()==null){
            Toast.makeText(BookReviewActivity.this,"您还未登录，请登录后再操作",Toast.LENGTH_SHORT).show();
            return;
        }
        if (reviewContentEditText.getText().toString()==null||reviewContentEditText.getText().toString().equals("")){
            Toast.makeText(BookReviewActivity.this,"请输入评论内容",Toast.LENGTH_SHORT).show();
            return;
        }
        String content=reviewContentEditText.getText().toString();
        Log.d("content",content);
        BookReview bookReview=new BookReview();
        bookReview.setAgreeCount(0);
        bookReview.setBookId(book.get_id());
        bookReview.setPublishDate(new java.util.Date().toString());
        bookReview.setUsername(myApplication.getLoginUser().getUsername());
        bookReview.setContent(content);
        Gson gson=new Gson();
        OkHttpUtil.httpPost(ServerConfig.getIp()+":8080/bookreview/add", gson.toJson(bookReview), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("response",response.body().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BookReviewActivity.this,"评论成功",Toast.LENGTH_SHORT).show();
                        getBookReviews();
                    }
                });
            }
        });

    }

    public List<BookReview> getBookReviewList() {
        return bookReviewList;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.book_review_back_book_detail:
                finish();

            case R.id.publish_review_image_view:

                addBookReview();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            //Log.d("RESULT_OK",RESULT_OK+"");
            setResult(RESULT_OK, intent);
        }
        return super.onKeyDown(keyCode, event);
    }

}
