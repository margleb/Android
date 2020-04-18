package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myproject.model.Journal;
import com.example.myproject.util.JournalApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class LoginAccount extends AppCompatActivity {
    private Button login_button;
    private Button create_acc_button;
    private AutoCompleteTextView email;
    private ProgressBar progress_bar;
    private EditText password;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    // private FirebaseUser firebaseUser;
    private FirebaseFirestore db;
    private CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__account);

        login_button = findViewById(R.id.email_sign_in_button);
        create_acc_button = findViewById(R.id.create_acc_button);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        progress_bar = findViewById(R.id.progress_bar);

        // инициализация базы данных и коллекция пользователей
        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("Users");
        mAuth = FirebaseAuth.getInstance();

        create_acc_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAccount.this, CreateAccount.class));
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                singIn(email.getText().toString().trim(), password.getText().toString().trim());
            }
        });
    }

    private void singIn(String email, String password) {
        progress_bar.setVisibility(View.VISIBLE);
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    String currentUserId = user.getUid();
                    collectionReference.whereEqualTo("userId", currentUserId).addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            if(e != null) {
                                progress_bar.setVisibility(View.INVISIBLE);
                                // Log.d("CollectionFindFail", e.printStackTrace());
                            }
                            for(QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {
                                JournalApi journalApi = JournalApi.getInstance();
                                journalApi.setUsername(snapshots.getString("userName"));
                                journalApi.setUserId(snapshots.getString("userId"));

                                // Go to List Activtiy
                                startActivity(new Intent(LoginAccount.this, JournalListActivity.class));

                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progress_bar.setVisibility(View.INVISIBLE);
                    Log.d("FailedLogin", "onFailure: " + e.getStackTrace());
                }
            });
        } else {
            progress_bar.setVisibility(View.INVISIBLE);
            Toast.makeText(LoginAccount.this, "Field's cannot be empty", Toast.LENGTH_SHORT);
        }
    }
}
