package com.example.novelapplication.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.novelapplication.R;
import com.example.novelapplication.sqlite.OpDatabase;
import com.example.novelapplication.util.UserManager;


public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivClose;
    private EditText etPwd;
    private EditText etPwd1;
    private Button btnU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        initView();
    }

    private void initView() {
        ivClose = findViewById(R.id.iv_close_update);
        etPwd = findViewById(R.id.et_pwd_update);
        etPwd1 = findViewById(R.id.et_pwd1_update);
        btnU = findViewById(R.id.btn_update);

        ivClose.setOnClickListener(this);
        btnU.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close_update:
                finish();
                break;
            case R.id.btn_update:
                updatePwd();
                break;
        }
    }

    private void updatePwd() {
        String pwd = etPwd.getText().toString();
        if (TextUtils.isEmpty(pwd)){
            Toast.makeText(this,"原密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pwd.equals(UserManager.get().getUser().getPwd())){
            Toast.makeText(this,"原密码错误", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd1 = etPwd1.getText().toString();
        if (TextUtils.isEmpty(pwd1)){
            Toast.makeText(this,"新密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pwd1.equals(pwd)){
            Toast.makeText(this,"两次密码不能一致", Toast.LENGTH_SHORT).show();
            return;
        }

        UserManager.get().getUser().setPwd(pwd1);

        OpDatabase.get().updateUser(UserManager.get().getUser());

        finish();
    }
}