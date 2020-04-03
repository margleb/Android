package com.bawp.babyneeds.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
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


}
