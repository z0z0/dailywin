package com.example.dailywin.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * User: gimlet
 * Date: 11/18/13
 * Time: 10:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class ElementHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DBName";
    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_CREATE_TABLE1 = "create table DailyWin ( _id integer primary key, name text not null, category text, created DATETIME not null, freq text not null, importance integer not null, f_arh int not null);";
    private static final String DATABASE_CREATE_TABLE2 = "create table Event ( _id integer primary key,created DATETIME not null, dailywin_id integer not null);";

    public ElementHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_TABLE1);
        db.execSQL(DATABASE_CREATE_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS DailyWin");
        db.execSQL("DROP TABLE IF EXISTS Event");
        onCreate(db);
    }
}