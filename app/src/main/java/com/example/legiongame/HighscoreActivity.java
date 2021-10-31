package com.example.legiongame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.legiongame.Database.DB;

public class HighscoreActivity extends AppCompatActivity {

    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        DB.getDatabase();

    }
}