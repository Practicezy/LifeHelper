package com.example.r.lifehelper.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.r.lifehelper.bean.Note;
import com.example.r.lifehelper.db.NoteDbSchema.NoteTable;
import com.example.r.lifehelper.db.NoteDbSchema.NoteTable.Cols;

public class NoteDbHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "noteDB.db";

    public NoteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+ NoteTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                Cols.UUID + ","+
                Cols.TITLE + ","+
                Cols.CONTENT + ","+
                Cols.DATE + ")");
        initNote(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void initNote(SQLiteDatabase database) {
        Note note1 = new Note();
        note1.setTitle("无名赞歌");
        note1.setContent("In the Age of Ancients,Theworld was unformed,shrouded by fog.A land of grey crags,archtrees,and everlasting dragons.\n" +
                "But then there was Fire.And with Fire came,Disparity.Heat and cold,life and death,and of course...Light and Dark.\n" +
                "Then,from the Dark,They came,and found the Souls of Lords within the flame.\n" +
                "Nito,the First of the Dead.The Witch of Izalith,and herdaughters of Chaos.Gwyn,the Lord of Sunlight,and his faithful knights.\n" +
                "And the furtive pygmy,so easily forgotten.With the Strength of Lords,they challenged the dragons.Gwyn's mighty bolts peeled apart their stone scales.\n" +
                "The witches weaved great firestorms.Nito unleashed a miasma of death and disease.And Seath the Scaleless betrayed his own,and the dragons were no more.\n" +
                "Thus began the Age of Fire...\n" +
                "But soon,the flames will fade,and only Dark will remain.\n" +
                "Even now,there are only embers,and man sees not light,but only endless nights.\n" +
                "And amongst the living are seen,carriers ofthe accursed Darksign.\n" +
                "Yes, indeed. The Darksign brands the Undead.\n" +
                "And in this land,\n" +
                "the Undead are corralled and led to the north,\n" +
                "where they are locked away, to await the end of the world.\n" +
                "...This is your fate.\n" +
                "Only, in the ancient legends it is stated,that one day an Undead shall be chosen to leave the Undead Asylum, in pilgrimage,to the land of the Ancient Lords,Lordran.");
        ContentValues values1 = getContentValues(note1);
        database.insert(NoteTable.NAME,null,values1);

        Note note2 = new Note();
        note2.setTitle("太阳战士-索拉尔");
        note2.setContent("Why,why?After all this searching,I still cannot find it..\n" +
                "Was it all a lie? Have I done this all, for nothing? Oh, my dear sun... \n" +
                "What now, what should I do...? ...My sun, my dear, dear sun...\n" +
                "喜欢一位网友的评论：\n" +
                "但他没有意识到的是，这一路走来，索拉尔那太阳骑士的英姿早已深深烙在人们心中，" +
                "他一路前行，一路散播温暖，这不就是他苦苦追寻的太阳吗？\n" +
                "索哥也许是太专注于追寻太阳，却没有意识到，他的一举一动：\n" +
                "从交给我们白蜡石的那一刻起，他就在不停地散发着光芒，" +
                "他俨然已经成为了人们心中那璀璨的太阳。\n"+
                "你可能永远也不理解这句话：\n"+"赞美太阳!");
        ContentValues values2 = getContentValues(note2);
        database.insert(NoteTable.NAME,null,values2);

        Note note3 = new Note();
        note3.setTitle("克拉娜老师、洋葱、A大及其他");
        note3.setContent("可别死了啊，我的笨徒弟 ——克拉娜老师 \n"+
                "你还真了不起呢，有时候我在想，是不是也该稍微努力一下呢 ——灰心哥\n"+
                "欧，父亲，我亲爱的父亲,他再也不能给别人添麻烦了…… ——洋葱妹\n"+
                "朋友，我有一个好主意，真的，我去引开那些怪物，你就可以趁乱逃走 --洋葱哥\n"+
                "不管你是何方神圣，奉劝最好别靠过来……\n 我就快要被黑暗……侵蚀掉……\n拜托你……阻止深…渊" +
                "…… --亚尔特留斯\n"+"");
        ContentValues values3 = getContentValues(note3);
        database.insert(NoteTable.NAME,null,values3);
    }

    private static ContentValues getContentValues(Note note){
        ContentValues values = new ContentValues();
        values.put(Cols.UUID,note.getId().toString());
        values.put(Cols.TITLE,note.getTitle());
        values.put(Cols.CONTENT,note.getContent());
        values.put(Cols.DATE,note.getDate().getTime());

        return values;
    }
}
