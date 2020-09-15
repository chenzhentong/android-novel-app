package com.example.novelapplication.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novelapplication.R;
import com.example.novelapplication.model.BookChapter;

import java.util.List;

public class BookChapterAdapter extends RecyclerView.Adapter<BookChapterAdapter.ViewHolder> {
    private List<BookChapter> bookChapterList;
    public BookChapterAdapter(List<BookChapter>  bookChapterList){
        this.bookChapterList=bookChapterList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("ResourceType")
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.book_chapter_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookChapter bookChapter=bookChapterList.get(position);
        holder.title.setText(bookChapter.getTitle());


    }

    @Override
    public int getItemCount() {
        return bookChapterList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.book_chapter_item_title);
        }
    }
}
