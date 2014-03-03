package com.example.dailywin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dailywin.model.Badge;

import java.util.ArrayList;

/**
 * Created by Uros on 3.3.14..
 */
public class BadgeDAO {

    private final String TAG = BadgeDAO.class.getSimpleName();

    // Database fields
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] allColumns = {dbHelper.BAGDE_COLUMN_ID, dbHelper.BADGE_COLUMN_TYPE_ID, dbHelper.BADGE_COLUMN_NAME, dbHelper.BADGE_COLUMN_TRIGGER, dbHelper.BADGE_COLUMN_ICON_URL, dbHelper.BADGE_COLUMN_TEXT};

    public BadgeDAO(Context context) {
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

    public Badge getBadgeById(int id){
        open(true);
        Badge badge = null;
        Log.d(TAG, "getBadgeById()");
        Cursor cursor = database.query(dbHelper.TABLE_BADGE, allColumns, dbHelper.BAGDE_COLUMN_ID + " = " + id, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            badge = cursorToBadge(cursor);
        }
        close(cursor);
        return badge;
    }

    public ArrayList<Badge> getBadgesByType(int typeId){
        open(true);
        ArrayList<Badge> badgesByType = new ArrayList<>();
        Log.d(TAG, "getBadgesByType()");
        Cursor cursor = database.query(dbHelper.TABLE_BADGE, allColumns, dbHelper.BADGE_COLUMN_TYPE_ID + " = " + typeId, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Badge badgeByType = cursorToBadge(cursor);
                badgesByType.add(badgeByType);
                cursor.moveToNext();
            }
        }
        close(cursor);
        return badgesByType;
    }

    public Badge createBadge(Badge badge) {
        open(false);
        Log.d(TAG, "createBadge()");

        Badge newBadge = null;
        ContentValues values = new ContentValues();

        values.put(dbHelper.BAGDE_COLUMN_ID, badge.getId());
        values.put(dbHelper.BADGE_COLUMN_TYPE_ID, badge.getBadgeTypeId());
        values.put(dbHelper.BADGE_COLUMN_NAME, badge.getName());
        values.put(dbHelper.BADGE_COLUMN_TRIGGER, badge.getTrigger());
        values.put(dbHelper.BADGE_COLUMN_ICON_URL, badge.getIconUrl());
        values.put(dbHelper.BADGE_COLUMN_TEXT, badge.getColumnText());


        long insertId = database.insert(dbHelper.TABLE_BADGE, null, values);
        Cursor cursor = database.query(dbHelper.TABLE_BADGE, allColumns, dbHelper.BAGDE_COLUMN_ID + " = " + insertId, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            newBadge = cursorToBadge(cursor);
        }
        close(cursor);
        return newBadge;
    }

    public Badge updateBadge(Badge badge) {
        open(false);
        Log.d(TAG, "updateBadge()");

        Badge updatedBadge = null;
        ContentValues values = new ContentValues();
        values.put(dbHelper.BAGDE_COLUMN_ID, badge.getId());
        values.put(dbHelper.BADGE_COLUMN_TYPE_ID, badge.getBadgeTypeId());
        values.put(dbHelper.BADGE_COLUMN_NAME, badge.getName());
        values.put(dbHelper.BADGE_COLUMN_TRIGGER, badge.getTrigger());
        values.put(dbHelper.BADGE_COLUMN_ICON_URL, badge.getIconUrl());
        values.put(dbHelper.BADGE_COLUMN_TEXT, badge.getIconUrl());

        database.update(dbHelper.TABLE_BADGE, values, dbHelper.BAGDE_COLUMN_ID + " = " + badge.getId(), null);
        Cursor cursor = database.query(dbHelper.TABLE_BADGE, allColumns, dbHelper.BAGDE_COLUMN_ID + " = " + badge.getId(), null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            updatedBadge = cursorToBadge(cursor);
        }
        close(cursor);
        return updatedBadge;
    }

    private Badge cursorToBadge(Cursor cursor) {
        Badge badge = new Badge();
        badge.setId(cursor.getInt(0));
        badge.setBadgeTypeId(cursor.getInt(1));
        badge.setName(cursor.getString(2));
        badge.setIconUrl(cursor.getString(3));
        badge.setTrigger(cursor.getInt(4));
        badge.setColumnText(cursor.getString(5));
        return badge;
    }

}
