package com.example.novelapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.novelapplication.R;
import com.example.novelapplication.common.MyApplication;


public class PersonalCenterActivity extends BaseActivity implements View.OnClickListener {

    private MyApplication myApplication;
    private TextView personalCenterUsername;
    private TextView personalCenterEmail;
    private TextView personalCenterResetPasswordTextView;
    private ImageView personalCenterResetPasswordImageView;
    private Button logoutButton;
    private ImageView personalCenterBackMine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        myApplication=(MyApplication)getApplication();
        personalCenterUsername=(TextView)findViewById(R.id.personal_center_username);
        personalCenterEmail=(TextView)findViewById(R.id.personal_center_email);
        personalCenterResetPasswordTextView=(TextView)findViewById(R.id.personal_center_reset_password_text_view);
        personalCenterResetPasswordImageView=(ImageView)findViewById(R.id.personal_center_reset_password_image_view);
        logoutButton=(Button)findViewById(R.id.logout_button);
        personalCenterBackMine=(ImageView)findViewById(R.id.personal_center_back_mine);
        personalCenterUsername.setText(myApplication.getLoginUser().getUsername());
        personalCenterEmail.setText(myApplication.getLoginUser().getEmail());
        personalCenterUsername.setOnClickListener(this);
        personalCenterEmail.setOnClickListener(this);
        personalCenterResetPasswordTextView.setOnClickListener(this);
        personalCenterResetPasswordImageView.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
        personalCenterBackMine.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.personal_center_back_mine:
                finish();
                break;
            case R.id.personal_center_email:


                break;
            case R.id.personal_center_reset_password_image_view:
            case R.id.personal_center_reset_password_text_view:
                Intent intent=new Intent(PersonalCenterActivity.this,ResetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.logout_button:
                myApplication.userLogout();
                finish();
                break;
        }

    }
}
