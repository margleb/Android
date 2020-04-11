package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button playing_button;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.example);
        playing_button = findViewById(R.id.play_button);

        playing_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()) {
                    // останавливаем медиа плеер
                    stopPlaying();
                    playing_button.setText("STOP");
                } else {
                    startPlaying();
                    playing_button.setText("START");
                }
            }
        });
    }

    public void startPlaying() {
        mediaPlayer.start();
    }

    public void stopPlaying() {
        mediaPlayer.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
