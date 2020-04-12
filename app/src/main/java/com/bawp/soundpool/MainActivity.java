package com.bawp.soundpool;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonOne, buttonTwo, buttonThree;
    private SoundPool soundPool;
    private int sound1, sound2, sound3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonOne = findViewById(R.id.button_one);
        buttonTwo = findViewById(R.id.button_two);
        buttonThree = findViewById(R.id.button_three);

        buttonOne.setOnClickListener(this);
        buttonTwo.setOnClickListener(this);
        buttonThree.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    // тип контента
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    // для целей использования
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    // кол-во загружаемых файлов
                    .setMaxStreams(4)
                    .setAudioAttributes(audioAttributes)
                    .build();

            sound1 = soundPool.load(MainActivity.this, R.raw.click_one, 1);
            sound2 = soundPool.load(MainActivity.this, R.raw.click_two, 1);
            sound3 = soundPool.load(MainActivity.this, R.raw.click_three, 1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_one:
                soundPool.play(sound1, 1,1,1,0,1);
                break;
            case R.id.button_two:
                soundPool.play(sound2, 1,1,1,0,1);
                break;
            case R.id.button_three:
                soundPool.play(sound3, 1,1,1,0,1);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}
