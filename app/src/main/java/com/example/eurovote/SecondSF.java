package com.example.eurovote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class SecondSF extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private CustomRecyclerAdapter adapter;
    Cursor cursor;
    boolean openForLink = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_sf);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        www();
        if(openForLink) {
            ddd();
        }
    }
    public void Back_Click(View view){
        Intent intent = new Intent(SecondSF.this, MainHub.class);
        startActivity(intent);
        finish();
    }
    public void www() {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("eurovote_db.db", MODE_PRIVATE, null);
        cursor = db.rawQuery("SELECT * FROM Artists WHERE Place = 2", null);
        // Создаем адаптер и устанавливаем его в ListView
        adapter = new CustomRecyclerAdapter(this, cursor);
        RecyclerView dd = findViewById(R.id.list);
        dd.setAdapter(adapter);
    }
    public void ddd()
    {

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("eurovote_db.db", MODE_PRIVATE, null);
        int itemPosition = getIntent().getIntExtra("itemPosition", -1);
        itemPosition = itemPosition + 15;
        Cursor cursor = db.rawQuery("SELECT Source FROM Artists WHERE Place = 1 AND ArtistID == ?", new String[]{String.valueOf(itemPosition)});
        if (cursor != null && cursor.moveToFirst()) {
            // Получаем ссылку на видео из курсора
            @SuppressLint("Range") String videoUrl = cursor.getString(cursor.getColumnIndex("Source"));

            // Создаем Intent для открытия ссылки в браузере
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));

            // Запускаем Intent
            startActivity(intent);
        }
    }

}