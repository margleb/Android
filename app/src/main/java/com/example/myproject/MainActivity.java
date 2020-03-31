package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {
    private static final String MESSAGE_ID = "messages_prefs";
    private TextView textInfo;
    private Button saveButton;
    private TextView enterMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInfo = findViewById(R.id.text_info);
        saveButton = findViewById(R.id.saveButton);
        enterMessage = findViewById(R.id.enter_message);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String message = enterMessage.getText().toString().trim();
              // mode_private - гарантирует что только это приложение сможет использовать данную настройку
              SharedPreferences sharedPreferences = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
              SharedPreferences.Editor editor = sharedPreferences.edit();
              editor.putString("message", message);
              editor.apply(); // сохраняем на диск
            }
        });
        // Get Data back from SP
        SharedPreferences getShareData = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
        String value = getShareData.getString("message", "Nothing yet");
        textInfo.setText(value);
    }

}
