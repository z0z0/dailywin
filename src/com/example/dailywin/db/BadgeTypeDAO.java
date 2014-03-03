package com.example.dailywin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dailywin.model.BadgeType;

import java.util.ArrayList;

/**
 * Created by Uros on 3.3.14..
 */
public class BadgeTypeDAO {

    private final String TAG = BadgeTypeDAO.class.getSimpleName();

    // Database fields
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] allColumns = {dbHelper.BAGDE_TYPE_COLUMN_ID,dbHelper.BADGE_TYPE_COLUMN_NAME};

    public BadgeTypeDAO(Context context) {
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

    public BadgeType getBadgeTypeById(int id){
        open(true);
        BadgeType badgeType = null;
        Log.d(TAG, "getBadgeTypeById()");
        Cursor cursor = database.query(dbHelper.TABLE_BADGE_TYPE, allColumns, dbHelper.BAGDE_TYPE_COLUMN_ID + " = " + id, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            badgeType = cursorToBadgeType(cursor);
        }
        close(cursor);
        return badgeType;
    }

    public ArrayList<BadgeType> getAllBadgeTypes() {
        open(true);
        Log.d(TAG, "getAllBadgeTypes()");
        ArrayList<BadgeType> badgeTypes = new ArrayList<>();
        Cursor cursor = database.query(dbHelper.TABLE_BADGE_TYPE, allColumns, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                BadgeType badgeType = cursorToBadgeType(cursor);
                badgeTypes.add(badgeType);
                cursor.moveToNext();
            }
        }
        close(cursor);
        return badgeTypes;
    }

    public BadgeType createBadgeType(BadgeType badgeType) {
        open(false);
        Log.d(TAG, "createBadgeType()");

        BadgeType newBadgeType = null;
        ContentValues values = new ContentValues();

        values.put(dbHelper.BAGDE_TYPE_COLUMN_ID, badgeType.getId());
        values.put(dbHelper.BADGE_TYPE_COLUMN_NAME, badgeType.getName());
        long insertId = database.insert(dbHelper.TABLE_BADGE_TYPE, null, values);
        Cursor cursor = database.query(dbHelper.TABLE_BADGE_TYPE, allColumns, dbHelper.BAGDE_TYPE_COLUMN_ID + " = " + insertId, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            newBadgeType = cursorToBadgeType(cursor);
        }
        close(cursor);
        return newBadgeType;
    }

    public BadgeType updateBadgeType(BadgeType badgeType) {
        open(false);
        BadgeType updatedBadgeType = null;
        ContentValues values = new ContentValues();
        values.put(dbHelper.BAGDE_TYPE_COLUMN_ID, badgeType.getId());
        values.put(dbHelper.BADGE_TYPE_COLUMN_NAME, badgeType.getName());
        database.update(dbHelper.TABLE_BADGE_TYPE, values, dbHelper.BAGDE_TYPE_COLUMN_ID + " = " + badgeType.getId(), null);
        Cursor cursor = database.query(dbHelper.TABLE_BADGE_TYPE, allColumns, dbHelper.BAGDE_TYPE_COLUMN_ID + " = " + badgeType.getId(), null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            updatedBadgeType = cursorToBadgeType(cursor);
        }
        close(cursor);
        return updatedBadgeType;
    }

    private BadgeType cursorToBadgeType(Cursor cursor) {
        BadgeType badgeType = new BadgeType();
        badgeType.setId(cursor.getInt(0));
        badgeType.setName(cursor.getString(1));
        return badgeType;
    }


}
