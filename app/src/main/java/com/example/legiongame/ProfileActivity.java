package com.example.legiongame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        TextView userName = (TextView) findViewById(R.id.textView4);
        TextView userEmail = (TextView) findViewById(R.id.textView6);

        if (firebaseUser != null){
            String profilePicture = Objects.requireNonNull(firebaseUser.getPhotoUrl()).toString();
            ImageView userImage = (ImageView) findViewById(R.id.userImage);
            Glide.with(this).load(profilePicture).into(userImage);

            userEmail.setText(firebaseUser.getEmail());
            userName.setText(firebaseUser.getDisplayName());
        }

        final Button buttonReturn = (Button) findViewById(R.id.buttonReturn);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });



    }
}