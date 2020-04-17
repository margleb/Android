package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.util.JournalApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
    // экземпляр класса firestore
    private FirebaseFirestore firebaseFireStore = FirebaseFirestore.getInstance();
    // создание журнала firestor
    private CollectionReference journal = firebaseFireStore.collection("Users");
    private TextView username_account;
    private AutoCompleteTextView email_account;
    private EditText password_account;
    private Button create_account;
    private ProgressBar progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        // инициализируем авторизацию
        mAuth = FirebaseAuth.getInstance();

        username_account = findViewById(R.id.username_account);
        email_account = findViewById(R.id.email_account);
        password_account = findViewById(R.id.password_account);
        create_account = findViewById(R.id.create_acc_button_login);
        progress_bar = findViewById(R.id.progress_bar);

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

                progress_bar.setVisibility(View.VISIBLE);

                String username = username_account.getText().toString();
                String password = password_account.getText().toString();
                String email = email_account.getText().toString();

                if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(email))  {
                    create_user_account(username, password, email);
                } else {
                    Toast.makeText(CreateAccount.this, "Fields cannot be empty", Toast.LENGTH_SHORT);
                }
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
                        journal.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.getResult().exists()) {
                                            String userame = task.getResult().getString("username");

                                            JournalApi journalApi = new JournalApi();
                                            journalApi.setUserId(currentUser.getUid());
                                            journalApi.setUsername(userame);

                                            Intent intent = new Intent(CreateAccount.this, PostJurnalActivity.class);
                                            intent.putExtra("username", userame);
                                            intent.putExtra("userId", currentUser.getUid());
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
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
