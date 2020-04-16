package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.data.Journal;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.DocumentCollections;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText add_note, add_thought;
    private Button submit_button, show_button, update_button, delete_button;
    private TextView show_note, show_thought;
    // ключи
    private static final String KEY_NOTE = "key_note";
    private static final String KEY_THOUGHT = "key_thought";

    // подключение
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private DocumentReference JournalThoughtData = firebaseFirestore.collection("Journal").document("Thought");
    private CollectionReference JournalData =  firebaseFirestore.collection("Journal");

    private static final String TAG = "ErrorFireStore";

    @Override
    protected void onStart() {
        super.onStart();
        JournalThoughtData.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot != null && documentSnapshot.exists()) {

                    Journal journal = documentSnapshot.toObject(Journal.class);

                    if(journal != null) {
                        show_note.setText(journal.get_note());
                        show_note.setText(journal.get_thought());
                    }

                    // String note = documentSnapshot.getString(KEY_NOTE);
                    // String thought = documentSnapshot.getString(KEY_THOUGHT);
                    // show_note.setText(note);
                    // show_thought.setText(thought);
                } else {
                    show_note.setText("");
                    show_thought.setText("");
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_note = findViewById(R.id.add_note);
        add_thought = findViewById(R.id.add_thought);
        submit_button = findViewById(R.id.submit_button);
        show_button = findViewById(R.id.show_button);
        update_button = findViewById(R.id.update_button);
        show_note = findViewById(R.id.show_note);
        show_thought = findViewById(R.id.show_thought);
        delete_button = findViewById(R.id.delete_button);

        update_button.setOnClickListener(this);
        delete_button.setOnClickListener(this);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToJournal();
            }
        });

         show_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFromJournal();
            }
        });
    }

    public void addToJournal() {
        String note = add_note.getText().toString().trim();
        String thought = add_thought.getText().toString().trim();
        Journal journal = new Journal(note, thought);
        // journal.set_note(note);
        // journal.set_thought(thought);
        JournalData.add(journal);
    }

    public void ShowFromJournal() {
        JournalData.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {
                    Log.d("DoucmentId", "onSuccess: " + snapshots.getId());
                }
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.update_button:
                updateData();
                break;
            case R.id.delete_button:
                deleteData();
        }
    }

    private void deleteData() {
        JournalThoughtData.update(KEY_NOTE, FieldValue.delete());
        // Journal.delete();
    }

    private void updateData() {
        String note = add_note.getText().toString().trim();
        String thought = add_thought.getText().toString().trim();
        Map<String, Object> data = new HashMap<>();
        data.put(KEY_NOTE, note);
        data.put(KEY_THOUGHT, thought);

        JournalThoughtData.update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this, "Updatet Success!", Toast.LENGTH_SHORT);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.getStackTrace());
            }
        });
    }
}
