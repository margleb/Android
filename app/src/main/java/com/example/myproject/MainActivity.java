package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText add_note;
    private EditText add_thought;
    private Button submit_botton;
    // ключи
    private static final String KEY_NOTE = "key_note";
    private static final String KEY_THOUGHT = "key_thought";
    private static final String TAG = "ErrorFireStore";
    // подключение
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_note = findViewById(R.id.add_note);
        add_thought = findViewById(R.id.add_thought);
        submit_botton = findViewById(R.id.submit_botton);

        submit_botton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = add_note.getText().toString().trim();
                String thought = add_thought.getText().toString().trim();
                Map<String, Object> data = new HashMap<>();
                data.put(KEY_NOTE, note);
                data.put(KEY_THOUGHT, thought);

                firebaseFirestore
                        .collection("Journal")
                        .document("Thought")
                        .set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this, "Sucess!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: " + e.getStackTrace());
                        }
                    });
            }
        });
    }
}
