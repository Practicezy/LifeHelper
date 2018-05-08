package com.example.r.lifehelper.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.r.lifehelper.bean.Note;
import com.example.r.lifehelper.db.NoteDbSchema.NoteTable.Cols;

import java.util.Date;
import java.util.UUID;

public class NoteCursorWrapper extends CursorWrapper {

    public NoteCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Note getNote(){
        String uuidString = getString(getColumnIndex(Cols.UUID));
        String title = getString(getColumnIndex(Cols.TITLE));
        String content = getString(getColumnIndex(Cols.CONTENT));
        long date = getLong(getColumnIndex(Cols.DATE));

        Note note = new Note(UUID.fromString(uuidString));
        note.setTitle(title);
        note.setContent(content);
        note.setDate(new Date(date));

        return note;
    }
}
