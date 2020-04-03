package com.bawp.babyneeds.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.bawp.babyneeds.model.Item;
import com.bawp.babyneeds.util.Contstants;


public class DatabaseHandler extends SQLiteOpenHelper {
    private Context context;

    public DatabaseHandler(@Nullable Context context) {
        super(context, Contstants.DB_NAME, null, Contstants.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BABY_TABLE = "CREATE TABLE " + Contstants.TABLE_NAME + "("
                + Contstants.KEY_ID + " INTEGER PRIMARY KEY,"
                + Contstants.KEY_BABY_ITEM + " INTEGER,"
                + Contstants.KEY_COLOR + " TEXT,"
                + Contstants.KEY_QTY_NUMBER + " INTEGER,"
                + Contstants.KEY_ITEM_SIZE + " INTEGER,"
                + Contstants.KEY_DATE_NAME + " LONG);";
        db.execSQL(CREATE_BABY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contstants.TABLE_NAME);
        onCreate(db);
    }

    // CRUD operations
    public void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contstants.KEY_BABY_ITEM, item.getItemName());
        values.put(Contstants.KEY_COLOR, item.getItemColor());
        values.put(Contstants.KEY_QTY_NUMBER, item.getItemQuantity());
        values.put(Contstants.KEY_ITEM_SIZE, item.getItemSize());
        values.put(Contstants.KEY_DATE_NAME, java.lang.System.currentTimeMillis()); // timestamp in milliseconds
        // Insert the raw
        db.insert(Contstants.TABLE_NAME, null, values);
        Log.d("DBHandler", "added Item: ");
    }

    public Item getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Contstants.TABLE_NAME,
                new String[]{
                Contstants.KEY_ID,
                Contstants.KEY_BABY_ITEM,
                Contstants.KEY_QTY_NUMBER,
                Contstants.KEY_ITEM_SIZE,
                Contstants.KEY_DATE_NAME},
                Contstants.KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();

        Item item = new Item();
        if(cursor != null) {
            item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Contstants.KEY_ID))));
            item.setItemName(cursor.getString(cursor.getColumnIndex(Contstants.KEY_BABY_ITEM)));
            item.setItemQuantity(cursor.getInt(cursor.getColumnIndex(Contstants.KEY_QTY_NUMBER)));
            item.setItemSize(cursor.getInt(cursor.getColumnIndex(Contstants.KEY_ITEM_SIZE)));
            // convert Timestamp to something readeble
            
        }
        return null;
    }

}
