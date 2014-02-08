package com.example.dailywin.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zorana on 2/8/14.
 */
public class DateTimeUtil {

    public static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

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
