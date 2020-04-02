package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.myproject.data.DatabaseHandler;
import com.example.myproject.model.Contact;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler db = new DatabaseHandler(MainActivity.this);

        Contact jeremy = new Contact();
        jeremy.setName("Jeremy");
        jeremy.setPhoneNumber("9893213");

        Contact json = new Contact();
        json.setName("Jason");
        json.setPhoneNumber("23213123");

        // db.addContact(json);

        // Get 1 contact
        Contact c = db.getContact(2);
        c.setName("NewJeremy");
        c.setPhoneNumber("2314");
        int updatedRow = db.updateContact(c);
        Log.d("RowId", "onCreate: " + updatedRow);

        List<Contact> contactList = db.getAllContacts();
        for(Contact contact : contactList) {
            Log.d("MainActivity", "onCreate: " + contact.getName());
        }
    }
}
