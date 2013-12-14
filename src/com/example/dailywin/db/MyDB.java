package com.example.dailywin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: gimlet
 * Date: 11/18/13
 * Time: 10:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyDB {
    private ElementHelper dbHelper;

    private SQLiteDatabase database;

    public final static String WIN = "DailyWin"; // name of table

    public final static String WIN_ID = "_id"; // id value for daily win
    public final static String WIN_NAME = "name";  // name of daily win
    public final static String WIN_CAT = "category";  // activity category which we wont use for now
    public final static String WIN_CREATED = "created";  // date created
    public final static String WIN_FREQ = "freq";  // frequency : daily/weekly/random
    public final static String WIN_IMP = "importance";  // importance 0-100
    public final static String WIN_F_ARH = "f_arh"; //

    public final static String EVN_TABLE = "Event"; // name of table
    public final static String EVN_WIN = "dailywin_id";  // id of daily win
    public final static String EVN_ID = "_id"; // id value
    public final static String EVN_CREATED = "created";  // timestamp when daily win is checked



    /**
     * @param context
     */
    public MyDB(Context context) {
        dbHelper = new ElementHelper(context);
        database = dbHelper.getWritableDatabase();
    }


    public long createRecord(String name, String category, String freq, Integer importance) {
        ContentValues values = new ContentValues();
        values.put(WIN_NAME, name);
        values.put(WIN_CAT, category);
        values.put(WIN_CREATED, new Date().toString());
        values.put(WIN_FREQ, freq);
        values.put(WIN_IMP, importance);
        return database.insert(WIN, null, values);
    }

    public long createEvent(Integer activityId) {
        ContentValues values = new ContentValues();
        values.put(EVN_WIN, activityId);
        values.put(EVN_CREATED, new Date().toString());
        return database.insert(EVN_TABLE, null, values);
    }

    public Cursor selectRecords() {
        String[] cols = new String[]{WIN_ID, WIN_NAME, WIN_CAT, WIN_CREATED, WIN_FREQ, WIN_IMP, WIN_F_ARH};
        Cursor mCursor = database.query(true, WIN, cols, null
                , null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor selectRecordsWithCount() {
        Cursor mCursor = database.rawQuery("select t1._id,t1.name,t1.category,t1.created, t1.freq, t1.importance, count(t2._id) as count1" +
                                          " from DailyWin t1 left join Event t2 on t2.dailywin_id=t1._id " +
                                          " where t1.f_arh = false " +
                                          "  group by t1._id ", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor selectRecordsByFreq(String freq) {
        Cursor mCursor = database.rawQuery("select t1._id,t1.name,t1.category,t1.created, t1.freq, t1.importance, count(t2._id) as count1 " +
                "from DailyWin t1 left join Event t2 on t2.activity_id=t1._id " +
                "where t1.freq='" + freq + "' group by t1._id " +
                "and t1.f_arh = false", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

}
