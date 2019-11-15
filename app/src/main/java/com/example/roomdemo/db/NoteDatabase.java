package com.example.roomdemo.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.roomdemo.model.Note;

/*
This class is used to depict entities, version and info about exportSchema
if we have to use multiple entities then we can define like below

@Database(entities = { Repo.class, User.class },
          version = 1)
 */
@Database(entities = {Note.class}, version = 1,exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
}
