package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
        // устанавливаем событие клика на сообщение
        showGuessTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // возращает намерение, вызваенное данным activity
                Intent intent = getIntent();
                // putExtra - добавляет рассширенные данные для данного намерения
                // name, value - ключ и значение
                intent.putExtra("message_back", "From Second Activity");
                // устанавливает результат, который activity будет возращать для данного вызова
                // RESULT_OK - стандартный результат, операцияя завершена успешно
                setResult(RESULT_OK, intent);
                // закрывает текущее activity
                finish();
            }
        });

    }
}
