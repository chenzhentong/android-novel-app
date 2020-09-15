package com.example.novelapplication.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.novelapplication.R;
import com.example.novelapplication.adapter.FictionAdapter;
import com.example.novelapplication.model.Book;
import com.example.novelapplication.model.BookList;
import com.example.novelapplication.util.HttpUtil;
import com.example.novelapplication.util.Utility;
import com.google.android.material.navigation.NavigationView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class RankListActivity extends AppCompatActivity {
    private static final int ITEM_HOT = 1;
    private static final int ITEM_NEW = 2;
    private static  final int ITEM_RANK=3;
    private static  final int ITEM_OVER=4;
    private static  final int ITEM_GIRL=5;

    private List<Book> bookList= new ArrayList<>();
    private ListView listView;
    private FictionAdapter adapter;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_list);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        //setActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            // actionBar.setHomeAsUpIndicator(R.drawable.square);
        }
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("热度榜");

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        listView = (ListView) findViewById(R.id.list_view);
        adapter = new FictionAdapter(this, R.layout.list_view_item, bookList);
        listView.setAdapter(adapter);

        /**
         *intent内替换成要跳转的activity名(TestActivity为示例)，putExtra传递需要的参数
         * */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RankListActivity.this, BookDetailActivity.class);
                Book book = bookList.get(position);
                intent.putExtra("title", actionBar.getTitle());
                intent.putExtra("_id",book.get_id());
                //Log.d("MainActivity", "onItemClick: "+fiction.getId());
                startActivity(intent);
            }
        });

        drawerLayout =  findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.hot_button);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.hot_button:
                        handleCurrentPage("人气榜", ITEM_HOT);
                        break;
                    case R.id.new_button:
                        handleCurrentPage("新品榜",ITEM_NEW);
                        break;
                    case R.id.rank_button:
                        handleCurrentPage("评分榜",ITEM_RANK);
                        break;
                    case R.id.over_button:
                        handleCurrentPage("完结榜",ITEM_OVER);
                        break;
//                    case R.id.girl_button:
//                        handleCurrentPage("女生最爱",ITEM_GIRL);
//                        break;
                    default:
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                int itemName = parseString((String) actionBar.getTitle());
                requestNew(itemName);
            }
        });

        requestNew(ITEM_HOT);
    }

    private void handleCurrentPage(String text, int item) {
        ActionBar actionBar = getSupportActionBar();
        if (!text.equals(actionBar.getTitle().toString())) {
            actionBar.setTitle(text);
            requestNew(item);
            refreshLayout.setRefreshing(true);
        }
    }

    public void requestNew(int itemName) {

        // 根据返回到的 URL 链接进行申请和返回数据
        String address = response(itemName);    // key
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RankListActivity.this, "小说加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                BookList novelList = Utility.parseJsonWithGson(responseText);

                /**
                 *
                 for (Novel novel : novelList.books) {
                 Fiction fiction = new Fiction(novel.title, novel.author, novel.shortIntro, novel.cover);
                 Log.d("MainActivity", "title" + novel.title);
                 Log.d("MainActivity", "author" + novel.author);
                 Log.d("MainActivity", "cover "+novel.cover);
                 Log.d("MainActivity", "shortIntro "+novel.shortIntro);
                 *
                 **/

                final int total = novelList.getTotal();
                // Log.d("MainActivity", "这是total的数量" + total);
                //Log.d("MainActivity", "这是第一个的cover "+novellist.novelList.get(0).cover);
                // Log.d("MainActivity", "这是Json返回的responseText" + responseText);
                // Log.d("MainActivity", "这是加入到novelList中的json数据："+novellist.novelList);
                // final String msg = newlist.msg;
                if (total != 0) {
                    bookList.clear();
                    for (Book book : novelList.getBooks()) {
//                     Fiction fiction = new Fiction(novel._id,novel.title, novel.author, novel.shortIntro, novel.cover);
//
//                     fictionList.add(fiction);
                        // Book book=new Book();
                        //Log.d("book",book.getShortIntro());

                        bookList.add(book);
                    }
                    //bookList=novelList.books;

                    runOnUiThread(new Runnable() {
                        @Override public void run() {
                            adapter.notifyDataSetChanged();
                            listView.setSelection(0);
                            refreshLayout.setRefreshing(false);
                        }

                        ;
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override public void run() {
                            Toast.makeText(RankListActivity.this, "数据错误返回", Toast.LENGTH_SHORT).show();
                            refreshLayout.setRefreshing(false);
                        }
                    });
                }
            }
        });

    }

    private String response(int itemName) {
        String address = "https://api.zhuishushenqi.com/book/by-categories?gender=male&type=hot&major=%E5%A5%87%E5%B9%BB&minor=&start=0&limit=20";


        switch (itemName) {
            case ITEM_HOT:
                break;
            case ITEM_NEW:
                address=address.replaceAll("hot","new");
                break;
            case ITEM_RANK:
                address=address.replaceAll("hot","reputation");
                break;
            case ITEM_OVER:
                address=address.replaceAll("hot","over");
                break;
            case ITEM_GIRL:
                address=address.replaceAll("male","female");
                break;
            default:
        }

        return address;
    }

    /**
     * 通过 actionbar.getTitle() 的参数，返回对应的 ItemName
     */
    private int parseString(String text) {
        if (text.equals("人气榜")) {
            return ITEM_HOT;
        }
        if (text.equals("新品榜")){
            return ITEM_NEW;
        }
        if (text.equals("评分榜")){
            return ITEM_RANK;
        }
        if (text.equals("完结榜")){
            return ITEM_OVER;
        }
        if (text.equals("女生最爱")){
            return ITEM_GIRL;
        }
        return ITEM_HOT;
    }

    /**
     *打开选择侧边栏
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }
}