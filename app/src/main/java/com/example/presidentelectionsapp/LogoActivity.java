package com.example.presidentelectionsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.window.SplashScreen;

import java.util.concurrent.TimeUnit;

public class LogoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(LogoActivity.this, MainActivity.class);
                LogoActivity.this.startActivity(mainIntent);
                LogoActivity.this.finish();
            }
        }, 4000);
    }
}