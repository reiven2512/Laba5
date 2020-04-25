package com.example.prak_3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static com.example.prak_3.Names.con.DB_CREATE;
import static com.example.prak_3.Names.con.DES;
import static com.example.prak_3.Names.con.ID;
import static com.example.prak_3.Names.con.NAME;
import static com.example.prak_3.Names.con.NUM;
import static com.example.prak_3.Names.con.TABLE;


public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, "DataBase", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
        ContentValues cv = new ContentValues();
        for (int i = 1; i <= 10; i++) {
            cv.put(NUM, i);
            cv.put(NAME, "Name " + i);
            cv.put(DES, "Description " + i);
            db.insert(TABLE, null, cv);
        }
    }

    public List<Info> getAllInfo(){
        SQLiteDatabase db = getWritableDatabase();
        List<Info> lt = new ArrayList<>();
        Cursor c = db.query(TABLE, null, null, null, null, null,null);
        c.moveToFirst();
        do {
            int quantity = c.getInt(c.getColumnIndex(NUM));
            int id = c.getInt(c.getColumnIndex(ID));
            String name = c.getString(c.getColumnIndex(NAME));
            String description = c.getString(c.getColumnIndex(DES));
            lt.add(new Info(name, description, id, quantity));
        } while (c.moveToNext());
        c.close();
        return lt;
    }

    public List<Info> getInfo(){
        SQLiteDatabase db = getWritableDatabase();
        List<Info> lt = new ArrayList<>();
        Cursor c = db.query(TABLE, null, null, null, null, null,null);
        c.moveToFirst();
        do {
            int quantity = c.getInt(c.getColumnIndex(NUM));
            if(quantity == 0) continue;
            int id = c.getInt(c.getColumnIndex(ID));
            String name = c.getString(c.getColumnIndex(NAME));
            String description = c.getString(c.getColumnIndex(DES));
            lt.add(new Info(name, description, id, quantity));
        } while (c.moveToNext());
        c.close();
        return lt;
    }

    public int insert(int num, String name, String desc) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NUM, num);
        cv.put(NAME, name);
        cv.put(DES, desc);
        db.insert(TABLE, null, cv);
        Cursor c = db.query(TABLE, null, null, null, null, null,null);
        c.moveToLast();
        int id = c.getInt(c.getColumnIndex(ID));
        c.close();
        return id;
    }

    public void update(Info dt) {
        String id = Integer.toString(dt.getId());
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        cv.put(NUM, dt.getQuantity());
        cv.put(NAME, dt.getName());
        cv.put(DES, dt.getDescription());
        db.update(TABLE, cv, "id = ?",
                new String[]{id});
    }

    public void delete(int id){
        SQLiteDatabase db = getWritableDatabase();
        String s = Integer.toString(id);
        db.delete(TABLE, "id = ?", new String[]{s});
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}