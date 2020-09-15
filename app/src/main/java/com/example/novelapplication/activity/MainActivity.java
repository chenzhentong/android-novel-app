package com.example.novelapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.example.novelapplication.R;
public class MainActivity extends BaseActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // Log.d("onActivityResult","onActivityResult:"+resultCode+" "+RESULT_OK);
        //getBookShelfItemList();
        //getBookShelfItemList();
        //getUserById(myApplication.getLoginUser().getId());

    }

    @Override
    protected void onResume() {

        super.onResume();
        //Log.d("onResume","onResume");
//        getBookShelfItemList();
    }
}
