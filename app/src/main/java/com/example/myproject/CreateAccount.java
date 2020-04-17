package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateAccount extends AppCompatActivity {
    // авторизация
    private FirebaseAuth mAuth;
    // слушатель собывтия авторизации
    private FirebaseAuth.AuthStateListener mAuthListener;
    // текущий авторизирвоанный пользователь
    private FirebaseUser currentUser;

    private FirebaseFirestore firebaseFireStore = FirebaseFirestore.getInstance();

    private TextView username_account;
    private AutoCompleteTextView email_account;
    private EditText password_account;
    private Button create_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        // инициализируем авторизацию
        mAuth = FirebaseAuth.getInstance();

        username_account = findViewById(R.id.username_account);
        email_account = findViewById(R.id.email_account);
        password_account = findViewById(R.id.password_account);
        create_account = findViewById(R.id.create_account);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();
                if(currentUser != null) {
                    // пользователь уже существует
                } else {

                }
            }
        };
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              create_user_account(username, password, email);
            }
        });
    }

    public void create_user_account(final String username, String password, String email) {
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(email)) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        // создать запись в бд
                        currentUser = mAuth.getCurrentUser();
                        assert currentUser != null;
                        // получаем id пользователя
                        String currentUserId = currentUser.getUid();
                        // Cоздаем мапу для добавляения пользователя
                        Map<String, String> user = new HashMap<>();
                        user.put("userId", currentUserId);
                        user.put("username", username);
                        // сохраняем данные в firestore

                    } else {
                        // что то пошло не так
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(mAuthListener);
    }
}
