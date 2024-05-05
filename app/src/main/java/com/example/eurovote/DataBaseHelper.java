package com.example.eurovote;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH; // полный путь к базе данных
    private static String DB_NAME = "eurovote_db.db";
    private static final int SCHEMA = 1; // версия базы данных
    static final String USER = "User"; // название таблицы в бд
    static final String VOTE = "Vote";
    static final String ARTIST = "Artists";

    // названия столбцов
    static final String USER_ID = "UserID";
    static final String USER_NAME = "Login";
    static final String USER_PASSWORD = "Password";
    static final String ARTIST_ID = "ArtistID";
    static final String ARTIST_NAME = "Name";
    static final String ARTIST_SONG = "Song";
    static final String ARTIST_COUNTRY = "Country";
    static final String ARTIST_PHOTO = "Photo";
    static final String ARTIST_WHERE = "Where";
    static final String ARTIST_URL = "Source";
    static final String VOTE_ID = "VoteID";
    static final String VOTE_ARTIST_ID = "ArtistID";
    static final String VOTE_USER_ID = "UserID";
    static final String VOTE_POINT = "Points";


    private Context myContext;

    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA);
        this.myContext=context;
        DB_PATH =context.getFilesDir().getPath() + DB_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) { }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) { }

    void create_db(){

        File file = new File(DB_PATH);
        if (!file.exists()) {
            //получаем локальную бд как поток
            try(InputStream myInput = myContext.getAssets().open(DB_NAME);
                // Открываем пустую бд
                OutputStream myOutput = new FileOutputStream(DB_PATH)) {

                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
            }
            catch(IOException ex){
                Log.d("DatabaseHelper", ex.getMessage());
            }
        }
    }
    public SQLiteDatabase open()throws SQLException {

        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
    public static long addUser(String username, String password) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put(username, username);
        values.put(password, password);
        long id = db.insert(USER, null, values);
        db.close();
        return id;
    }

}
