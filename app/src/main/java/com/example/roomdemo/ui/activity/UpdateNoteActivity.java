package com.example.roomdemo.ui.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.roomdemo.utils.Constant;
import com.example.roomdemo.db.DatabaseClient;
import com.example.roomdemo.model.Note;
import com.example.roomdemo.R;

public class UpdateNoteActivity extends AppCompatActivity {
    private Note mNoteData;
    private EditText mEtTitle;
    private EditText mEtContent;
    private EditText mEtAuthor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_note);
        getIntentData();
        init();
        setData();
    }

    private void setData() {
        mEtTitle.setText(mNoteData.getTitle());
        mEtContent.setText(mNoteData.getContent());
        mEtAuthor.setText(mNoteData.getAuthor());
        mEtTitle.setSelection(mEtTitle.getText().length());
        mEtContent.setSelection(mEtContent.getText().length());
        mEtAuthor.setSelection(mEtAuthor.getText().length());
    }

    private void init() {
        mEtTitle = findViewById(R.id.et_title_update);
        mEtContent = findViewById(R.id.et_content_update);
        mEtAuthor = findViewById(R.id.et_author_update);
        Button mBtnUpdate = findViewById(R.id.btn_update);
        mBtnUpdate.setOnClickListener(v -> validation());
    }

    private void validation() {
        String title = mEtTitle.getText().toString();
        String content = mEtContent.getText().toString();
        String author = mEtAuthor.getText().toString();

        if (TextUtils.isEmpty(title)) {
            Toast.makeText(UpdateNoteActivity.this, R.string.enter_title, Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(content)) {
            Toast.makeText(UpdateNoteActivity.this, R.string.enter_content, Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(author)) {
            Toast.makeText(UpdateNoteActivity.this, R.string.enter_author, Toast.LENGTH_SHORT).show();

        } else {
            Note note = new Note(title, content, author, Constant.INPROGRESS);
            updateNote(note);
        }
    }

    private void updateNote(Note note) {
        @SuppressLint("StaticFieldLeak")
        class UpdateNote extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(UpdateNoteActivity.this).getNoteDatabase().noteDao()
                        .update(note.getTitle(), note.getContent(), note.getAuthor(),
                                Constant.COMPLETED, mNoteData.getId());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(UpdateNoteActivity.this, R.string.updated, Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        UpdateNote updateNote = new UpdateNote();
        updateNote.execute();
    }

    private void getIntentData() {
        if (getIntent() != null) {
            mNoteData = (Note) getIntent().getSerializableExtra(Constant.NOTE);
        }
    }


}
