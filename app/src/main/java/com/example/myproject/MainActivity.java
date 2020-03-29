package com.example.myproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button showGuess;
    private EditText enterGuess;
    private final int REQUEST_CODE = 2; // код запроса

    @Override
    // данный метод отвечает за основные настройки при создании activity
    // например, определение макета пользовательского интерфеса
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Cycle", "onCreate: ");
        super.onCreate(savedInstanceState);
        // отображает макет пользовательского интерфейса
        setContentView(R.layout.activity_main);

        showGuess = findViewById(R.id.button_guess);
        enterGuess = findViewById(R.id.guess_field);
        showGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // получаем значение guess
                String guess = enterGuess.getText().toString().trim();
                if(!guess.isEmpty()) {
                    // создает намерение и переход на новый activity
                    Intent intent = new Intent(MainActivity.this, ShowGuess.class);
                    intent.putExtra("guess", guess);
                    intent.putExtra("name", "Bond");
                    intent.putExtra("age", 34);
                    // запускает новую activity
                    // startActivity(intent);
                    // Изменяет стандартное поведение, чтобы разрешить доставку результатов во фрагменты
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    Toast.makeText(MainActivity.this, "Enter guess", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    // метод позволяет обрабатывать полученные результаты от других activity
    // requestCode - какой либо код запроса (создается самостоятельно)
    // resultCode - код статуса, позволяющий оценить результат полученных данных
    // data - данные получаемые нами, @Nullable - говорит о том, что информация может быть null
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE) {
            assert data != null; // проверяет что данные не равны null
            // получаем значение по ключу
            String message = data.getStringExtra("message_back");
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }
}

