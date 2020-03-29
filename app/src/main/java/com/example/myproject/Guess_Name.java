package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Guess_Name extends AppCompatActivity {
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess__name);
        text = findViewById(R.id.textView);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            text.setText(extras.getString("guess"));
        }

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("guess2", "from Guess_Name activity");
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }

}
