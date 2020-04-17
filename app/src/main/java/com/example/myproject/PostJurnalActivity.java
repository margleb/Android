package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myproject.util.JournalApi;
import com.google.api.LogDescriptor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostJurnalActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_CODE = 1;
    private FirebaseAuth mAuth;
    // слушатель собывтия авторизации
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser currentUser;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private ImageView post_camera_button;
    private TextView user_name;
    private TextView time_add;
    private ImageView baground_image;
    private EditText post_title_et;
    private EditText post_description_et;
    private ProgressBar post_progressBar;
    private Button post_save_journal_button;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_jurnal);

       mAuth = FirebaseAuth.getInstance();
       user_name = findViewById(R.id.user_name);
       time_add = findViewById(R.id.time_add);
       post_title_et = findViewById(R.id.post_title_et);
       post_description_et = findViewById(R.id.post_description_et);
       post_progressBar = findViewById(R.id.post_progressBar);
       baground_image = findViewById(R.id.baground_image);


       post_camera_button = findViewById(R.id.post_camera_button);
       post_camera_button.setOnClickListener(this);
       post_save_journal_button = findViewById(R.id.post_save_journal_button);
       post_save_journal_button.setOnClickListener(this);

       mAuthListener = new FirebaseAuth.AuthStateListener() {
           @Override
           public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               currentUser = firebaseAuth.getCurrentUser();
               if(currentUser != null) {
                    // Пользователь уже существует
               } else {

               }
           }
       };

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id. post_save_journal_button:
                break;
            case R.id.post_camera_button:
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Log.d("ImageUrl", "onActivityResult2: ");
            if(data != null) {
                Log.d("ImageUrl", "onActivityResult3: " + imageUri);
                imageUri = data.getData();
                baground_image.setImageURI(imageUri);
            }
        }
    }
}
