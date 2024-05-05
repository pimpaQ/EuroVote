package com.example.eurovote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class Load_Window extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_window);
        ImageView imageView = findViewById(R.id.imagelogo);
        TranslateAnimation animation = new TranslateAnimation(-200, 200, 0, 0);
        animation.setDuration(4001);
        imageView.startAnimation(animation);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                int userId = getIntent().getIntExtra("userId", -1);
                Intent intent = new Intent(Load_Window.this, MainHub.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}