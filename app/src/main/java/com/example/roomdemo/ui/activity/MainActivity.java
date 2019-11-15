package com.example.roomdemo.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdemo.utils.Constant;
import com.example.roomdemo.db.DatabaseClient;
import com.example.roomdemo.ui.adapter.MainAdapter;
import com.example.roomdemo.model.Note;
import com.example.roomdemo.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.INoteItemClickListener {
    private MainAdapter mMainAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNotes();

    }

    private void getNotes() {
        class GetNotes extends AsyncTask<Void, Void, List<Note>> {
            @Override
            protected List<Note> doInBackground(Void... voids) {
                return DatabaseClient.getInstance(getApplicationContext()).getNoteDatabase().noteDao().getNotesList();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                if (notes != null && notes.size() > 0) {
                    setRecyclerView(notes);
                } else {
                    Toast.makeText(MainActivity.this, R.string.add_note, Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetNotes getNotes = new GetNotes();
        getNotes.execute();
    }

    private void setRecyclerView(List<Note> notes) {
        RecyclerView rvNote = findViewById(R.id.rv_note);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        rvNote.setLayoutManager(linearLayoutManager);
        mMainAdapter = new MainAdapter(MainActivity.this, notes);
        rvNote.setAdapter(mMainAdapter);
    }

    private void init() {
        findViewById(R.id.floating_button_add).setOnClickListener(v -> onAddClick());
    }

    private void onAddClick() {
        Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
        startActivity(intent);
    }

    @Override
    public void onNoteItemClick(View v, int adapterPosition, List<Note> mNoteList) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setTitle("Update!");
        alertDialogBuilder.setMessage("Are you sure you want to update note?");
        alertDialogBuilder.setPositiveButton("Yes", (dialog, which) -> updateItem(adapterPosition
                , mNoteList, v));
        alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        alertDialogBuilder.show();
    }

    private void updateItem(int adapterPosition, List<Note> mNoteList, View v) {

        Intent intent = new Intent(MainActivity.this, UpdateNoteActivity.class);
        intent.putExtra(Constant.NOTE,mNoteList.get(adapterPosition));
        startActivity(intent);

    }

    @Override
    public void onNoteItemLongClick(View v, int adapterPosition, List<Note> mNoteList) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setTitle("Delete!");
        alertDialogBuilder.setMessage("Are you sure you want to delete note?");
        alertDialogBuilder.setPositiveButton("Yes", (dialog, which) -> deleteItem(adapterPosition
                , mNoteList));
        alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        alertDialogBuilder.show();
    }

    private void deleteItem(int adapterPosition, List<Note> mNoteList) {

        class DeleteItem extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(MainActivity.this).getNoteDatabase().noteDao().delete(mNoteList.get(adapterPosition));
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                mMainAdapter.updateNoteList(adapterPosition);
                mMainAdapter.notifyDataSetChanged();
            }

        }

        DeleteItem deleteItem = new DeleteItem();
        deleteItem.execute();
    }
}
