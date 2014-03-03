package com.example.dailywin.db;

/**
 * Created by zorana on 2/22/14.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.dailywin.model.NailIt;

import java.util.ArrayList;
import java.util.List;


public class NailItDAO {

    private final String TAG = NailItDAO.class.getSimpleName();

    // Database fields
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] allColumns = {dbHelper.NAILIT_COLUMN_ID, dbHelper.NAILIT_COLUMN_NAME, dbHelper.NAILIT_COLUMN_CREATED,
            dbHelper.NAILIT_COLUMN_FREQ, dbHelper.NAILIT_COLUMN_IMP, dbHelper.NAILIT_COLUMN_F_ARH};


    public NailItDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open(boolean readOnly) throws SQLException {
        database = readOnly ? dbHelper.getReadableDatabase() : dbHelper.getWritableDatabase();
    }

    public void close(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
        dbHelper.close();
    }

    public NailIt getNailItById(String id) {

        open(true);
        NailIt nailIt = null;
        Log.d(TAG, "getNailItById()");
        Cursor cursor = database.query(dbHelper.TABLE_NAILIT, allColumns, dbHelper.NAILIT_COLUMN_ID + " = " + id, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            nailIt = cursorToNailIt(cursor);
        }
        close(cursor);
        return nailIt;
    }

    public ArrayList<NailIt> getAllNailIts() {
        open(true);
        Log.d(TAG, "getAllNailIts()");
        ArrayList<NailIt> nailIts = new ArrayList<NailIt>();
        Cursor cursor = database.query(dbHelper.TABLE_NAILIT, allColumns, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                NailIt nailIt = cursorToNailIt(cursor);
                nailIts.add(nailIt);
                cursor.moveToNext();
            }
        }
        close(cursor);
        return nailIts;
    }

    public ArrayList<NailIt> saveNailItList(List<NailIt> nailIts) {
        Log.d(TAG, "saveNailItList()");
        ArrayList<NailIt> allNailIts = new ArrayList<NailIt>();
        if (nailIts != null && nailIts.size() > 0 && !nailIts.isEmpty()) {
            for (NailIt nailIt : nailIts) {
                NailIt savedNailIt = createNailIt(nailIt);
                allNailIts.add(savedNailIt);
            }
        }
        return allNailIts;
    }

    public NailIt createNailIt(NailIt nailIt) {
        open(false);
        NailIt newNailIt = null;
        ContentValues values = new ContentValues();
//      values.put(dbHelper.NAILIT_COLUMN_ID, nailIt.getId());
        values.put(dbHelper.NAILIT_COLUMN_NAME, nailIt.getName());
        values.put(dbHelper.NAILIT_COLUMN_CREATED, nailIt.getCreated());
        values.put(dbHelper.NAILIT_COLUMN_FREQ, nailIt.getFreq());
        values.put(dbHelper.NAILIT_COLUMN_IMP, nailIt.getImportance());
        values.put(dbHelper.NAILIT_COLUMN_F_ARH, nailIt.getfArh());
        long insertId = database.insert(dbHelper.TABLE_NAILIT, null, values);
        Cursor cursor = database.query(dbHelper.TABLE_NAILIT, allColumns, dbHelper.NAILIT_COLUMN_ID + " = " + insertId, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            newNailIt = cursorToNailIt(cursor);
        }
        close(cursor);
        return newNailIt;
    }

    public NailIt updateNailIt(NailIt nailIt) {
        open(false);
        NailIt updatedNailIt = null;
        ContentValues values = new ContentValues();
        values.put(dbHelper.NAILIT_COLUMN_NAME, nailIt.getName());
        values.put(dbHelper.NAILIT_COLUMN_CREATED, nailIt.getCreated());
        values.put(dbHelper.NAILIT_COLUMN_FREQ, nailIt.getFreq());
        values.put(dbHelper.NAILIT_COLUMN_IMP, nailIt.getImportance());
        values.put(dbHelper.NAILIT_COLUMN_F_ARH, nailIt.getfArh());
        database.update(dbHelper.TABLE_NAILIT, values, dbHelper.NAILIT_COLUMN_ID + " = " + nailIt.getId(), null);
        Cursor cursor = database.query(dbHelper.TABLE_NAILIT, allColumns, dbHelper.NAILIT_COLUMN_ID + " = " + nailIt.getId(), null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            updatedNailIt = cursorToNailIt(cursor);
        }
        close(cursor);
        return updatedNailIt;
    }

//selectCheckedRecordsByFreq
    //todo return list
    public List<NailIt> getCheckedNailitsByFrequency(String freq) {
        open(true);
        List <NailIt> allNailIts = new ArrayList<>();
        Cursor cursor = database.rawQuery("select dw.*, "+
                "(select count(e1.id) from Event e1  "+
                " where e1.created >= strftime('%Y-%m-%d 00:00:00','now','localtime') and e1.nailit_id = dw.id) as checked "+
                "from NailIt dw left join Event e on e.nailit_id=dw.id "+
                "where dw.freq='" + freq + "' "+
                "and dw.f_arh = 0 group by dw.id order by dw.id desc"
                , null);
        while (!cursor.isAfterLast()) {
            NailIt nailIt = cursorToNailIt(cursor);
            allNailIts.add(nailIt);
            cursor.moveToNext();
        }


        close(cursor);
        return allNailIts;
    }

// archive event
    public long archiveNailIt(NailIt nailIt){
        ContentValues values = new ContentValues();
        values.put(dbHelper.NAILIT_COLUMN_F_ARH, 1);
        return database.update(dbHelper.TABLE_NAILIT, values, dbHelper.NAILIT_COLUMN_ID + "= " + nailIt.getId() +"", null);
    }

    private NailIt cursorToNailIt(Cursor cursor) {
        NailIt nailIt = new NailIt();
        nailIt.setId(cursor.getInt(0));
        nailIt.setName(cursor.getString(1));
        nailIt.setCreated(cursor.getString(2));
        nailIt.setFreq(cursor.getString(3));
        nailIt.setImportance(cursor.getInt(4));
        nailIt.setfArh(cursor.getInt(5));
        return nailIt;
    }

}
