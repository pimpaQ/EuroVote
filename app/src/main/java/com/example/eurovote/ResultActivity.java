package com.example.eurovote;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.widget.Spinner;

import java.time.LocalDate;

public class ResultActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    private resultAdapter adapter;
    Cursor cursor;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        LocalDate today = LocalDate.now();

        // Устанавливаем день и месяц для 9 мая
        www();
    }
    public void www() {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("eurovote_db.db", MODE_PRIVATE, null);

        String query = "SELECT Artists.*, (SELECT SUM(Points) FROM Vote WHERE Artists.ArtistID == Vote.ArtistID) AS TotalPoints FROM Artists";
        cursor = db.rawQuery(query, null);

        // Создаем адаптер и передаем ему курсор с данными
        adapter = new resultAdapter(this, cursor);

        // Устанавливаем адаптер в RecyclerView
        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}