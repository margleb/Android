package com.bawp.soundpool;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SurfaceHolder.Callback {
    Button play, pause, skip;
    MediaPlayer mediaPlayer;
    SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = mediaPlayer.create(MainActivity.this, R.raw.video);

        surfaceView = findViewById(R.id.surface);
        surfaceView.setKeepScreenOn(true);

        SurfaceHolder holder = surfaceView.getHolder();
        holder.setFixedSize(400, 300);
        holder.addCallback(this);

        play = findViewById(R.id.button_blay);
        pause = findViewById(R.id.button_pause);
        skip = findViewById(R.id.button_skip);

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        skip.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_blay:
                mediaPlayer.start();
              break;
            case R.id.button_pause:
                mediaPlayer.pause();
              break;
            case R.id.button_skip:
                mediaPlayer.seekTo(mediaPlayer.getDuration() / 2);
              break;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mediaPlayer.setDisplay(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}