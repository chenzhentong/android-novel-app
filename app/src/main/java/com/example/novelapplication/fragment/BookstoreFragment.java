package com.example.novelapplication.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.novelapplication.R;
import com.example.novelapplication.activity.MainActivity;
import com.example.novelapplication.util.LayoutUtil;


public class BookstoreFragment extends Fragment {


    private Activity activity;
    public BookstoreFragment() {

        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        activity= getActivity();

        View view = inflater.inflate(R.layout.fragment_bookstore, container, false);
//       if (activity instanceof MainActivity){
//            LayoutUtil.setMargins(view,0,200,0,0);
//        }
        return view;
    }



}
