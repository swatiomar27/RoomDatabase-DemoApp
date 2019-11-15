package com.example.roomdemo.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

 /*A class annotated with the @Entity annotation is mapped to a table in database.
 Every entity is persisted in its own table and every field in class represents the column name.
 TableName attribute is used to define the name of the table.
 Every entity class must have at-least one Primary Key field, annotated with @PrimaryKey
 Fields in entity class can be annotated with @ColumnInfo(name = “name_of_column”) annotation to
 give specific column names*/

// Database entity is a thing, person, place, unit, object or any item about which the data should
// be captured and stored in the form of properties, workflow and tables.

//In Note class we need id, Title, Content, Author. So we make 4 fields.

//Below commented code snippet shows relationship
/*@Entity(tableName = "user_repo_join",
        primaryKeys = { "userId", "repoId" },
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId"),
                @ForeignKey(entity = Repo.class,
                        parentColumns = "id",
                        childColumns = "repoId")
        })*/

/*tableName parameter for giving our table some specific name
        primaryKeys parameter for having many primary keys — in SQL we can have not only a single
         primary key, but also set of primary keys! It’s called composite primary key and it’s
         used for declaring that every row in our join table should be unique for every pair of
         userId and repoId
        foreignKey parameter is for declaring an array of foreign keys to the other tables. Here
        we say that userId from our join table is the child id for User class and similarly for
        Repo model*/
@Entity(tableName = "note")
public class Note implements Serializable {

    @ColumnInfo(name = "note_id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "note_title")
    private String title;

    @ColumnInfo(name = "note_content")
    private String content;

    @ColumnInfo(name = "note_author")
    private String author;

    @ColumnInfo(name = "note_status")
    private String status;

    public Note(String title, String content, String author, String status) {

        this.title = title;
        this.content = content;
        this.author = author;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @androidx.annotation.NonNull
    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
