package com.example.r.lifehelper.bean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.r.lifehelper.db.NoteCursorWrapper;
import com.example.r.lifehelper.db.NoteDbHelper;
import com.example.r.lifehelper.db.NoteDbSchema.NoteTable;
import com.example.r.lifehelper.db.NoteDbSchema.NoteTable.Cols;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NoteLab {
    private static NoteLab sNoteLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static NoteLab getInstance(Context context) {
        if (sNoteLab == null){
            sNoteLab = new NoteLab(context);
        }
        return sNoteLab;
    }

    private NoteLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new NoteDbHelper(mContext).getWritableDatabase();
    }

    public List<Note> getNotes(){
        List<Note> notes = new ArrayList<>();

        NoteCursorWrapper cursor = queryNotes(null,null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                notes.add(cursor.getNote());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return notes;
    }

    public Note getNote(UUID uuid){
        NoteCursorWrapper cursor = queryNotes(Cols.UUID + " = ?",new String[]{uuid.toString()});

        try {
            if (cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getNote();
        } finally {
            cursor.close();
        }
    }

    public void updateNote(Note note){
        String uuidString = note.getId().toString();
        ContentValues values = getContentValues(note);

        mDatabase.update(NoteTable.NAME,values,Cols.UUID + " = ?",new String[]{uuidString});
    }

    public void addNote(Note note){
        ContentValues values = getContentValues(note);
        mDatabase.insert(NoteTable.NAME,null,values);
    }

    public void deleteNote(Note note){
        mDatabase.delete(NoteTable.NAME,Cols.UUID + " = ?",new String[]{note.getId().toString()});
    }

    private static ContentValues getContentValues(Note note){
        ContentValues values = new ContentValues();
        values.put(Cols.UUID,note.getId().toString());
        values.put(Cols.TITLE,note.getTitle());
        values.put(Cols.CONTENT,note.getContent());
        values.put(Cols.DATE,note.getDate().getTime());

        return values;
    }

    private NoteCursorWrapper queryNotes(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(NoteTable.NAME,null,whereClause,whereArgs,
                null,null,null);
        return new NoteCursorWrapper(cursor);
    }
}
