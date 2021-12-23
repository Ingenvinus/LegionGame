package com.example.legiongame.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper{

    private static final int VERSION = 1;
    private static final String DB_NAME = "users.db";
    private static DB database;


    public DB(Context context){super(context, DB_NAME, null, VERSION);}


    @Override
    public void onCreate(SQLiteDatabase db) {
        // create users table with userID, username and a highscore
        db.execSQL("CREATE TABLE USERS ("+
                "userID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "Username TEXT,"+
                "Highscore FLOAT);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists USERS");
        onCreate(db);
    }

    public void addNewUser(String username, float highscore){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Username", username);
        values.put("Highscore", highscore);
        db.insert("USERS", null, values);

    }

    public static void createDatabase(Context context){
        if(database == null){
            database = new DB(context);
        }
    }

    public static DB getDatabase(){
        if(database == null){
            database = null;
        }
        return database;
    }

    public ArrayList<Float> getHighscores(){
        //retrieving highscores from database and ordering them in a descending manner
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT Highscore FROM USERS ORDER BY Highscore DESC";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        final ArrayList<Float> highscores = new ArrayList<>();
        final int nameIndex = cursor.getColumnIndex("Highscore");
        while (!cursor.isAfterLast()){
            highscores.add(cursor.getFloat(nameIndex));
            cursor.moveToNext();
        }
        cursor.close();
        return highscores;
    }

    public ArrayList<String> getUsernames(){
        //retrieving Usernames from database and ordering them in a descending manner
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT Username, Highscore FROM USERS ORDER BY Highscore DESC";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        final ArrayList<String> usernames = new ArrayList<>();
        final int nameIndex = cursor.getColumnIndex("Username");
        final int nameIndexHS = cursor.getColumnIndex("Highscore");
        while (!cursor.isAfterLast()){

            String Username = cursor.getString(nameIndex);
            String Highscore = cursor.getString(nameIndexHS);


            String data = "  " + Username + " : " + Highscore;
            usernames.add(data);
            cursor.moveToNext();
        }
        cursor.close();
        return usernames;
    }



}
