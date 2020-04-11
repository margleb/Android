package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button playing_button;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = new MediaPlayer();
        playing_button = findViewById(R.id.play_button);

        try {
            mediaPlayer.setDataSource("https://buildappswithpaulo.com/music/watch_me.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }

        MediaPlayer.OnPreparedListener onPrepared = new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp) {
                // mp.start();
                playing_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mp.isPlaying()) {
                            // останавливаем медиа плеер
                            mp.stop();
                            playing_button.setText("STOP");
                        } else {
                            mp.start();
                            playing_button .setText("START");
                        }
                    }
                });
            }
        };

        mediaPlayer.setOnPreparedListener(onPrepared);
        mediaPlayer.prepareAsync();
        // mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.example);


    }

//    public void startPlaying() {
//        if(mediaPlayer != null) {
//            mediaPlayer.start();
//            playing_button.setText("START");
//        }
//    }
//
//    public void stopPlaying() {
//        if(mediaPlayer != null) {
//            mediaPlayer.stop();
//            playing_button.setText("STOP");
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
