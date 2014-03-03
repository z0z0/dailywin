package com.example.dailywin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.dailywin.model.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zorana on 3/2/14.
 */
public class EventDAO {


    private final String TAG = EventDAO.class.getSimpleName();

    // Database fields
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] allColumns = {dbHelper.EVENT_COLUMN_ID,dbHelper.EVENT_COLIMUN_NAILIT_ID,dbHelper.EVENT_COLUMN_CREATED};


    public EventDAO(Context context) {
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

    public Event getEventById(int id) {

        open(true);
        Event event = null;
        Log.d(TAG, "getEventById()");
        Cursor cursor = database.query(dbHelper.TABLE_EVENT, allColumns, dbHelper.NAILIT_COLUMN_ID + " = " + id, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            event = cursorToEvent(cursor);
        }
        close(cursor);
        return event;
    }

    public ArrayList<Event> getAllEvents() {
        open(true);
        Log.d(TAG, "getAllEvents()");
        ArrayList<Event> events = new ArrayList<>();
        Cursor cursor = database.query(dbHelper.TABLE_NAILIT, allColumns, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Event event = cursorToEvent(cursor);
                events.add(event);
                cursor.moveToNext();
            }
        }
        close(cursor);
        return events;
    }

    public ArrayList<Event> saveEventList(List<Event> events) {
        Log.d(TAG, "saveEventList()");
        ArrayList<Event> allEvents = new ArrayList<>();
        if (events != null && events.size() > 0 && !events.isEmpty()) {
            for (Event event : events) {
                Event savedEvent = createEvent(event);
                allEvents.add(savedEvent);
            }
        }
        return allEvents;
    }

    public Event createEvent(Event event) {
        open(false);
        Event newEvent = null;
        ContentValues values = new ContentValues();
//        values.put(dbHelper.EVENT_COLUMN_ID, event.getId());
        values.put(dbHelper.EVENT_COLIMUN_NAILIT_ID, event.getNailitId());
        values.put(dbHelper.EVENT_COLUMN_CREATED, event.getCreated());
        long insertId = database.insert(dbHelper.TABLE_EVENT, null, values);
        Cursor cursor = database.query(dbHelper.TABLE_EVENT, allColumns, dbHelper.EVENT_COLUMN_ID + " = " + insertId, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            newEvent = cursorToEvent(cursor);
        }
        close(cursor);
        return newEvent;
    }

    public Event updateEvent(Event event) {
        open(false);
        Event updatedEvent = null;
        ContentValues values = new ContentValues();
        values.put(dbHelper.EVENT_COLUMN_ID, event.getId());
        values.put(dbHelper.EVENT_COLIMUN_NAILIT_ID, event.getNailitId());
        values.put(dbHelper.EVENT_COLUMN_CREATED, event.getCreated());
        database.update(dbHelper.TABLE_EVENT, values, dbHelper.EVENT_COLUMN_ID + " = " + event.getId(), null);
        Cursor cursor = database.query(dbHelper.TABLE_EVENT, allColumns, dbHelper.EVENT_COLUMN_ID + " = " + event.getId(), null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            updatedEvent = cursorToEvent(cursor);
        }
        close(cursor);
        return updatedEvent;
    }




    private Event cursorToEvent(Cursor cursor) {
        Event event = new Event();
        event.setId(cursor.getInt(0));
        event.setNailitId(cursor.getInt(1));
        event.setCreated(cursor.getString(2));
        return event;
    }
    
    
    
}
