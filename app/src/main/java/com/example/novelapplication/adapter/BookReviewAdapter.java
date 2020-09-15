package com.example.novelapplication.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.novelapplication.R;
import com.example.novelapplication.activity.BookDetailActivity;
import com.example.novelapplication.activity.BookReviewActivity;
import com.example.novelapplication.common.MyApplication;
import com.example.novelapplication.model.BookReview;
import com.example.novelapplication.util.OkHttpUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BookReviewAdapter extends ArrayAdapter<BookReview> {
    private List<BookReview> bookReviewList;
    private MyApplication myApplication;
    private Activity activity;
    int resourceId;
    public BookReviewAdapter(@NonNull Context context, int resource,
                             List<BookReview> objects) {
        super(context, resource,objects);
        this.resourceId=resource;
        this.bookReviewList=objects;
        //获取当前活动
        activity=(Activity)context;
        //判断是哪个活动
        if (context instanceof BookReviewActivity){
            Log.d("context instance of ","BookReviewActivity");
        } else if (context instanceof BookDetailActivity){
            Log.d("context instance of ","BookDetailActivity");
        }




    }

    @SuppressLint("LongLogTag")
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        myApplication=(MyApplication)getContext().getApplicationContext();
         BookReview bookReview=getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.reviewUsername=(TextView)view.findViewById(R.id.review_user_name);
            viewHolder.reviewAgreeCount=(TextView)view.findViewById(R.id.review_agree_count);
            viewHolder.reviewContent=(TextView)view.findViewById(R.id.review_content);
            viewHolder.reviewPublishDate=(TextView)view.findViewById(R.id.review_publish_date);
            viewHolder.deleteReviewBtn=(Button)view.findViewById(R.id.delete_book_review);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.reviewUsername.setText(bookReview.getUsername());
        viewHolder.reviewPublishDate.setText(bookReview.getPublishDate());
        viewHolder.reviewContent.setText(bookReview.getContent());
        viewHolder.reviewAgreeCount.setText(bookReview.getAgreeCount()+"");
        //如果用户未登录
        if (myApplication.getLoginUser()==null){
            viewHolder.deleteReviewBtn.setVisibility(View.INVISIBLE);
            return view;
        }
        //如果用户已经登录，判断当前用户是否于评论用户名相同
        if (bookReview.getUsername().equals(myApplication.getLoginUser().getUsername())){
            viewHolder.deleteReviewBtn.setVisibility(View.VISIBLE);
            viewHolder.deleteReviewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bookReviewList.size()>0){
                        bookReviewList.remove(position);
                        notifyDataSetChanged();
                    }
                    deleteBookReview(bookReview,position);
                }
            });
        } else{
            viewHolder.deleteReviewBtn.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    class ViewHolder{
        ImageView reviewUserIcon;
        TextView reviewUsername;
        TextView reviewPublishDate;
        TextView reviewContent;
        TextView reviewAgreeCount;
        Button deleteReviewBtn;
    }
    public void deleteBookReview(final BookReview bookReview, final int position){
        OkHttpUtil.httpGet("http://192.168.1.2:8080/bookreview/delete/" + bookReview.getId(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.d("delete","success"+bookReview.getId());

                if (activity instanceof BookReviewActivity){

                    Log.d("context instance of ","BookReviewActivity");
                } else if (activity instanceof BookDetailActivity){
                    Log.d("context instance of ","BookDetailActivity");
                }

            }
        });
    }

}
