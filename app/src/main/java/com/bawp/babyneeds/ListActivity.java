package com.bawp.babyneeds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.bawp.babyneeds.data.DatabaseHandler;
import com.bawp.babyneeds.model.Item;
import com.bawp.babyneeds.ui.RecycleViewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private static final String TAG = "ListActivity";
    private RecyclerView recyclerView;
    private RecycleViewsAdapter recyclerViewsAdapter;
    private List<Item> itemList;
    private DatabaseHandler databaseHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.recyclerview);
        databaseHandler = new DatabaseHandler(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();
        // Get items from db
        itemList = databaseHandler.getAllItems();
        for(Item item: itemList) {
            Log.d(TAG, "onCreate: " + item);
        }

        recyclerViewsAdapter = new RecycleViewsAdapter(this, itemList);
        recyclerView.setAdapter(recyclerViewsAdapter);
        recyclerViewsAdapter.notifyDataSetChanged();
    }
}
