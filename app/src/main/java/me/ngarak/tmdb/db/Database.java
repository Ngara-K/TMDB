package me.ngarak.tmdb.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import me.ngarak.tmdb.model.Movie;

@androidx.room.Database (entities = Movie.class, version = 1)
public abstract class Database extends RoomDatabase {

    public abstract MoviesDAO moviesDAO ();

    public static Database instance;

    public static synchronized Database getInstance (Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), Database.class, "TMDB").addCallback(callback).allowMainThreadQueries().fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate (@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
