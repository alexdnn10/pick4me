package com.example.alexdnn10.pick4me;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton mButtonSignin;
    Button mFbButton;
    Button mTwButton;
    EditText mEditUsername;
    EditText mEditPassword;
    int screenWidth;
    int screenHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenWidth = displaymetrics.widthPixels;
        screenHeight = displaymetrics.heightPixels;

        mButtonSignin=(ImageButton) findViewById(R.id.button_signin);
        mEditUsername=(EditText) findViewById(R.id.edit_username);
        mEditPassword=(EditText) findViewById(R.id.edit_password);
        mEditUsername.setText(""+screenHeight);
        mEditPassword.setText(""+screenWidth);
        mButtonSignin.setOnClickListener(this);
        mFbButton=(Button) findViewById(R.id.button_facebook);
        mTwButton=(Button) findViewById(R.id.button_twitter);
        mFbButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( LoginActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        mTwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( LoginActivity.this, CropImageActivity.class);
                startActivity(intent);
            }
        });
    }
    public void onClick(View v)
    {
        Intent intent=new Intent();
        intent.setClass(this, NewsFeedActivity.class);
        startActivity(intent);
    }

}
