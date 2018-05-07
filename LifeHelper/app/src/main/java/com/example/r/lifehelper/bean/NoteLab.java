package com.example.r.lifehelper.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NoteLab {
    private static NoteLab sNoteLab;
    private List<Note> mNotes;
    private Note mNote;

    public static NoteLab getInstance() {
        if (sNoteLab == null){
            sNoteLab = new NoteLab();
        }
        return sNoteLab;
    }

    private NoteLab() {
        mNotes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Note note = new Note();
            note.setTitle("Default #" + i);
            note.setContent("有这样的情况，某人在积雪很深的雪地里穿过，结果他并不是白费力气，" +
                    "另一个人怀着感激之情顺着他的脚印走过去，然后是第三个，第四个……于是，" +
                    "那里已经可以看到一条新的小路。就这样，由于一个人，整整一冬就有了一条冬季的道路。");
            mNotes.add(note);
        }
    }

    public List<Note> getNotes(){
        return mNotes;
    }

    public Note getNote(UUID uuid){
        for (Note note : mNotes) {
            if (note.getId().equals(uuid)){
                mNote = note;
                return note;
            }
        }
        return null;
    }

    public void updateNote(Note note){
        mNote = note;
    }

    public void addNote(Note note){
        mNotes.add(note);
    }
}
