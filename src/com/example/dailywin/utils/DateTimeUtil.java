package com.example.dailywin.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zorana on 2/8/14.
 */
public class DateTimeUtil {

    public static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    public static final String dateFormat = "yyyy-MM-dd";


    public static String formatDate (Date date,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String getDefaultDateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    public static String getDefaultDateTimeFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
        return sdf.format(date);
    }

    public static boolean isAfter (Date time, String hrs) {
        String[] timing = hrs.split(":");
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(timing[0]));
        cal.set(Calendar.MINUTE,Integer.parseInt(timing[1]));

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(time);

        return cal2.after(cal);
    }
}
