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

    public final static String EMP_TABLE = "Activity"; // name of table

    public final static String EMP_ID = "_id"; // id value for employee
    public final static String EMP_NAME = "name";  // name of employee
    public final static String EMP_CAT = "category";  // name of employee
    public final static String EMP_CREATED = "created";  // name of employee
    public final static String EMP_FREQ = "freq";  // name of employee
    public final static String EMP_IMP = "importance";  // name of employee

    public final static String EVN_TABLE = "Event"; // name of table
    public final static String EVN_ID = "_id"; // id value for employee
    public final static String EVN_CREATED = "created";  // name of employee
    public final static String EVN_ACTIVITY = "activity_id";  // name of employee

    /**
     * @param context
     */
    public MyDB(Context context) {
        dbHelper = new ElementHelper(context);
        database = dbHelper.getWritableDatabase();
    }


    public long createRecord(String name, String category, String freq, Integer importance) {
        ContentValues values = new ContentValues();
        values.put(EMP_NAME, name);
        values.put(EMP_CAT, category);
        values.put(EMP_CREATED, new Date().toString());
        values.put(EMP_FREQ, freq);
        values.put(EMP_IMP, importance);
        return database.insert(EMP_TABLE, null, values);
    }

    public long createEvent(Integer activityId) {
        ContentValues values = new ContentValues();
        values.put(EVN_ACTIVITY, activityId);
        values.put(EVN_CREATED, new Date().toString());
        return database.insert(EVN_TABLE, null, values);
    }

    public Cursor selectRecords() {
        String[] cols = new String[]{EMP_ID, EMP_NAME, EMP_CAT, EMP_CREATED, EMP_FREQ, EMP_IMP};
        Cursor mCursor = database.query(true, EMP_TABLE, cols, null
                , null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor selectRecordsWithCount() {
        Cursor mCursor = database.rawQuery("select t1._id,t1.name,t1.category,t1.created, t1.freq, t1.importance, count(t2._id) as count1 from Activity t1 left join Event t2 on t2.activity_id=t1._id group by t1._id ", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor selectRecordsByFreq(String freq) {
        Cursor mCursor = database.rawQuery("select t1._id,t1.name,t1.category,t1.created, t1.freq, t1.importance, count(t2._id) as count1 from Activity t1 left join Event t2 on t2.activity_id=t1._id where t1.freq='" + freq + "' group by t1._id", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

}
