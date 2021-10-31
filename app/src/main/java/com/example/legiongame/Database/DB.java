package com.example.legiongame.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper{

    private static final int VERSION = 1;
    private static final String DB_NAME = "users.db";
    private static DB database;


    public DB(Context context){super(context, DB_NAME, null, VERSION);}


    @Override
    public void onCreate(SQLiteDatabase db) {
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
}
