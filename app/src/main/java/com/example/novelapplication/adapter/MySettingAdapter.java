package com.example.novelapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.novelapplication.R;
import com.example.novelapplication.activity.RecentViewActivity;
import com.example.novelapplication.model.MySettingItem;

import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

public class MySettingAdapter extends ArrayAdapter<MySettingItem>  {

    private int resourceId;
    private List<MySettingItem> mySettingItemList=new ArrayList<>();
    public MySettingAdapter(@NonNull Context context, int resource, @NonNull List<MySettingItem> objects) {
        super(context, resource, objects);
        this.resourceId=resource;
        this.mySettingItemList=objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MySettingItem mySettingItem=getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
             viewHolder=new ViewHolder();
            viewHolder.text=(TextView)view.findViewById(R.id.my_setting_item_text);
            viewHolder.image=(ImageView)view.findViewById(R.id.my_setting_item_image);
            view.setTag(viewHolder);

        } else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();


        }
        viewHolder.text.setText(mySettingItem.getText());
        viewHolder.image.setImageResource(mySettingItem.getResourceId());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (mySettingItem.getResourceId()){
                    case R.drawable.recentview:
                        Intent intent=new Intent(getContext(), RecentViewActivity.class);
                        getContext().startActivity(intent);
                        break;

                }

            }
        });
        return view;
    }



    class ViewHolder{
        TextView text;
        ImageView image;
    }
}
