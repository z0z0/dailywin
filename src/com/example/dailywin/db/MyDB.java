package com.example.dailywin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.dailywin.utils.DateTimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private int consecutive;

    public final static String WIN_TABLE = "DailyWin"; // name of table

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



    public final static String BADGE_TABLE = "Badge"; // name of table

    public final static String BAGDE_ID = "_id";  // id of badge
    public final static String BADGE_NAME = "name"; // name of the badge
    public final static String BADGE_TRIGGER = "trigger";  // number of times checked when the badge gets activated
    public final static String BADGE_TYPE = "badge_type_id"; // id of badge type
    public final static String BADGE_ICON_URL = "icon_url"; // id of badge type
    public final static String BADGE_TEXT = "text"; // id of badge type



    public final static String BADGE_TYPE_TABLE = "BadgeType"; // name of table

    public final static String BAGDE_TYPE_ID = "_id";  // id of badge type
    public final static String BADGE_TYPE_NAME = "name"; // name of the badge type



    public final static String USR_BADGE = "UserBadge"; // name of table

    public final static String USR_BADGE_ID = "_id";  // id
    public final static String USR_BADGE_BADGE_ID = "badge_id"; // badge_id
    public final static String USR_BADGE_TMPSTMP = "timestamp"; // time


    public final static String BR_BAD_MESSAGES_TABLE = "BreakingBadMessages"; // name of table

    public final static String BR_BAD_ID = "_id";  // id
    public final static String BR_BAD_TEXT= "text"; // message text
    public final static String BR_BAD_WMY = "week_month"; // this week, this month
    public final static String BR_BAD_DWR = "d_w_r"; // daily/weekly/random
    public final static String BR_BAD_IMPORTANCE = "importance_range"; // importance range
    public final static String BR_BAD_HOURS = "hours_range"; // AM/PM

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
        values.put(WIN_CAT, "");
        values.put(WIN_CREATED, DateTimeUtil.getDefaultDateTimeFormat(new Date()));
        values.put(WIN_FREQ, freq);
        values.put(WIN_IMP, importance);
        values.put(WIN_F_ARH, false);
        return database.insert(WIN_TABLE, null, values);
    }

    public long updateRecord(Integer activity_id, String name, String category, String freq, Integer importance){
        ContentValues values = new ContentValues();
        values.put(WIN_NAME, name);
        values.put(WIN_FREQ, freq);
        values.put(WIN_IMP, importance);
        return database.update(WIN_TABLE, values, WIN_ID + "= " + activity_id +"", null);
    }

    public long createEvent(Integer dailywinId, String date) {

        ContentValues values = new ContentValues();
        values.put(EVN_WIN, dailywinId);
        values.put(EVN_CREATED, date);
        return database.insert(EVN_TABLE, null, values);
    }

    public long archiveEvent(Integer activityId){
        ContentValues values = new ContentValues();
        values.put(WIN_F_ARH, 1);
        return database.update(WIN_TABLE, values, WIN_ID + "= " + activityId +"", null);
    }

    public String getPlainMessage(Integer id){
        Cursor message = database.rawQuery("select msg from PlainMessage where _id = "+id+"", null);
        if (message != null) {
            message.moveToFirst();
        }
        return message.getString(0);
    }

    public Cursor selectRecords() {
        String[] cols = new String[]{WIN_ID, WIN_NAME, WIN_CAT, WIN_CREATED, WIN_FREQ, WIN_IMP, WIN_F_ARH};
        Cursor mCursor = database.query(true, WIN_TABLE, cols, null
                , null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    @Deprecated
    public Cursor selectRecordsWithCount() {
        Cursor mCursor = database.rawQuery("select t1._id,t1.name,t1.category,t1.created, t1.freq, t1.importance, count(t2._id) as count1 " +
                " from DailyWin t1 left join Event t2 on t2.dailywin_id=t1._id " +
                " where t1.f_arh = 0 " +
                "  group by t1._id ", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor selectRecordsByFreq(String freq) {
        Cursor mCursor = database.rawQuery("select t1._id,t1.name,t1.category,t1.created, t1.freq, t1.importance, count(t2._id) as count1 " +
                "from DailyWin t1 left join Event t2 on t2.dailywin_id=t1._id " +
                "where t1.freq='" + freq + "' and t1.f_arh = 0" +
                " group by t1._id", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    /*
    * gets records by frequency,
    * and also returns if a win (activity)
    * has been checked that day
    *
    * we actually need this only for rendering
     * daily and random wins
     *
     * TODO for weekly, we need to see if win (activity)
     * has been checked within a week
    * */
    public Cursor selectCheckedRecordsByFreq(String freq) {
            Cursor mCursor = database.rawQuery("select dw._id, dw.name, dw.category, dw.created, dw.freq, dw.importance, count(e._id) as count1, "+
                            "(select count(e1._id) from Event e1  "+
                            " where e1.created >= strftime('%Y-%m-%d 00:00:00','now','localtime') and e1.dailywin_id = dw._id) as checked "+
                            "from DailyWin dw left join Event e on e.dailywin_id=dw._id "+
                            "where dw.freq='" + freq + "' "+
                            "and dw.f_arh = 0 group by dw._id order by dw._id desc"
                                    , null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean selectIfRecordChecked(int id) {
        Cursor mCursor = database.rawQuery("select count(e._id) as checked " +
                "from DailyWin dw, Event e " +
                "where e.created >= date('now') " +
                " and dw._id = '"+ id +"' " +
                " and e.dailywin_id = dw._id  " +
                " and  dw.f_arh = 0", null);


        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor.getInt(0)>0;
    }

    public Cursor selectItemsCountByPeriod() {
        Cursor mCursor = database.rawQuery("select dw._id, dw.name, dw.category, dw.created, dw.freq, dw.importance, count(e._id) as win_count "+
                                            "from DailyWin dw left outer join Event e " +
                                            "on e.dailywin_id = dw._id "+
                                            "where dw.f_arh = 0 " +
                                            "group by dw._id " +
                                            "order by win_count desc" , null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    public long consecutive (long dailyWinId) {
        consecutive = 0;

        consecutiveDailyCount(dailyWinId, DateTimeUtil.getDefaultDateTimeFormat(new Date()), 0) ;
        return consecutive;

    }
    private long consecutiveDailyCount (long dailyWinId, String date, long count) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeUtil.dateFormat);

        try {
            cal.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.add(Calendar.DATE, -1);
        String d = sdf.format(cal.getTime());

        if (existsActivityOnDate(dailyWinId,d)) {
            consecutive++;
            consecutiveDailyCount(dailyWinId, d, consecutive);
        }
        return consecutive;
    }

    private boolean existsActivityOnDate (long dailyWinId, String date) {
        Cursor mCursor = database.rawQuery("select count(e._id)" +
                "from Event e " +
                "where e.created >= strftime('%Y-%m-%d 00:00:00','" + date + "','localtime') " +
                "and e.created <= strftime('%Y-%m-%d 23:59:59','" + date + "','localtime') " +
                " and e.dailywin_id = " + dailyWinId + " ", null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor.getInt(0)>0;

    }
}
