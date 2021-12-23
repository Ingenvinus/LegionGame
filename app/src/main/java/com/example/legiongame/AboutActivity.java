package com.example.legiongame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView YouTubeTextView = findViewById(R.id.textView10);
        YouTubeTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}