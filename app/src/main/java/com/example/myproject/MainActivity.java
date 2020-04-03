package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myproject.data.DatabaseHandler;
import com.example.myproject.model.Contact;
import com.example.myproject.util.Util;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> contactArrayList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_view);
        contactArrayList = new ArrayList<>();

        DatabaseHandler db = new DatabaseHandler(this);

//        db.addContact(new Contact("Boris", "89113335018"));
//        db.addContact(new Contact("Vadim", "89119318038"));
//        db.addContact(new Contact("Valentin", "89111431248"));
//        db.addContact(new Contact("Valeriy", "89115532342"));

         List<Contact> contacts = db.getAllContacts();
         for(Contact contact : contacts) {
             Log.d("Contacts", contact.toString());
             contactArrayList.add(contact.getName());
         }

    }
}
