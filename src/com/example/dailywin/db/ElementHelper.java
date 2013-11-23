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
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_CREATE = "create table Activity ( _id integer primary key,name text not null,category text not null,created DATETIME not null, freq text not null, importance integer not null);";

    public ElementHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Activity");
        onCreate(db);
    }
}