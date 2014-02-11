package com.example.dailywin.messages;

import com.example.dailywin.utils.DateTimeUtil;

import java.util.Date;

/**
 * Created by zorana on 2/8/14.
 */
public class MessageMan implements MessageStrategyApi {

    private static final String messageTemplate = "Man, %s happening for you %s %s!";
    private String frequency;
    private int importance;
    private Date timing;

    public MessageMan(String frequency, int importance, Date timing) {
        this.frequency = frequency;
        this.importance = importance;
        this.timing = timing;
    }


    @Override
    public String getMessage() {
        return String.format(messageTemplate, getImportance(importance), getFrequency(frequency), getTiming(timing));
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
        } else if (DateTimeUtil.isAfter(timing, "08:00")) {
            return "late at night";
        }
        return null;

    }

}
