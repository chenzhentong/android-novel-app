package com.example.novelapplication.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.novelapplication.R;
import com.example.novelapplication.activity.BookDetailActivity;

import com.example.novelapplication.adapter.BookAdapter;
import com.example.novelapplication.model.Book;
import com.example.novelapplication.model.BookList;

import java.util.ArrayList;
import java.util.List;

public class BookSearchResultFragment extends Fragment {

    private List<Book> books=new ArrayList<>();
    private ListView searchedBookListView;
    private Activity activity;
    @SuppressLint("LongLogTag")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BookSearchResultFragment","BookSearchResultFragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_book_search_result, container, false);
        activity=getActivity();
        view.setPadding(20,200,20,0);

        BookList bookList=(BookList)getArguments().getSerializable("bookList");
        books= bookList.getBooks();

        Log.d("books",books.toString());
        searchedBookListView=(ListView)view.findViewById(R.id.searched_book_list_view);
        BookAdapter bookAdapter=new BookAdapter(activity,R.layout.book_item,books);
        searchedBookListView.setAdapter(bookAdapter);
        searchedBookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //对整个item设置点击事件
                Book book=books.get(i);
//                Toast.makeText(BookSearchResultActivity.this,book.toString(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(activity, BookDetailActivity.class);
                intent.putExtra("_id",book.get_id());
                startActivity(intent);
            }
        });
        return view;
    }


}
