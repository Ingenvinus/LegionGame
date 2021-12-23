package com.example.legiongame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.legiongame.Database.DB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DB db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        TextView userName = (TextView) findViewById(R.id.textView4);
        TextView userEmail = (TextView) findViewById(R.id.textView6);

        db = DB.getDatabase();

        if (firebaseUser != null){
            String profilePicture = Objects.requireNonNull(firebaseUser.getPhotoUrl()).toString();
            ImageView userImage = (ImageView) findViewById(R.id.userImage);
            Glide.with(this).load(profilePicture).into(userImage);

            userEmail.setText(firebaseUser.getEmail());
            userName.setText(firebaseUser.getDisplayName());
        }

        final TextView textView = findViewById(R.id.showHighscore);
        textView.setText(highscore());

        final Button buttonReturn = (Button) findViewById(R.id.buttonReturn);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });


    }

    public String highscore() {
        float highscore_float = 0;
        ArrayList<Float> scores = db.getHighscores();

        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i) > highscore_float){
                highscore_float = scores.get(i);
            }
        }
        return String.valueOf(highscore_float);
    }

}