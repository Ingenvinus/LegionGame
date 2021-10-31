package com.example.legiongame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.legiongame.Database.DB;

import java.util.ArrayList;

public class HighscoreActivity extends AppCompatActivity {

    DB db;
    ArrayList<Float> highscores;
    HighscoreAdapter MyRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        db = DB.getDatabase();
        highscores = db.getHighscores();

        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyRecyclerViewAdapter = new HighscoreAdapter(highscores);
        recyclerView.setAdapter(MyRecyclerViewAdapter);

        Log.d("array", highscores.toString());




    }
}