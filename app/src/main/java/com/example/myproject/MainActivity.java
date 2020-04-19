package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Drawing drawing = new Drawing(this);
        CustomTextView ctv = new CustomTextView(this);
        setContentView(ctv);
    }
}
