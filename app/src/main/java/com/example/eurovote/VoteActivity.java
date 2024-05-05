package com.example.eurovote;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDate;

public class VoteActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private CRA2 adapter;
    Cursor cursor;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        www();
    }
    public void www() {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("eurovote_db.db", MODE_PRIVATE, null);

        cursor = db.rawQuery("SELECT * FROM Artists", null);

        // Создаем адаптер и устанавливаем его в ListView
        adapter = new CRA2(this, cursor);
        RecyclerView dd = findViewById(R.id.list);
        dd.setAdapter(adapter);
    }
    public void Back_Click(View view){
        Intent intent = new Intent(VoteActivity.this, MainHub.class);
        startActivity(intent);
        finish();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Result_Click(View view){
        LocalDate today = LocalDate.now();
        // Устанавливаем день и месяц для 9 мая
        int may = 5; // Май
        int ninth = 11;
        LocalDate ninthOfMay = LocalDate.of(today.getYear(), may, ninth);
        if(today.equals(ninthOfMay)) {
            Intent intent = new Intent(VoteActivity.this, ResultActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Результаты будут доступны после финала Евровидения 11 мая!", Toast.LENGTH_SHORT).show();
        }
    }
    public void SaveBtn_Click(View view){

        int userId = getIntent().getIntExtra("userId", -1);
        if (userId != -1) {
            SQLiteDatabase db = getBaseContext().openOrCreateDatabase("eurovote_db.db", MODE_PRIVATE, null);
            db.beginTransaction();
            try {
                for (int i = 1; i <= 37; i++) {
                    int artistId = i;
                    int selectedVote = Integer.parseInt(adapter.getSelectedItemValue(i));

                    String sqlQuery = "INSERT INTO Vote (UserID, ArtistID, Points) VALUES (?, ?, ?)";
                    SQLiteStatement statement = db.compileStatement(sqlQuery);
                    statement.bindLong(1, userId);
                    statement.bindLong(2, artistId);
                    statement.bindLong(3, selectedVote);
                    statement.executeInsert();
                }
                Toast.makeText(this, "Голоса сохранены", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Ошибка при сохранении голосов", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "ID пользователя не найден", Toast.LENGTH_SHORT).show();
        }

    }
}