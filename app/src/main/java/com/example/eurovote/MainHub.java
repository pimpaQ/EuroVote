package com.example.eurovote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class MainHub extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hub);
    }
    public void FirstSemi_Click(View view){
        Intent intent = new Intent(MainHub.this, MainPage.class);
        startActivity(intent);
        finish();

    }
    public void SecondSemi_Click(View view){
        Intent intent = new Intent(MainHub.this, SecondSF.class);
        startActivity(intent);
        finish();
    }
    public void Final_Click(View view){
        Intent intent = new Intent(MainHub.this, AutoFinal.class);
        startActivity(intent);
        finish();
    }
    public void  Vote_Click(View view){
        int userId = getIntent().getIntExtra("userId", -1);
        Intent intent = new Intent(MainHub.this, VoteActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
    }
}