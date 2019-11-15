package com.example.roomdemo.db;

/*
DAO : Data Access Object is either be an interface or an abstract class annotated with @Dao
annotation, containing all the methods to define the operations to be performed on data. The
methods can be annotated with

@Query to retrieve data from database
@Insert to insert data into database
@Delete to delete data from database
@Update to update data in database

The DAO acts as a contract to perform CRUD operations on data within a database.

        The result of SQLite queries are composed into cursor object, DAO methods abstract the
        conversion of cursor to Entity objects and vice-versa.

*/
//Below query is used for relationship of tables
/*
@Query("SELECT * FROM user INNER JOIN user_repo_join ON
        user.id=user_repo_join.userId WHERE
        user_repo_join.repoId=:repoId")
        List<User> getUsersForRepository(final int repoId);*/
/*
@Relation(parentColumn = "id",
        entityColumn = "userId") public List<Repo> repoList;*/

//Please refer this link for better understanding
// https://android.jlelse.eu/android-architecture-components-room-relationships-bf473510c14a

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomdemo.model.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note")
    List<Note> getNotesList();

    @Query("SELECT * FROM note WHERE note_author=:author")
    Note getNotesByAuthor(String author);

    @Query("SELECT * FROM note WHERE note_title=:name")
    List<Note> getNotesByTitle(String name);

    @Query("SELECT * FROM note WHERE note_author=:name LIMIT :max")
    List<Note> getReposByName(int max, String... name);

    @Query("SELECT * FROM note WHERE note_status=:status")
    List<Note> getNotesByStatus(String status);

    @Query("UPDATE note SET note_title = :title, note_content = :content, note_author = :author, " +
            "note_status = :status WHERE note_id =:id")
    void update(String title, String content, String author, String status, int id);

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);
}
