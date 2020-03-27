package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    private Button myButton;
    private TextView showtext;
    private EditText enterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // получаем id кнопки
        myButton = findViewById(R.id.button);
        enterName = findViewById(R.id.editText);
        // устанавливаем обработчик события
        myButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // получаем значение текста из поля
               String name = enterName.getText().toString();
               // устанавливаем значение текста
               showtext.setText("Hello, " + name);
           }
        });


        showtext = findViewById(R.id.textView);

    }
}
