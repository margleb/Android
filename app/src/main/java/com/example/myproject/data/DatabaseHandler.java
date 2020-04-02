package com.example.myproject.data;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myproject.R;
import com.example.myproject.model.Contact;
import com.example.myproject.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context ) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    // We create our table
    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL - Structured Query Language
        // create table_name (id, name, phone_number);
        String CREATE_CONTACT_TABLE  = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY," + Util.KEY_NAME + " TEXT, "
                + Util.KEY_PHONE_NUMBER + " TEXT" + ")";
        db.execSQL(CREATE_CONTACT_TABLE); // creating our table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = String.valueOf(R.string.db_drop);
        db.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});
        // Create a table again
        onCreate(db);
    }

    /*
      CRUD = Create, Read, Update, Delete
     */
    //Add Contact
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());
        //Insert to row
        db.insert(Util.TABLE_NAME, null, values);
        Log.d("DBHandler", "addContact: " + "item added");
        db.close(); // closing db connection!;
    }
    //Get a contact
    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{ Util.KEY_ID, Util.KEY_NAME, Util.KEY_PHONE_NUMBER},
                Util.KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();
        }

        Contact contact = new Contact();
        contact.setId(Integer.parseInt(cursor.getString(0)));
        contact.setName(cursor.getString(1));
        contact.setPhoneNumber(cursor.getString(2));

        return contact;
    }

    // Get all Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Select all contacts
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);
        // Loop through out data
        if(cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                // add contact object to our list
                contactList.add(contact);
            } while(cursor.moveToNext());
        }
        return contactList;
    }

}
