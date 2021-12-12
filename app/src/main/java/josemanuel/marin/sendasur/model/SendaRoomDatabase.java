package josemanuel.marin.sendasur.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import android.os.AsyncTask;

@Database(entities = {Senda.class}, version = 2, exportSchema = false)
public abstract class SendaRoomDatabase extends RoomDatabase {
    public abstract SendaDAO sendaDao();
    private static SendaRoomDatabase INSTANCE;

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    public static SendaRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SendaRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SendaRoomDatabase.class, "senda_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final SendaDAO mDao;


        public PopulateDbAsync(SendaRoomDatabase db) {
            mDao = db.sendaDao();
        }

        @Override
        public void onPreExecute() {

        }

        @Override
        public Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        public void onPostExecute(Void unused) {

        }
    }


}
