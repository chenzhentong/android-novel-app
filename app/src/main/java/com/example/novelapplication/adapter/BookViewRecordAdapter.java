package com.example.novelapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novelapplication.R;


import com.example.novelapplication.activity.BookDetailActivity;
import com.example.novelapplication.model.BookViewRecord;


import java.util.List;



public class BookViewRecordAdapter extends RecyclerView.Adapter<BookViewRecordAdapter.ViewHolder> {
    private List<BookViewRecord> bookViewRecordList;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.book_view_record_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookViewRecord bookViewRecord=bookViewRecordList.get(position);
        holder.bookCoverTitle.setText(bookViewRecord.getTitle()+"");
        holder.bookCatAndAuthor.setText(bookViewRecord.getCat()+" | "+bookViewRecord.getAuthor());
        if (bookViewRecord.getLongIntro().length()>50){
            bookViewRecord.setLongIntro(bookViewRecord.getLongIntro().substring(0,50)+"……");
        }
        holder.bookIntro.setText(bookViewRecord.getLongIntro());
        holder.bookTitle.setText(bookViewRecord.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.itemView.getContext(), BookDetailActivity.class);
                intent.putExtra("_id",bookViewRecord.getBookId());
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }
    public BookViewRecordAdapter(List<BookViewRecord> bookViewRecordList){

        this.bookViewRecordList=bookViewRecordList;
    }

    @Override
    public int getItemCount() {
        return bookViewRecordList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView bookCoverTitle;
        private TextView bookTitle;
        private TextView bookCatAndAuthor;
        private TextView bookIntro;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookCoverTitle=(TextView) itemView.findViewById(R.id.view_book_cover_title);
          //  Log.d("holder.bookCoverTitle",R.id.view_book_cover_title1+"");
           bookCatAndAuthor=(TextView)itemView.findViewById(R.id.view_book_cat_author);
            bookIntro=(TextView)itemView.findViewById(R.id.view_book_intro);
           bookTitle=(TextView)itemView.findViewById(R.id.view_book_title);

        }
    }
}
