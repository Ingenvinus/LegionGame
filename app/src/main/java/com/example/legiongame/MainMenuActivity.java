package com.example.legiongame;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainMenuActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // background animation
        ConstraintLayout constraintLayout = findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();


        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        final Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                checkUser();
            }
        });

        final Button buttonStart = findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        final Button buttonSettings = findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });

        final Button buttonHighscore = findViewById(R.id.buttonHighscore);
        buttonHighscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, HighscoreActivity.class);
                startActivity(intent);
            }
        });

    }

    private void checkUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null){
            // user not logged in
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        else {
            // user logged in
            String email = firebaseUser.getEmail();
            Toast.makeText(getApplicationContext(), "Signed in as:\n"+email, Toast.LENGTH_SHORT).show();
        }
    }
    

    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.actions, popup.getMenu());
        popup.show();
    }

    public void exitGame(MenuItem item){
        Intent intent = new Intent(MainMenuActivity.this, IntroScreenActivity.class);
        startActivity(intent);
    }

    public void profileActivity(MenuItem item){
        Intent intent = new Intent(MainMenuActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void aboutActivity(MenuItem item){
        Intent intent = new Intent(MainMenuActivity.this, AboutActivity.class);
        startActivity(intent);
    }


}