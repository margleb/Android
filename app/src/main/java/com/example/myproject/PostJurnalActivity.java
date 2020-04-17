package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myproject.model.Journal;
import com.example.myproject.util.JournalApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.LogDescriptor;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

public class PostJurnalActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_CODE = 1;
    private FirebaseAuth mAuth;
    // слушатель собывтия авторизации
    private FirebaseAuth.AuthStateListener mAuthListener;
    private StorageReference mStorageRef;
    private FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference journalCollection;

    private ImageView post_camera_button;
    private TextView user_name;
    private TextView time_add;
    private ImageView baground_image;
    private EditText post_title_et;
    private EditText post_description_et;
    private ProgressBar post_progressBar;
    private Button post_save_journal_button;
    private Uri imageUri;

    private String currentUserId;
    private String currentUserName;

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
       mStorageRef = FirebaseStorage.getInstance().getReference();
       journalCollection = db.collection("Journal");

       post_progressBar.setVisibility(View.INVISIBLE);
       post_camera_button = findViewById(R.id.post_camera_button);
       post_camera_button.setOnClickListener(this);
       post_save_journal_button = findViewById(R.id.post_save_journal_button);
       post_save_journal_button.setOnClickListener(this);

        if (JournalApi.getInstance() != null) {
            currentUserId = JournalApi.getInstance().getUserId();
            currentUserName = JournalApi.getInstance().getUsername();
            user_name.setText(currentUserName);
        }

       mAuthListener = new FirebaseAuth.AuthStateListener() {
           @Override
           public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               currentUser = firebaseAuth.getCurrentUser();
               Log.d("currentUser", "work!: " + currentUser);
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
                saveJournal();
                break;
            case R.id.post_camera_button:
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, REQUEST_CODE);
                break;
        }
    }

    private void saveJournal() {
        post_progressBar.setVisibility(View.VISIBLE);

       final String titlePost = post_title_et.getText().toString().trim();
       final String descriptionPost = post_description_et.getText().toString().trim();
        Log.d("saveJournal", "saveJournal: " + titlePost);
        Log.d("saveJournal", "saveJournal: " + descriptionPost);
        Log.d("saveJournal", "saveJournal: " + imageUri);
       if(!TextUtils.isEmpty(titlePost) && !TextUtils.isEmpty(descriptionPost) && imageUri != null ) {
           final StorageReference filePath = mStorageRef.child("journal_mages").child("my_image_" + Timestamp.now().getSeconds());
           filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                       @Override
                       public void onSuccess(Uri uri) {
                           // сохранение данных в журнал
                           Journal journal  = new Journal();
                           journal.setTitle(titlePost);
                           journal.setDescription(descriptionPost);
                           String imageUrl = uri.toString();
                           journal.setImageUrl(imageUrl);
                           journal.setUserId(currentUserId);
                           journal.setTimeAdded(new Timestamp(new Date()));
                           journal.setUserName(currentUserName);
                           // добавляем в базу
                           journalCollection.add(journal).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                               @Override
                               public void onSuccess(DocumentReference documentReference) {
                                   post_progressBar.setVisibility(View.INVISIBLE);
                                   startActivity( new Intent(PostJurnalActivity.this, JournalListActivity.class));
                                   // вызывается, когда активити завершено и должно быть закрыто
                                   finish();
                               }
                           }).addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {

                               }
                           });
                       }
                   });
               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   post_progressBar.setVisibility(View.INVISIBLE);
               }
           });
       } else {
           post_progressBar.setVisibility(View.INVISIBLE);
       }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Log.d("ImageUrl", "onActivityResult2: ");
            if(data != null) {
                imageUri = data.getData();
                baground_image.setImageURI(imageUri);
            }
        }
    }
}
