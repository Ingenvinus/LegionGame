package com.example.legiongame;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.legiongame.Database.DB;
import com.example.legiongame.Models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainMenuActivity extends AppCompatActivity {

    private boolean mute = false;
    private FirebaseAuth firebaseAuth;
    private MediaPlayer mediaPlayer;
    private BottomNavigationView bottomNavigationView;
    public User gameUser;
    public DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DB.createDatabase(MainMenuActivity.this);
        db = DB.getDatabase();
        //set mute button icon


        // background music
        mediaPlayer = MediaPlayer.create(MainMenuActivity.this,R.raw.menu_song);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        // background animation
        ConstraintLayout constraintLayout = findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();


        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        //create user
        gameUser = new User(firebaseAuth.getCurrentUser().getDisplayName(), 0);

        // Buttons

        final Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                checkUser();
            }
        });

        final Button muteButton = findViewById(R.id.muteButton);
        //muteButton.setBackgroundResource(R.drawable.outline_volume_up_white_24dp);
        muteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mute){
                    muteButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.outline_volume_off_white_24dp, 0,0,0);
                    mediaPlayer.stop();
                    mute = true;
                    // show muted icon
                } else {
                    muteButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.outline_volume_up_white_24dp, 0,0,0);
                    // background music
                    mediaPlayer = MediaPlayer.create(MainMenuActivity.this,R.raw.menu_song);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                    mute = false;
                    // show normal icon
                }
            }
        });

        final Button buttonStart = findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, GameActivity.class);
                startActivity(intent);
                mediaPlayer.stop();
            }
        });

        final Button mProfileButton = findViewById(R.id.profileButton);
        mProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        final Button mAboutButton = findViewById(R.id.aboutButton);
        mAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        final Button mHomeButton = findViewById(R.id.homeButton);
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, IntroScreenActivity.class);
                startActivity(intent);
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

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }
    @Override
    protected void onPause(){
        super.onPause();
        mediaPlayer.pause();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mediaPlayer.stop();
    }


}