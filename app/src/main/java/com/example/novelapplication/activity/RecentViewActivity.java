package com.example.novelapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novelapplication.R;
import com.example.novelapplication.adapter.BookAdapter;
import com.example.novelapplication.adapter.BookViewRecordAdapter;
import com.example.novelapplication.dao.db.BookViewRecordDb;
import com.example.novelapplication.model.Book;
import com.example.novelapplication.model.BookViewRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecentViewActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView bookRecentViewRecycleView;
    private  List<BookViewRecord> bookViewRecordList;
    private BookViewRecordDb bookViewRecordDb;
    private TextView clearBookViewRecord;
    private ImageView backMineImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_view);
        bookRecentViewRecycleView=(RecyclerView) findViewById(R.id.book_recent_view_recycle_view);
        clearBookViewRecord=(TextView)findViewById(R.id.clear_book_view_record);
        backMineImageView=(ImageView)findViewById(R.id.recent_view_back_mine);
        clearBookViewRecord.setOnClickListener(this);
        backMineImageView.setOnClickListener(this);
        bookViewRecordList=new ArrayList<>();
        bookViewRecordDb=new BookViewRecordDb();
        getAllBookViewRecord();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        bookRecentViewRecycleView.setLayoutManager(layoutManager);
        bookRecentViewRecycleView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        BookViewRecordAdapter adapter = new BookViewRecordAdapter(bookViewRecordList);
        bookRecentViewRecycleView.setAdapter(adapter);


    }

    public void getAllBookViewRecord(){
        bookViewRecordList=bookViewRecordDb.queryAllBookViewRecord();
        //Log.d("bookViewRecordList",bookViewRecordList.size()+"");

    }
    public void deleteAllBookViewRecord(){
        bookViewRecordDb.deleteAllBookViewRecords();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.recent_view_back_mine:
                finish();;
                break;
            case R.id.clear_book_view_record:
                deleteAllBookViewRecord();
                getAllBookViewRecord();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BookViewRecordAdapter adapter = new BookViewRecordAdapter(bookViewRecordList);
                        bookRecentViewRecycleView.setAdapter(adapter);
                    }
                });
                Toast.makeText(getApplicationContext(),"已成功清除浏览记录",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
