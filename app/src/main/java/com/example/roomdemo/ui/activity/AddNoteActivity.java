package com.example.roomdemo.ui.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roomdemo.utils.Constant;
import com.example.roomdemo.db.DatabaseClient;
import com.example.roomdemo.model.Note;
import com.example.roomdemo.R;

public class AddNoteActivity extends AppCompatActivity {

    private EditText mEtTitle;
    private EditText mEtContent;
    private EditText mEtAuthor;
    private Button mBtnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        init();
        initListeners();
    }

    private void initListeners() {
        mBtnSave.setOnClickListener(v -> validation());
    }

    private void validation() {
        String title = mEtTitle.getText().toString();
        String content = mEtContent.getText().toString();
        String author = mEtAuthor.getText().toString();

        if (TextUtils.isEmpty(title)) {
            Toast.makeText(AddNoteActivity.this, R.string.enter_title, Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(content)) {
            Toast.makeText(AddNoteActivity.this, R.string.enter_content, Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(author)) {
            Toast.makeText(AddNoteActivity.this, R.string.enter_author, Toast.LENGTH_SHORT).show();

        } else {
            Note note = new Note(title, content, author, Constant.INPROGRESS);
            InsertNote(note);
        }
    }

    private void InsertNote(Note note) {

        @SuppressLint("StaticFieldLeak")
        class InsertNote extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(AddNoteActivity.this).getNoteDatabase().noteDao().insert(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(AddNoteActivity.this, R.string.saved, Toast.LENGTH_SHORT).show();
                finish();

            }
        }

        InsertNote insertNote = new InsertNote();
        insertNote.execute();
    }

    private void init() {

        mEtTitle = findViewById(R.id.et_title);
        mEtContent = findViewById(R.id.et_content);
        mEtAuthor = findViewById(R.id.et_author);
        mBtnSave = findViewById(R.id.btn_save);
    }
}
