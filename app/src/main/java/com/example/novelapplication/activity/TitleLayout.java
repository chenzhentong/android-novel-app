package com.example.novelapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.novelapplication.R;


public class TitleLayout extends LinearLayout implements View.OnClickListener {
    private Activity activity;
    public TitleLayout(final Context context, AttributeSet attrs) {
        super(context, attrs);
        activity=(Activity)context;
        LayoutInflater.from(context).inflate(R.layout.title_layout, this);
        ImageView listImageView = findViewById(R.id.title_layout_list);
        ImageView searchImageView = findViewById(R.id.title_layout_search);

        listImageView.setOnClickListener(this);
        searchImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_layout_list:
                Toast.makeText(getContext(),"bookshelf_title_list",Toast.LENGTH_SHORT).show();
                break;
            case R.id.title_layout_search:
                Intent intent=new Intent(getContext(),BookSearchActivity.class);
                activity.startActivityForResult(intent,1);
                break;
            default:
                break;
        }
    }
}
