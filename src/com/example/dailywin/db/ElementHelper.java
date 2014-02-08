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
    private static final String DATABASE_CREATE_TABLE6 = "create table PlainMessage ( _id integer primary key, msg text not null);";


    private static final String TABLE6_INITIAL_INSERT = "INSERT INTO PlainMessage " +
            " SELECT 1 as _id, 'You nailed it!' as msg " +
            " UNION SELECT 2, 'Rocked it today!' " +
            " UNION SELECT 3, 'You nailed it!' " +
            " UNION SELECT 4, 'Rocked it today!' " +
            " UNION SELECT 5, 'Wow. Impressive.' " +
            " UNION SELECT 6, 'Little things matter!' " +
            " UNION SELECT 7, 'Nicely done!' " +
            " UNION SELECT 8, 'Score!' " +
            " UNION SELECT 9, 'Holla!' " +
            " UNION SELECT 10, 'Woop dee do!' " +
            " UNION SELECT 11, 'out of the park! Homerun.' " +
            " UNION SELECT 12, 'Shazaaaam' " +
            " UNION SELECT 13, 'Fancy shmancy' " +
            " UNION SELECT 14, 'You nailed it!' " +
            " UNION SELECT 15, 'You put lesser beings to shame' " +
            " UNION SELECT 16, 'Pat yourself on the back for this one.' " +
            " UNION SELECT 17, 'There are no words to adequately praise this accomplishment.' " +
            " UNION SELECT 18, 'Giddy Up!' " +
            " UNION SELECT 19, 'Hells to the yes!' " +
            " UNION SELECT 20, 'True story.' " +
            " UNION SELECT 21, 'Challenge accepted.' " +
            " UNION SELECT 22, 'Freeeeedom!' " +
            " UNION SELECT 23, 'Here''s looking at you, kid.' " +
            " UNION SELECT 24, 'May the Force be with you.' " +
            " UNION SELECT 25, 'You talking to me?' " +
            " UNION SELECT 26, 'Made it, Ma! Top of the world!' " +
            " UNION SELECT 27, 'I am big! It''s the pictures that got small.' " +
            " UNION SELECT 28, 'Round up the usual suspects.' " +
            " UNION SELECT 29, 'You''re back!' " +
            " UNION SELECT 30, 'It''s alive! It''s alive!' " +
            " UNION SELECT 31, 'You brave little soldier! You''re too precious for this world!' " +
            " UNION SELECT 32, 'What are the odds?' " +
            " UNION SELECT 33, 'Live long and prosper.' " +
            " UNION SELECT 34, 'Aces high!' " +
            " UNION SELECT 35, 'Your mother would be so proud!' ";

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
        db.execSQL(TABLE6_INITIAL_INSERT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS DailyWin");
        db.execSQL("DROP TABLE IF EXISTS Event");
        db.execSQL("DROP TABLE IF EXISTS Badge");
        db.execSQL("DROP TABLE IF EXISTS BadgeType");
        db.execSQL("DROP TABLE IF EXISTS UserBadge");
        db.execSQL("DROP TABLE IF EXISTS PlainMessage");

        onCreate(db);
    }
}