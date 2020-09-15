package com.example.novelapplication.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.novelapplication.R;


public class BottomToolbarFragment extends Fragment implements View.OnClickListener{


    public BottomToolbarFragment() {
        // Required empty public constructor
    }


    ImageView bookshelfImageView;
    ImageView bookstoreImageView;
    ImageView mineImageView;
    TextView bookshelfTextView;
    TextView bookstoreTextView;
    TextView mineTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_toolbar, container, false);
        bookshelfImageView=(ImageView)view.findViewById(R.id.bottom_toolbar_fragment_book_shelf_image_view);
        bookstoreImageView=(ImageView)view.findViewById(R.id.bottom_toolbar_fragment_book_store_image_view);
        mineImageView=(ImageView)view.findViewById(R.id.bottom_toolbar_fragment_mine_image_view);
        bookshelfTextView=(TextView) view.findViewById(R.id.bottom_toolbar_fragment_book_shelf_text_view);
        bookstoreTextView=(TextView)view.findViewById(R.id.bottom_toolbar_fragment_book_store_text_view);
        mineTextView=(TextView)view.findViewById(R.id.bottom_toolbar_fragment_mine_text_view);
        bookshelfImageView.setOnClickListener(this);
        bookstoreImageView.setOnClickListener(this);
        mineImageView.setOnClickListener(this);
        bookshelfTextView.setOnClickListener(this);
        bookstoreTextView.setOnClickListener(this);
        mineTextView.setOnClickListener(this);
        updateFootUi(0);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new BookshelfFragment()).commit();
        return view;
    }

    //更新底部tab栏
    private void updateFootUi(int i){
        switch (i){
            case 0:
                bookshelfImageView.setImageDrawable(getResources().getDrawable(R.drawable.bookshelf2));
                bookstoreImageView.setImageDrawable(getResources().getDrawable(R.drawable.bookstore));
                mineImageView.setImageDrawable(getResources().getDrawable(R.drawable.mine));
                bookshelfTextView.setTextColor(Color.parseColor("#D33030"));
                bookstoreTextView.setTextColor(Color.parseColor("#BFBFBF"));
                mineTextView.setTextColor(Color.parseColor("#BFBFBF"));
                break;
            case 1:
                bookshelfImageView.setImageDrawable(getResources().getDrawable(R.drawable.bookshelf));
                bookstoreImageView.setImageDrawable(getResources().getDrawable(R.drawable.bookstore2));
                mineImageView.setImageDrawable(getResources().getDrawable(R.drawable.mine));
                bookshelfTextView.setTextColor(Color.parseColor("#BFBFBF"));
                bookstoreTextView.setTextColor(Color.parseColor("#D33030"));
                mineTextView.setTextColor(Color.parseColor("#BFBFBF"));
                break;
            case 2:
                bookshelfImageView.setImageDrawable(getResources().getDrawable(R.drawable.bookshelf));
                bookstoreImageView.setImageDrawable(getResources().getDrawable(R.drawable.bookstore));
                mineImageView.setImageDrawable(getResources().getDrawable(R.drawable.mine2));
                bookshelfTextView.setTextColor(Color.parseColor("#BFBFBF"));
                bookstoreTextView.setTextColor(Color.parseColor("#BFBFBF"));
                mineTextView.setTextColor(Color.parseColor("#D33030"));
                break;
            default:
                break;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bottom_toolbar_fragment_book_shelf_image_view:
            case R.id.bottom_toolbar_fragment_book_shelf_text_view:
                updateFootUi(0);
                getFragmentManager().beginTransaction().replace(android.R.id.content,new BookshelfFragment()).commit();
                break;
            case R.id.bottom_toolbar_fragment_book_store_image_view:
            case R.id.bottom_toolbar_fragment_book_store_text_view:
                updateFootUi(1);

                getFragmentManager().beginTransaction().replace(android.R.id.content,new BookstoreFragment()).commit();

                break;
            case R.id.bottom_toolbar_fragment_mine_image_view:
            case R.id.bottom_toolbar_fragment_mine_text_view:
                updateFootUi(2);


                getFragmentManager().beginTransaction().replace(android.R.id.content,new MineFragment()).commit();

                break;
            default:break;
        }

    }
}
