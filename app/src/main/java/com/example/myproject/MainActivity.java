package com.example.myproject;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import com.example.myproject.adapter.RecycleViewAdapter;
import com.example.myproject.data.DatabaseHandler;
import com.example.myproject.model.Contact;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecycleViewAdapter recycleViewAdapter;
    private ArrayList<Contact> contactArrayList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1. получаем обработчик на обьект
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        // 2. cоединяем с layoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactArrayList = new ArrayList<>();
        DatabaseHandler db = new DatabaseHandler(this);

        List<Contact> contacts = db.getAllContacts();
        for(Contact contact : contacts) {
            Log.d("Contacts", contact.toString());
            contactArrayList.add(contact);
        }

        // соединяем адаптер с данными для отображения
        recycleViewAdapter = new RecycleViewAdapter(MainActivity.this, contactArrayList);
        recyclerView.setAdapter(recycleViewAdapter);
    }
}
