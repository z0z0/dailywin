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
    private static final String DATABASE_CREATE_TABLE3 = "create table Badge ( _id integer primary key, name text not null, trigger integer not null, badge_type_id integer not null, icon_url text not null, text text not null );";
    private static final String DATABASE_CREATE_TABLE4 = "create table BadgeType ( _id integer primary key, name text not null);";
    private static final String DATABASE_CREATE_TABLE5 = "create table UserBadge ( _id integer primary key, badge_id integer not null, timestamp DATETIME not null);";
    private static final String DATABASE_CREATE_TABLE6 = "create table PlainMessage ( _id integer primary key, text text not null;";


    private static final String TABLE6_INITIAL_INSERT = "INSERT INTO plain_message (text) VALUES " +
            " ('You nailed it!'), " +
            " ('Rocked it today!'), " +
            " ('Wow. Impressive.'), " +
            " ('Little things matter!'), " +
            " ('Nicely done!'), " +
            " ('Score!'), " +
            " ('Holla!'), " +
            " ('Woop dee do!'), " +
            " ('sharp as a witch\"s boob / beek'), " +
            " ('out of the park! Homerun.'), " +
            " ('Shazaaaam'), " +
            " ('Fancy shmancy'), " +
            " ('You nailed it!'), " +
            " ('You put lesser beings to shame'), " +
            " ('Pat yourself on the back for this one.'), " +
            " ('There are no words to adequately praise this accomplishment.'), " +
            " ('Giddy Up!'), " +
            " ('Hells to the yes!'), " +
            " ('True story.'), " +
            " ('Challenge accepted.'), " +
            " ('Freeeeedom!'), " +
            " ('Here\\'s looking at you, kid.'), " +
            " ('May the Force be with you.'), " +
            " ('You talking to me?'), " +
            " ('Made it, Ma! Top of the world!'), " +
            " ('I am big! It\\'s the pictures that got small.'), " +
            " ('Round up the usual suspects.'), " +
            " ('You\\'re back!'), " +
            " ('It's alive! It's alive!'), " +
         //   " ('As God is my witness, I\\'ll never be hungry again.'), " +
            " ('You brave little soldier! You\\'re too precious for this world!'), " +
            " ('What are the odds?'), " +
            " ('Live long and prosper.'), " +
            " ('Aces high!'), " +
            " ('You mother would be so proud!');";

    public ElementHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_TABLE1);
        db.execSQL(DATABASE_CREATE_TABLE2);
        db.execSQL(DATABASE_CREATE_TABLE3);
        db.execSQL(DATABASE_CREATE_TABLE4);
        db.execSQL(DATABASE_CREATE_TABLE5);
        db.execSQL(DATABASE_CREATE_TABLE6);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS DailyWin");
        db.execSQL("DROP TABLE IF EXISTS Event");
        db.execSQL("DROP TABLE IF EXISTS Badge");
        db.execSQL("DROP TABLE IF EXISTS BadgeType");
        db.execSQL("DROP TABLE IF EXISTS UserBadge");

        onCreate(db);
    }
}