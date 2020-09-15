package com.example.novelapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.bumptech.glide.Glide;
import com.example.novelapplication.R;
import com.example.novelapplication.model.Book;

import java.util.List;

public class FictionAdapter extends ArrayAdapter<Book> {
    private int resourceId;

    public FictionAdapter(@NonNull Context context, @LayoutRes int resource,
                          @NonNull List<Book> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    public class ViewHolder {
        TextView novelName;
        TextView novelAuthor;
        TextView novelIntro;
        ImageView novelCover;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Book book = getItem(position);
        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.novelName = (TextView) view.findViewById(R.id.novel_title);
            viewHolder.novelCover = (ImageView) view.findViewById(R.id.novel_cover);
            viewHolder.novelAuthor = (TextView) view.findViewById(R.id.novel_author);
            viewHolder.novelIntro = (TextView) view.findViewById(R.id.novel_long_intro);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(getContext()).load(book.getCover()).into(viewHolder.novelCover);
        viewHolder.novelName.setText(book.getTitle());
        viewHolder.novelIntro.setText(book.getShortIntro());
        viewHolder.novelAuthor.setText(book.getAuthor());

        return view;
    }

}
