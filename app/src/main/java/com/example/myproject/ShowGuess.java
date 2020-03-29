package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ShowGuess extends AppCompatActivity {
    private TextView showGuessTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_guess);
        // получаем значение с прошлого activity
        showGuessTextView = findViewById(R.id.recieved_text_view);
        // устанавлиевает значение из прошлого activity, если оно есть
        if(getIntent().getStringExtra("guess") != null) {
            showGuessTextView.setText(getIntent().getStringExtra("guess"));
        }
    }
}
