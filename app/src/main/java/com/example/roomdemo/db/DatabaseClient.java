package com.example.roomdemo.db;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.room.Room;

import com.example.roomdemo.utils.Constant;

/*
We make database client using singleton pattern to avoid memory leak.
 */
public class DatabaseClient {

    @SuppressLint("StaticFieldLeak")
    private static DatabaseClient mDatabaseInstance = null;
    private NoteDatabase mNoteDatabaseFile;

    private DatabaseClient(Context context) {
        mNoteDatabaseFile = Room.databaseBuilder(context,
                NoteDatabase.class, Constant.DB_FILE_NAME).build();
    }

    public static DatabaseClient getInstance(Context context){
        if (mDatabaseInstance == null){
            mDatabaseInstance = new DatabaseClient(context);
        }
        return mDatabaseInstance;
    }

    public NoteDatabase getNoteDatabase(){
        return mNoteDatabaseFile;
    }
}
