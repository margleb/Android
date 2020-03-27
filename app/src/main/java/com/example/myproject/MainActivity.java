package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button showMoney;
    private Button showTag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showMoney = findViewById(R.id.button_make_rain);
        showTag = findViewById(R.id.button_show_tag);

        // showMoney.setOnClickListener(new View.OnClickListener() {
            // @Override
            // public void onClick(View v) {
                // Log.d("MYTAG", "onClick: Show Money");
                // System.out.println("Hello!");
            // }
        // });
    }

    // работает, благодаря указанаму атрибуту onclick в xml
    public void showTag(View v) {
        // по идеи должен выводить информацию в лог по событию onClick
        Log.d("MYTAG", "onClick: Show Money");
        // System.out.println("Hello!");
    }

    public void makeItRain(View v) {
        Log.d("MIR", "makeItRain: Trapped");
        // System.out.println("Hello!");
    }


}
