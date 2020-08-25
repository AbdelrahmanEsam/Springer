package com.first.springer.room;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.first.springer.ModelResults;



@Database(entities = ModelResults.class,version = 10,exportSchema = false)
public abstract class ResultsDatabase extends RoomDatabase {

    private static ResultsDatabase instance;

    public abstract ModelDao ModelsDoa();

    public static synchronized ResultsDatabase getInstanced(Context context)
    {
        if (instance == null)
        {

            instance = Room.databaseBuilder(context.getApplicationContext(),ResultsDatabase.class,"ResultsDatabase").addCallback(roomcallback).fallbackToDestructiveMigration().build();

        }

        return instance;
    }

    private static RoomDatabase.Callback roomcallback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };




}
