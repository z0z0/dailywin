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

    public final static String EMP_TABLE="Activity"; // name of table

    public final static String EMP_ID="_id"; // id value for employee
    public final static String EMP_NAME="name";  // name of employee
    public final static String EMP_CAT="category";  // name of employee
    public final static String EMP_CREATED="created";  // name of employee
    public final static String EMP_FREQ="freq";  // name of employee
    public final static String EMP_IMP="importance";  // name of employee
    /**
     *
     * @param context
     */
    public MyDB(Context context){
        dbHelper = new ElementHelper(context);
        database = dbHelper.getWritableDatabase();
    }


    public long createRecord(String name, String category, String freq, Integer importance){
        ContentValues values = new ContentValues();
        values.put(EMP_NAME, name);
        values.put(EMP_CAT, category);
        values.put(EMP_CREATED, new Date().toString());
        values.put(EMP_FREQ, freq);
        values.put(EMP_IMP, importance);
        return database.insert(EMP_TABLE, null, values);
    }

    public Cursor selectRecords() {
        String[] cols = new String[] {EMP_ID, EMP_NAME, EMP_CAT, EMP_CREATED, EMP_FREQ, EMP_IMP};
        Cursor mCursor = database.query(true, EMP_TABLE,cols,null
                , null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();


        }
        return mCursor;
    }
}
