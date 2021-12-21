package com.example.legiongame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
        // initializing database
        db = DB.getDatabase();
        highscores = db.getHighscores();

        // setting the recyclerView with the contents of the highscore database
        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyRecyclerViewAdapter = new HighscoreAdapter(highscores);
        recyclerView.setAdapter(MyRecyclerViewAdapter);

        //Log.d("array", highscores.toString());

        Button returnButton2 = (Button) findViewById(R.id.buttonReturn2);
        returnButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HighscoreActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });



    }
}