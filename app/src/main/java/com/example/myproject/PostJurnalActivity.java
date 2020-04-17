package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myproject.util.JournalApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostJurnalActivity extends AppCompatActivity {
    
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private ImageView post_camera_button;
    private TextView user_name;
    private TextView time_add;
    private EditText post_title_et;
    private EditText post_description_et;
    private ProgressBar post_progressBar;
    private Button post_save_journal_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_jurnal);

       post_camera_button = findViewById(R.id.post_camera_button);
       user_name = findViewById(R.id.user_name);
       time_add = findViewById(R.id.time_add);
       post_title_et = findViewById(R.id.post_title_et);
       post_description_et = findViewById(R.id.post_description_et);
       post_progressBar = findViewById(R.id.post_progressBar);
       post_save_journal_button = findViewById(R.id.post_save_journal_button);

        mAuth = FirebaseAuth.getInstance();

    }
}
