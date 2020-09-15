package com.example.novelapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novelapplication.R;
import com.example.novelapplication.common.MyApplication;
import com.example.novelapplication.model.User;
import com.example.novelapplication.util.OkHttpUtil;
import com.example.novelapplication.util.ServerConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends BaseActivity implements TextWatcher, View.OnClickListener {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView login;
    private MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myApplication=(MyApplication)getApplication();
        login=(TextView)findViewById(R.id.login);
        login.setOnClickListener(this);
        usernameEditText=(EditText)findViewById(R.id.username);
        passwordEditText=(EditText)findViewById(R.id.password);
        usernameEditText.addTextChangedListener(this);
        passwordEditText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {

//        Log.d("aaa",(username.getText().toString().equals(""))+"");
//        Log.d("bbb",(password.getText().toString().equals(""))+"");
        if (usernameEditText.getText().toString().equals("")||passwordEditText.getText().toString().equals("")){

            login.setEnabled(false);
        } else{
            login.setEnabled(true);
        }
    }
    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                login();
                //
                break;
            default:
                break;
        }
    }
    public void login(){
        String username=usernameEditText.getText().toString();
        String password=passwordEditText.getText().toString();
        OkHttpUtil.httpGet(ServerConfig.getIp()+":8080/getUserByAccount/" + username + "/" + password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this,"未连接到服务端",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData=response.body().string();
                Gson gson=new Gson();
                User user=gson.fromJson(responseData,new TypeToken<User>(){}.getType());
                //Log.d("user",(user==null)+"");
                if (user==null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                        }
                    });
                } else{
                    myApplication.userLogin(user);
                    finish();
                    startActivityForResult(new Intent(LoginActivity.this, PersonalCenterActivity.class),1);
                }
            }
        });
    }
}
