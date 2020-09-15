package com.example.novelapplication.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novelapplication.R;
import com.example.novelapplication.model.BookShelfItem;

import java.util.List;

public class BookShelfItemAdapter extends RecyclerView.Adapter<BookShelfItemAdapter.ViewHolder> {

    private List<BookShelfItem> bookShelfItemList;

    public BookShelfItemAdapter(List<BookShelfItem> bookShelfItemList){
        this.bookShelfItemList=bookShelfItemList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.book_shelf_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookShelfItem bookShelfItem=bookShelfItemList.get(position);
        holder.book_cover_title.setText(bookShelfItem.getBookTitle());
        holder.book_title.setText(bookShelfItem.getBookTitle());
        holder.book_last_chapter.setText(bookShelfItem.getBookLastChapter());
        holder.book_updated.setText(bookShelfItem.getBookUpdatedDate());
    }

    @Override
    public int getItemCount() {
        return bookShelfItemList.size();
    }

    static  class ViewHolder extends RecyclerView.ViewHolder{
        TextView book_cover_title;
        TextView book_title;
        TextView book_last_chapter;
        TextView book_updated;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            book_cover_title=(TextView)itemView.findViewById(R.id.book_shelf_item_cover_title);
            book_title=(TextView)itemView.findViewById(R.id.book_shelf_item_book_title);
            book_last_chapter=(TextView)itemView.findViewById(R.id.book_shelf_item_book_last_chapter);
            book_updated=(TextView)itemView.findViewById(R.id.book_shelf_item_updated);
        }
    }
}
