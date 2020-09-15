package com.example.novelapplication.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novelapplication.R;

import com.example.novelapplication.activity.BookSearchActivity;
import com.example.novelapplication.dao.db.BookSearchTagDb;
import com.example.novelapplication.fragment.BookSearchFragment;
import com.example.novelapplication.model.BookList;
import com.example.novelapplication.model.BookSearchTag;

import java.util.List;


public class BookSearchTagAdapter extends RecyclerView.Adapter<BookSearchTagAdapter.ViewHolder> {
    private List<BookSearchTag> bookSearchTagList;
    private LocalBroadcastManager localBroadcastManager;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.book_search_tag_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        localBroadcastManager=LocalBroadcastManager.getInstance(parent.getContext());
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookSearchTag bookSearchTag=bookSearchTagList.get(position);
        holder.historyTag.setText(bookSearchTag.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BookSearchFragment.LOCAL_BROADCAST);
                intent.putExtra("keyword",holder.historyTag.getText());
                localBroadcastManager.sendBroadcast(intent);//发送本地广播,通知BookSearchFragment更新界面

            }
        });
    }


    public BookSearchTagAdapter(List<BookSearchTag> bookSearchTagList){

        this.bookSearchTagList=bookSearchTagList;
    }

    @Override
    public int getItemCount() {
        return bookSearchTagList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView historyTag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            historyTag=itemView.findViewById(R.id.history_tag);
        }
    }
}
