package com.example.novelapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.novelapplication.R;
import com.example.novelapplication.common.MyApplication;
import com.example.novelapplication.model.Book;
import com.example.novelapplication.model.BookShelfItem;
import com.example.novelapplication.util.OkHttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BookAdapter extends ArrayAdapter<Book>{

//    private MyApplication myApplication;
    int resourceId;
    public BookAdapter(@NonNull Context context, int resource, @NonNull List<Book> objects) {
        super(context, resource, objects);
        this.resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Book book=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.bookAuthor=view.findViewById(R.id.book_item_author);
            viewHolder.bookTitle=view.findViewById(R.id.book_item_title);
            viewHolder.bookLastChapter=view.findViewById(R.id.book_item_lastChapter);
            viewHolder.bookCoverTitle=view.findViewById(R.id.book_item_cover_title);

            view.setTag(viewHolder);
        } else{
           view=convertView;
           viewHolder=(ViewHolder) view.getTag();
        }
        viewHolder.bookAuthor.setText(book.getAuthor());
        viewHolder.bookTitle.setText(book.getTitle());
        viewHolder.bookLastChapter.setText(book.getLastChapter());
        viewHolder.bookCoverTitle.setText(book.getTitle());

        return view;
    }



    class ViewHolder{
        TextView bookCoverTitle;
        TextView bookTitle;
        TextView bookAuthor;
        TextView bookLastChapter;
        ImageView addBookshelf;
    }



}
