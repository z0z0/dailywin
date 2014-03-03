package com.example.dailywin.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * User: zorana
 * Date: 11/18/13
 * Time: 10:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class DBHelper extends SQLiteOpenHelper {

    public final static String TABLE_NAILIT = "NailIt"; // name of table

    public final static String NAILIT_COLUMN_ID = "id"; // id value for daily win
    public final static String NAILIT_COLUMN_NAME = "name";  // name of daily win
    public final static String NAILIT_COLUMN_CREATED = "created";  // date created
    public final static String NAILIT_COLUMN_FREQ = "freq";  // frequency : daily/weekly/random
    public final static String NAILIT_COLUMN_IMP = "importance";  // importance 0-100
    public final static String NAILIT_COLUMN_F_ARH = "f_arh"; //


    public final static String TABLE_EVENT = "Event"; // name of table

    public final static String EVENT_COLUMN_ID = "id"; // id value
    public final static String EVENT_COLIMUN_NAILIT_ID = "nailit_id";  // id of nail it record
    public final static String EVENT_COLUMN_CREATED = "created";  // timestamp when daily win is checked



    public final static String TABLE_BADGE_TYPE = "BadgeType"; // name of table

    public final static String BAGDE_TYPE_COLUMN_ID = "id";  // id of badge type
    public final static String BADGE_TYPE_COLUMN_NAME = "name"; // name of the badge type



    public final static String TABLE_BADGE = "Badge"; // name of table

    public final static String BAGDE_COLUMN_ID = "id";  // id of badge
    public final static String BADGE_COLUMN_TYPE_ID = "badge_type_id"; // id of badge type
    public final static String BADGE_COLUMN_NAME = "name"; // name of the badge
    public final static String BADGE_COLUMN_TRIGGER = "trigger";  // number of times checked when the badge gets activated
    public final static String BADGE_COLUMN_ICON_URL = "icon_url"; // id of badge type
    public final static String BADGE_TCOLUMN_EXT = "text"; // id of badge type


    public final static String TABLE_USER_BADGE = "UserBadge"; // name of table

    public final static String USER_BADGE_COLUMN_ID = "id";  // id
    public final static String USER_BADGE_COLUMN_BADGE_ID = "badge_id"; // badge_id
    public final static String USER_BADGE_COLUMN_TMPSTMP = "timestamp"; // time


    private static final String DATABASE_NAME = "DBName";
    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_CREATE_TABLE1 = "create table NailIt ( id integer primary key, name text not null, created DATETIME not null, freq text not null, importance integer not null, f_arh int not null);";
    private static final String DATABASE_CREATE_TABLE2 = "create table Event ( id integer primary key,created DATETIME not null, nailit_id integer not null);";
    private static final String DATABASE_CREATE_TABLE3 = "create table Badge ( id integer primary key, name text not null, trigger integer not null, badge_type_id integer not null, icon_url text not null, text text not null );";
    private static final String DATABASE_CREATE_TABLE4 = "create table BadgeType ( id integer primary key, name text not null);";
    private static final String DATABASE_CREATE_TABLE5 = "create table UserBadge ( id integer primary key, badge_id integer not null, timestamp DATETIME not null);";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_TABLE1);
        db.execSQL(DATABASE_CREATE_TABLE2);
        db.execSQL(DATABASE_CREATE_TABLE3);
        db.execSQL(DATABASE_CREATE_TABLE4);
        db.execSQL(DATABASE_CREATE_TABLE5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS NailIt");
        db.execSQL("DROP TABLE IF EXISTS Event");
        db.execSQL("DROP TABLE IF EXISTS Badge");
        db.execSQL("DROP TABLE IF EXISTS BadgeType");
        db.execSQL("DROP TABLE IF EXISTS UserBadge");
        db.execSQL("DROP TABLE IF EXISTS PlainMessage");

        onCreate(db);
    }
}