package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myproject.model.Journal;
import com.example.myproject.ui.JournalRecycleViewAdapter;
import com.example.myproject.util.JournalApi;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class JournalListActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser CurrentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Journal> journalList;
    private RecyclerView recyclerView;
    private CollectionReference collectionReference = db.collection("Journal");
    private TextView no_posts;

    @Override
    protected void onStart() {
        super.onStart();
        collectionReference.whereEqualTo("userId", JournalApi.getInstance().getUserId()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()) {
                    for(DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        Journal journal = snapshot.toObject(Journal.class);
                        journalList.add(journal);
                    }
                    // Создание и вызов recycleView
                    JournalRecycleViewAdapter journalRecycleViewAdapter = new JournalRecycleViewAdapter(JournalListActivity.this, journalList);
                    recyclerView.setAdapter(journalRecycleViewAdapter);
                    journalRecycleViewAdapter.notifyDataSetChanged();
                } else {
                    no_posts.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_list);
        mAuth = FirebaseAuth.getInstance();
        CurrentUser = mAuth.getCurrentUser();
        no_posts = findViewById(R.id.no_posts);
        journalList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_new_post:
                if(mAuth != null && CurrentUser != null) {
                    startActivity(new Intent(JournalListActivity.this, PostJurnalActivity.class));
                }
            break;
            case R.id.sign_out:
                if(mAuth != null && CurrentUser != null) {
                    mAuth.signOut();
                    startActivity(new Intent(JournalListActivity.this, LoginAccount.class));
                }
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
