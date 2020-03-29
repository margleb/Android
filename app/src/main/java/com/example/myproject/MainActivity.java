package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button showGuess;

    @Override
    // данный метод отвечает за основные настройки при создании activity
    // например, определение макета пользовательского интерфеса
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Cycle", "onCreate: ");
        super.onCreate(savedInstanceState);
        // отображает макет пользовательского интерфейса
        setContentView(R.layout.activity_main);

        showGuess = findViewById(R.id.button_guess);
        showGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // создает намерение и переход на новый activity
                Intent intent = new Intent(MainActivity.this, ShowGuess.class);
                startActivity(intent);
            }
        });
    }

    @Override
    // после инициализиации метода OnCreate(),
    // инициализируется onStart(), для отображения пользовательского интерфейса,
    // а также всего, что требуется для отображения пользователю
    protected void onStart() {
        super.onStart();
        Log.d("Cycle", "onStart: ");
        Toast.makeText(MainActivity.this, "OnCreate() Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    // инициализируется после метода OnStart(), получает все пользовательские вводы, реализрует весь основной функционал приложения
    // onPause() всегда следует за методом onResume();
    protected void onResume() {
        super.onResume();
        Log.d("Cycle", "onResume: ");
        Toast.makeText(MainActivity.this, "onPostResume() Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    // Вызывается когда пропадет фокусировка на приложении и приложение находится в состоянии паузы,
    // Не должно сохранять пользовательские данные, интернет  запросы, или выполнять какие либо операции с БД
    // После паузы вызывается либо метод onResume() либо onStop()
    protected void onPause() {
        super.onPause();
        Log.d("Cycle", "onPause: ");
        Toast.makeText(MainActivity.this, "onPause() Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    // Вызывается когда activity более не видимо пользователем
    // Следующий вызов является либо onRestart(), если activity сново вызывается для взаимодействия с пользователем,
    // либо onDestroy(), если полностью прекращает свое действие
    protected void onStop() {
        super.onStop();
        Log.d("Cycle", "onStop: ");
        Toast.makeText(MainActivity.this, "onStop() Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    // Вызывается, когда активити было остановлено (onStop()) и сново возобновлено
    // Данный взово всегда следует за onStart()
    protected void onRestart() {
        super.onRestart();
        Log.d("Cycle", "onRestart: ");
        Toast.makeText(MainActivity.this, "onRestart() Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    // Вызывает данный метод перед тем как activity будет уничтожено
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Cycle", "onDestroy: ");
        Toast.makeText(MainActivity.this, "onDestroy() Called", Toast.LENGTH_SHORT).show();
    }
}
