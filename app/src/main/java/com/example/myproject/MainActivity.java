package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button playing_button;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = new MediaPlayer();
        playing_button = findViewById(R.id.play_button);
        seekBar = findViewById(R.id.seekBar);

        try {
            mediaPlayer.setDataSource("https://buildappswithpaulo.com/music/watch_me.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                int duraction = mp.getDuration();
                Toast.makeText(MainActivity.this, String.valueOf((duraction/1000)/60), Toast.LENGTH_SHORT).show();
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        MediaPlayer.OnPreparedListener onPrepared = new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp) {
                seekBar.setMax(mp.getDuration());
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
