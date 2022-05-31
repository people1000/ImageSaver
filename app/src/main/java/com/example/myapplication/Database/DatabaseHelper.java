package com.example.myapplication.Database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "memory2.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "posts"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IMG = "img";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ COLUMN_IMG + " TEXT);");
        // добавление начальных данных
//        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_IMG + ") VALUES ('');");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
