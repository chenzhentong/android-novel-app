package com.example.novelapplication.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.novelapplication.R;

public class BookstoreNavbarFragment extends Fragment implements View.OnClickListener{

    private TextView wellChoose;
    private TextView sort;
    private TextView ranklist;
    private TextView booklist;
    private TextView wellChooseLine;
    private TextView sortLine;
    private TextView ranklistLine;
    private TextView booklistLine;
    public BookstoreNavbarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bookstore_navbar, container, false);
        wellChoose=(TextView)view.findViewById(R.id.well_choose);
        sort=(TextView)view.findViewById(R.id.sort);
        ranklist=(TextView)view.findViewById(R.id.ranklist);
        booklist=(TextView)view.findViewById(R.id.booklist);
        wellChooseLine=(TextView)view.findViewById(R.id.well_choose_line);
        sortLine=(TextView)view.findViewById(R.id.sort_line);
        ranklistLine=(TextView)view.findViewById(R.id.ranklist_line);
        booklistLine=(TextView)view.findViewById(R.id.booklist_line);
        wellChoose.setOnClickListener(this);
        sort.setOnClickListener(this);
        ranklist.setOnClickListener(this);
        booklist.setOnClickListener(this);
        updateNavbarUi(0);
        return view;
    }

    private void updateNavbarUi(int i){
        switch (i){
            case 0:
                wellChoose.setTextColor(getResources().getColor(R.color.colorRed));
                sort.setTextColor(getResources().getColor(R.color.blackDark));
                ranklist.setTextColor(getResources().getColor(R.color.blackDark));
                booklist.setTextColor(getResources().getColor(R.color.blackDark));
                wellChooseLine.setBackgroundColor(getResources().getColor(R.color.colorRed));
                sortLine.setBackgroundColor(getResources().getColor(R.color.blackDark));
                ranklistLine.setBackgroundColor(getResources().getColor(R.color.blackDark));
                booklistLine.setBackgroundColor(getResources().getColor(R.color.blackDark));
                break;
            case 1:
                wellChoose.setTextColor(getResources().getColor(R.color.blackDark));
                sort.setTextColor(getResources().getColor(R.color.colorRed));
                ranklist.setTextColor(getResources().getColor(R.color.blackDark));
                booklist.setTextColor(getResources().getColor(R.color.blackDark));
                wellChooseLine.setBackgroundColor(getResources().getColor(R.color.blackDark));
                sortLine.setBackgroundColor(getResources().getColor(R.color.colorRed));
                ranklistLine.setBackgroundColor(getResources().getColor(R.color.blackDark));
                booklistLine.setBackgroundColor(getResources().getColor(R.color.blackDark));
                break;
            case 2:
                wellChoose.setTextColor(getResources().getColor(R.color.blackDark));
                sort.setTextColor(getResources().getColor(R.color.blackDark));
                ranklist.setTextColor(getResources().getColor(R.color.colorRed));
                booklist.setTextColor(getResources().getColor(R.color.blackDark));
                wellChooseLine.setBackgroundColor(getResources().getColor(R.color.blackDark));
                sortLine.setBackgroundColor(getResources().getColor(R.color.blackDark));
                ranklistLine.setBackgroundColor(getResources().getColor(R.color.colorRed));
                booklistLine.setBackgroundColor(getResources().getColor(R.color.blackDark));
                break;
            case 3:
                wellChoose.setTextColor(getResources().getColor(R.color.blackDark));
                sort.setTextColor(getResources().getColor(R.color.blackDark));
                ranklist.setTextColor(getResources().getColor(R.color.blackDark));
                booklist.setTextColor(getResources().getColor(R.color.colorRed));
                wellChooseLine.setBackgroundColor(getResources().getColor(R.color.blackDark));
                sortLine.setBackgroundColor(getResources().getColor(R.color.blackDark));
                ranklistLine.setBackgroundColor(getResources().getColor(R.color.blackDark));
                booklistLine.setBackgroundColor(getResources().getColor(R.color.colorRed));
                break;
            default:break;
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.well_choose:
                updateNavbarUi(0);
                Log.d("0","0");
                break;
            case R.id.sort:
                updateNavbarUi(1);
                Log.d("1","1");
                break;
            case R.id.ranklist:
                updateNavbarUi(2);
                Log.d("2","2");
                break;
            case R.id.booklist:
                updateNavbarUi(3);
                Log.d("3","3");
                break;
            default:
                break;
        }
    }
}
