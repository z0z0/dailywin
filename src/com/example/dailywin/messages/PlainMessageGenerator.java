package com.example.dailywin.messages;

import com.example.dailywin.utils.DateTimeUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by zorana on 2/8/14.
 */
public class PlainMessageGenerator {

    private static final String messageTemplate1 = "Man, %s happening for you %s %s!";


    public static Map<Integer, String> messageMap = new HashMap<>();
    private static final int MIN_RANGE=1;
    private static final int MAX_RANGE=2;
    private Random random = new Random();

    static {
        messageMap.put(1, "Man, %s happening for you %s %s!");
        messageMap.put(2, "Rocked it today. Your %s activity is admirable!");
    }

    public PlainMessageGenerator() {
    }


    public String getRandomMessage(String frequency, int importance, Date timing) {
        int randomNum = random.nextInt((MAX_RANGE - MIN_RANGE) + 1) + MIN_RANGE;
        return String.format(messageMap.get(randomNum), getImportance(importance), getFrequency(frequency), getTiming(timing));
    }

    private String getFrequency(String frequency) {
        switch (frequency) {
            case "daily":
                return "on daily basis";
            case "weekly":
                return "on weekly basis";
            case "random":
                return "at random";

        }
        return null;
    }

    private String getImportance(int importance) {
        if (importance > 70) {
            return "very important stuff";
        } else if (importance > 35) {
            return "some mildly important things";
        } else if (importance > 0) {
            return "little things";
        } else {
            return null;
        }

    }

    private String getTiming(Date timing) {

        if (DateTimeUtil.isAfter(timing, "18:00")) {
            return "this evening";
        } else if (DateTimeUtil.isAfter(timing, "12:00")) {
            return "this afternoon";
        } else if (DateTimeUtil.isAfter(timing, "09:00")) {
            return "this morning";
        } else if (DateTimeUtil.isAfter(timing, "07:00")) {
            return "this early morning";
        } else if (DateTimeUtil.isAfter(timing, "05:00")) {
            return "crazy early this morning";
        } else if (DateTimeUtil.isAfter(timing, "00:00")) {
            return "late at night";
        }
        return null;

    }

}
