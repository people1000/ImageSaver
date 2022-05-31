package com.example.myapplication.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;


import com.example.myapplication.Img;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context){
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_IMG};
        return  database.query(DatabaseHelper.TABLE, columns, null, null, null, null, null);
    }

    public List<Img> getPosts(){
        ArrayList<Img> posts = new ArrayList<>();
        Cursor cursor = getAllEntries();
        while (cursor.moveToNext()){
            @SuppressLint("Range")long id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            @SuppressLint("Range")String img = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMG));
            posts.add(new Img(id, img));
        }
        cursor.close();
        return posts;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DatabaseHelper.TABLE);
    }

    public Img getPost(long id){
        Img post = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            @SuppressLint("Range") String img = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMG));
            post = new Img(id, img);
        }
        cursor.close();
        return post;
    }

    public long insert(Img post){

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_IMG, post.getUrl());

        return  database.insert(DatabaseHelper.TABLE, null, cv);
    }

    public long delete(long postId){

        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(postId)};
        return database.delete(DatabaseHelper.TABLE, whereClause, whereArgs);
    }

    public long update(Img post){

        String whereClause = DatabaseHelper.COLUMN_ID + "=" + post.getId();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_IMG, post.getUrl());
        return database.update(DatabaseHelper.TABLE, cv, whereClause, null);
    }
}
