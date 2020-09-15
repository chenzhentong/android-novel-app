package com.example.novelapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.example.novelapplication.R;
import com.example.novelapplication.common.MyApplication;
import com.example.novelapplication.dao.db.BookShelfItemDb;
import com.example.novelapplication.dao.db.BookViewRecordDb;
import com.example.novelapplication.model.Book;
import com.example.novelapplication.model.BookList;
import com.example.novelapplication.model.BookReview;
import com.example.novelapplication.adapter.BookAdapter;
import com.example.novelapplication.adapter.BookReviewAdapter;
import com.example.novelapplication.model.BookShelfItem;
import com.example.novelapplication.model.BookViewRecord;
import com.example.novelapplication.util.OkHttpUtil;
import com.example.novelapplication.util.ServerConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BookDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG="BookDetailActivity";
    private BookViewRecordDb bookViewRecordDb;
    private  String bookId;
    /*private String data[] = {"书评1","书评2","书评3","书评1","书评2","书评3","书评1","书评2","书评3"};//假数据
    private String data2[] = {"book1","book2","book3","book1","book2","book3","book1","book2","book3"};//假数据*/
    private List<BookReview> bookReviewList=new ArrayList<>();
    private List<Book> authorOtherBookList=new ArrayList<>();

    private TextView bookTitle;
    private TextView bookCoverTitle;
    private TextView bookAuthor;
    private TextView bookCat;
    private TextView bookState;
    private TextView bookMark;
    private TextView bookIntro;
    private TextView bookLastChapter;
    private TextView bookUpdated;
    private ListView bookReviewListView;
    private ListView authorOtherBookListView;
    private BookShelfItemDb bookShelfItemDb;
    private MyApplication myApplication;
    private  Button btn_add_bookshelf;
    private TextView authorOtherBookText;
    private BookShelfItem bookShelfItem;
    private ImageView back_book_ranklist;
    private Button btn_go_bookshelf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        //取出小说id
        Intent intent=getIntent();
        bookId=intent.getStringExtra("_id");
        getBookDetail(bookId);
        getBookReviews();
        getAuthorOtherBooks();
        bookTitle=(TextView) findViewById(R.id.book_title);
        bookCoverTitle=(TextView)findViewById(R.id.book_cover_title);
        bookAuthor=(TextView)findViewById(R.id.book_author);
        bookCat=(TextView)findViewById(R.id.book_cat);
        bookState=(TextView)findViewById(R.id.book_state);
        bookMark=(TextView)findViewById(R.id.book_mark);
        bookIntro=(TextView)findViewById(R.id.book_intro);
        bookLastChapter=(TextView)findViewById(R.id.book_lastChapter);
        bookUpdated=(TextView)findViewById(R.id.book_updated);
        bookReviewListView=(ListView)findViewById(R.id.book_review_list);
        authorOtherBookListView=(ListView)findViewById(R.id.author_other_book_list);
        authorOtherBookText=(TextView)findViewById(R.id.author_other_book_text);
        btn_add_bookshelf=(Button)findViewById(R.id.btn_add_bookshelf);
        Button btn_read_book=(Button)findViewById(R.id.btn_read_book);
        Button btn_download_all_book=(Button)findViewById(R.id.btn_download_all_book);
        btn_add_bookshelf.setOnClickListener(this);
        btn_read_book.setOnClickListener(this);
        btn_download_all_book.setOnClickListener(this);
        ImageView write_review_image_view=(ImageView)findViewById(R.id.write_review_image_view);
        TextView more_view_text_view=(TextView)findViewById(R.id.more_review_text_view);
        write_review_image_view.setOnClickListener(this);
        more_view_text_view.setOnClickListener(this);
        TextView view_book_dirctory=(TextView) findViewById(R.id.view_book_dirctory);
        TextView view_book_dirctory2=(TextView) findViewById(R.id.view_book_dirctory2);
        view_book_dirctory.setOnClickListener(this);
        view_book_dirctory2.setOnClickListener(this);
        //bookShelfItemDb=new BookShelfItemDb();
        bookViewRecordDb=new BookViewRecordDb();
//        bookShelfItemDb.createBookShelfItemTable();
//        bookViewRecordDb.createBookViewTable();
        myApplication= (MyApplication) getApplication();
        updateBookShelfBtn();

        ImageView back_book_ranklist=(ImageView) findViewById(R.id.back_book_ranklist);
        back_book_ranklist.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_book_ranklist:
                finish();
                break;
            case R.id.btn_add_bookshelf:
                addOrRemoveBookShelfItem();
                //updateBookShelfBtn();
                break;
            case R.id.btn_read_book:
                break;
            case R.id.btn_download_all_book:
                break;
            case R.id.more_review_text_view:
            case R.id.write_review_image_view:
                Intent intent=new Intent(this,BookReviewActivity.class);
                intent.putExtra("book_id",bookId);//56dea43fc8bbc4363bd4cfea 548d9c17eb0337ee6df738f5
                intent.putExtra("book_title",bookTitle.getText());
                startActivityForResult(intent,1);
                break;
            case R.id.view_book_dirctory:
            case R.id.view_book_dirctory2:
                Intent intent1=new Intent(this,BookDirectoryActivity.class);
                intent1.putExtra("book_id",bookId);
                intent1.putExtra("book_title",bookTitle.getText());
                startActivity(intent1);
            default:break;

        }
    }

    //根据书架中是否有该书籍更新书架按钮
    public void updateBookShelfBtn(){
        //如果用户未登录
        if (myApplication.getLoginUser()==null){
            return;
        }
        OkHttpUtil.httpGet(ServerConfig.getIp()+":8080/bookshelf/" + myApplication.getLoginUser().getId() + "/" + bookId,
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData=response.body().string();
                        Gson gson=new Gson();
                        try {
                            //查询到书籍， 移出书架
                            BookShelfItem bookShelfItem=gson.fromJson(responseData,new TypeToken<BookShelfItem>(){}.getType());
                            Log.d("bookShelfItem",bookShelfItem.toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    btn_add_bookshelf.setText("移出书架");
                                }
                            });

                        }catch (Exception e) {
                            //未查询到书籍， 加入书架
                            Log.d("bookShelfItem", "null");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    btn_add_bookshelf.setText("加入书架");
                                }
                            });
                        }
                    }
                });
        Log.d(TAG,"书架按钮已更新");
    }
    //将书籍 加入/移出 书架
    public void addOrRemoveBookShelfItem(){
        //如果用户未登录
        if (myApplication.getLoginUser()==null){
            Toast.makeText(BookDetailActivity.this,"您还未登录，请登录后再操作",Toast.LENGTH_SHORT).show();
            return;
        }
      OkHttpUtil.httpGet(ServerConfig.getIp()+":8080/bookshelf/" + myApplication.getLoginUser().getId() + "/" + bookId,
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                            String responseData=response.body().string();
                            Gson gson=new Gson();
                            try {
                                //查询到书籍， 移出书架
                                BookShelfItem bookShelfItem=gson.fromJson(responseData,new TypeToken<BookShelfItem>(){}.getType());
                               Log.d("bookShelfItem",bookShelfItem.toString());
                                removeBookShelfItem();

                            }catch (Exception e) {
                                //未查询到书籍， 加入书架
                                Log.d("bookShelfItem", "null");
                                addBookShelfItem();


                            }
                    }
                });
    }
    //移出书架
    public void removeBookShelfItem(){
        OkHttpUtil.httpGet(ServerConfig.getIp()+":8080/bookshelf/delete/" + myApplication.getLoginUser().getId() + "/" + bookId,
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        runOnUiThread(new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(BookDetailActivity.this,"已移出书架",Toast.LENGTH_SHORT).show();
                                btn_add_bookshelf.setText("加入书架");
                            }
                        }));
                    }
                });
    }

    //加入书架
    public void addBookShelfItem(){

        BookShelfItem bookShelfItem=new BookShelfItem();
        bookShelfItem.setUserId(myApplication.getLoginUser().getId());
        bookShelfItem.setBookId(bookId);
        bookShelfItem.setBookTitle(bookTitle.getText().toString());
        bookShelfItem.setBookCover(null);
        bookShelfItem.setBookLastChapter(bookLastChapter.getText().toString());
        bookShelfItem.setBookUpdatedDate(new Date().toString());
        Gson gson=new Gson();
        OkHttpUtil.httpPost(ServerConfig.getIp()+":8080/bookshelf/add", gson.toJson(bookShelfItem),new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.d("response",response.body().toString());
                runOnUiThread(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BookDetailActivity.this,"已加入书架",Toast.LENGTH_SHORT).show();
                        btn_add_bookshelf.setText("移出书架");

                    }
                }));
            }
        });

    }
    //更新书籍详细信息界面
    public void updateBookDetailUi(final Book book){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               // bookIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.book1));
                bookTitle.setText(book.getTitle());
                bookCoverTitle.setText(book.getTitle());
                bookAuthor.setText("作者:"+book.getAuthor());
                bookCat.setText("类型:"+book.getCat());
                bookState.setText("状态:连载中");
                bookMark.setText("评分:"+book.getRating().getScore()+"");
                bookIntro.setText(book.getLongIntro());
                bookLastChapter.setText(book.getLastChapter());
                bookUpdated.setText("最近更新:"+book.getUpdated());

            }
        });
    }
    //更新书籍浏览记录
    public void updateBookViewRecord(Book book){

        BookViewRecord bookViewRecord=bookViewRecordDb.queryBookViewRecordByBookId(bookId);
        if (bookViewRecord==null){
            BookViewRecord bookViewRecord1=new BookViewRecord();
            bookViewRecord1.setViewDate(new Date().toString());
            bookViewRecord1.setAuthor(book.getAuthor());
            bookViewRecord1.setCat(book.getCat());
            bookViewRecord1.setBookId(book.get_id());
            bookViewRecord1.setLongIntro(book.getLongIntro());
            bookViewRecord1.setCover(book.getCover());
            bookViewRecord1.setTitle(book.getTitle());
            bookViewRecordDb.addBookViewRecord(bookViewRecord1);
        }
    }
    //获取书籍信息
    public void getBookDetail(String bookId){
        OkHttpUtil.httpGet("http://api.zhuishushenqi.com/book/"+bookId,new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //String responseData=response.body().string();
                String responseData=response.body().string();
                //Log.d("responseData",responseData);
                Gson gson=new Gson();
                Book book =gson.fromJson(responseData,new TypeToken<Book>(){}.getType());
                updateBookDetailUi(book);
                updateBookViewRecord(book);
                getAuthorOtherBooks(book);

            }
        });
    }


    //获取评论数据
    public void getBookReviews(){

        OkHttpUtil.httpGet(ServerConfig.getIp()+":8080/bookreview/all/" + bookId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData=response.body().string();
                //Log.d("responseData",responseData);
                Gson gson=new Gson();
                bookReviewList=gson.fromJson(responseData,new TypeToken<List<BookReview>>(){}.getType());
                /*for (BookReview bookReview:bookReviewList){
                    Log.d("bookReview",bookReview.toString());
                }*/
                runOnUiThread(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BookReviewAdapter adapter=new BookReviewAdapter(BookDetailActivity.this,R.layout.book_review_item,bookReviewList);
                        bookReviewListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }));

            }
        });


    }

    //模拟一些作者其他书籍
    public void getAuthorOtherBooks(){
        Book book=new Book("步行天下","翡翠王","第一百章 开始");
        authorOtherBookList.add(book);
        book=new Book("步行天下","功夫神医","第两千二百章 是开始也是结束");
        authorOtherBookList.add(book);
        book=new Book("步行天下","特种神医","第三千五百二十章 大结局");
        authorOtherBookList.add(book);
        book=new Book("步行天下","功夫神医","第两千二百章 是开始也是结束");
        authorOtherBookList.add(book);
    }

    //按返回键刷新评论数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.d("onActivityResult",resultCode+"");
        if (resultCode==RESULT_OK){
            getBookReviews();
        }
    }
    //点击返回图标finish上一活动回到当前活动刷新评论数据
    @Override
    protected void onResume() {
        super.onResume();
        getBookReviews();
    }
    public void getAuthorOtherBooks(Book book){
        //查询相关书籍信息
        OkHttpUtil.httpGet("http://api.zhuishushenqi.com/book/fuzzy-search?query=" + book.getAuthor(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BookDetailActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseData=response.body().string();
                Gson gson=new Gson();
                BookList bookList=gson.fromJson(responseData,new TypeToken<BookList>(){}.getType());
                authorOtherBookList=bookList.getBooks();
//                for (Book book1:authorOtherBookList){
//                    if (book.get_id().equals(book1.get_id())){
//                        authorOtherBookList.remove(book1);
//                    }
//                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BookAdapter adapter1=new BookAdapter(BookDetailActivity.this,R.layout.book_item,authorOtherBookList);
                        authorOtherBookListView.setAdapter(adapter1);
                        authorOtherBookText.setText(book.getAuthor()+" "+"还写过");
                    }
                });
            }
        });

    }
}
