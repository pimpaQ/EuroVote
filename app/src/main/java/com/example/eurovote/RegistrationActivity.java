package com.example.eurovote;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    Cursor cursor;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }
    @SuppressLint("Range")
    public int getUserId(String login) {
        int userId = -1; // Значение по умолчанию -1, если пользователь с указанным логином не найден
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("eurovote_db.db", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT UserID FROM User WHERE Login = ?", new String[]{login});
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex("UserID"));
        }
        return userId;
    }
    public  void Regist_Click(View view)
    {
        TextView textname = findViewById(R.id.editTextText2);
        TextView textpassword = findViewById(R.id.editTextTextPassword2);
        String login = textname.getText().toString().trim();
        String password = textpassword.getText().toString().trim();
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("eurovote_db.db", MODE_PRIVATE, null);
        cursor = db.rawQuery("SELECT Login FROM User WHERE Login = ?", new String[]{login});
        count = cursor.getCount();
        if(login.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this, "Надо заполнить все поля!", Toast.LENGTH_SHORT).show();
        }
        else if (password.length() < 8)
        {
            Toast.makeText(this, "Пароль должен быть длиннее 8 символов!", Toast.LENGTH_SHORT).show();
        }
        else if(count > 0){
            Toast.makeText(this, "Пользователь под таким логином уже зарегистрирован! Выберите другой.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String sqlQuery = "INSERT INTO User (Login, Password) VALUES (?, ?)";
            SQLiteStatement statement = db.compileStatement(sqlQuery);
            statement.bindString(1, login);
            statement.bindString(2, password);
            statement.executeInsert();
            Intent intent = new Intent(RegistrationActivity.this, Load_Window.class);
            intent.putExtra("userId", getUserId(login)); // userId - это ID пользователя
            startActivity(intent);
        }
    }
    public void Enter_Click(View view)
    {

        TextView textname = findViewById(R.id.editTextText2);
        TextView textpassword = findViewById(R.id.editTextTextPassword2);
        String login = textname.getText().toString().trim();
        String password = textpassword.getText().toString().trim();
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("eurovote_db.db", MODE_PRIVATE, null);
        cursor = db.rawQuery("SELECT Login, Password FROM User WHERE Login == ? AND Password == ?", new String[] { login, password });
        count = cursor.getCount();
        if(login.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this, "Надо заполнить все поля!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(count > 0){
                Toast.makeText(this, "Добро пожаловать, " + login,  Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegistrationActivity.this, Load_Window.class);
                intent.putExtra("userId", getUserId(login)); // userId - это ID пользователя
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Неверный логин или пароль ", Toast.LENGTH_SHORT).show();
            }

        }
    }
    
}