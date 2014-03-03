package com.example.dailywin.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.text.format.DateFormat;

/**
 * Created by zorana on 3/3/14.
 */
public class NailItService extends IntentService {
    public static final String PARAM_IN_MSG = "imsg";
    public static final String PARAM_OUT_MSG = "omsg";

    public NailItService() {
        super("NailItService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String msg = intent.getStringExtra(PARAM_IN_MSG);

        SystemClock
                .sleep(10000); // sleep 10 seconds
        String resultTxt = msg + " "
                + DateFormat.format("MM/dd/yy hh:mm:ss", System.currentTimeMillis());
        System.out.println("@@@@@@@@@@@@@@@@@ " + resultTxt);

    }
}
