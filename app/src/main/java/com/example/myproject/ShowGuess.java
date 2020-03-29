package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ShowGuess extends AppCompatActivity {
    private TextView showGuessTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_guess);
        // получаем массив переданных extras()
        Bundle extra = getIntent().getExtras();
        // получаем значение с прошлого activity
        showGuessTextView = findViewById(R.id.recieved_text_view);
        // устанавлиевает значение из прошлого activity, если оно есть
        if(extra != null) {
            // выводим в msg сообщение по ключу name
            Log.d("Name extra", " " + extra.getString("name"));
            Log.d("Name extra 2", " " + extra.getInt("age"));
            showGuessTextView.setText(extra.getString("guess"));
        }
    }
}
