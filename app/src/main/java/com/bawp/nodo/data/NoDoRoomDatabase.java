package com.bawp.nodo.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabase.Callback;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.bawp.nodo.model.NoDo;

@Database(entities = {NoDo.class}, version = 1)
public abstract class NoDoRoomDatabase extends RoomDatabase {
    // не кишируется и не сохраняется
    private static volatile NoDoRoomDatabase INSTANCE;

    public abstract NoDoDao noDoDao();

    public static NoDoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NoDoRoomDatabase.class) {
                if (INSTANCE == null) {
                    // create our db
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), NoDoRoomDatabase.class, "nodo_database")
                            .addCallback(roomDatabaseCallBack)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomDatabaseCallBack = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final NoDoDao noDoDao;

        public PopulateDbAsync(NoDoRoomDatabase db) {
            noDoDao = db.noDoDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // noDoDao.deleteAll(); // removes all itemds from our tables
            // for testing
            // NoDo noDo = new NoDo("Buy an new Ferriari");
            // noDoDao.insert(noDo);
            // noDo = new NoDo("Buy Big house");
            //noDoDao.insert(noDo);

            return null;
        }
    }
}
