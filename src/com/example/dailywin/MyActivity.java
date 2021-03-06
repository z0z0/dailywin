package com.example.dailywin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.example.dailywin.adapters.ListViewAdapter;
import com.example.dailywin.db.MyDB;
import com.example.dailywin.gestures.SwipeDetector;
import com.example.dailywin.messages.PlainMessageGenerator;
import com.example.dailywin.utils.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MyActivity extends Activity {

    private Button addActivityButton;
    private ImageView profileImageView;
    Random random = new Random();
    private static final int MIN_RANGE = 1;
    private static final int MAX_RANGE = 35;

    /**
     * Called when the activity is first created.
     */
    private static MyActivity self;
    private MyDB db;
    private ListView listView;
    private SimpleCursorAdapter adapter;
    private TextView dailyButton;
    private TextView weeklyButton;
    private TextView randomButton;
    String frequency = "daily";
    private int randomNum;
    private long consecutiveCount;
    private PlainMessageGenerator message = new PlainMessageGenerator();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        if (getIntent() != null && getIntent().getExtras() != null) {
            frequency = getIntent().getStringExtra("freq_filter");
        }

        super.onCreate(savedInstanceState);
        self = this;
        setContentView(R.layout.main);
        addActivityButton = (Button) findViewById(R.id.addActivity);

        int resourceId = getResources().getIdentifier(frequency + "Button", "id", getPackageName());
        TextView btn = (TextView) findViewById(resourceId);
        btn.setBackgroundColor(0xFFEF3E36);
        btn.setTextColor(0xFFFFFFFF);

        addActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(self, AddNewWinActivity.class);
                intent.putExtra("f_freq", frequency);
                startActivity(intent);
            }
        });

        profileImageView = (ImageView) findViewById(R.id.profileView);
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(self, Profile.class);
                startActivity(intent);
            }
        });

        db = new MyDB(this);
        Cursor cursor = db.selectCheckedRecordsByFreq(frequency);

        listView = (ListView) findViewById(R.id.listView);

        final SwipeDetector swipeDetector = new SwipeDetector();

       // listView.setOnTouchListener(swipeDetector);
        adapter = new ListViewAdapter(this, R.layout.list_item, cursor, new String[]{MyDB.WIN_NAME, "checked"}, new int[]{R.id.listItemLabel, R.id.itemChecked}, CursorAdapter.FLAG_AUTO_REQUERY);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//
//                if (swipeDetector.swipeDetected()) {
//
//                    if (swipeDetector.getAction() == SwipeDetector.Action.LR) {
//
//                        //this counts how many activities were checked that day, and if it's more than once,
//                        // we do wont let them check it again
//                        // the pic should also be different
//
//                        Cursor c = adapter.getCursor();
//                        String freq = c.getString(4);
//                        c.moveToPosition(position);
//                        int dailyWinId = c.getInt(0);
//
//                        //calculate consecutive days
//
//
//                        Dialog nailedIt = new Dialog(self, R.style.alertDialog);
//
//                        if (adapter.getCursor().getInt(7) == 0) {
//                            Date date = new Date();
//                            String d = DateTimeUtil.getDefaultDateTimeFormat(new Date());
//
//                            db.createEvent(dailyWinId,d);
//
//                            consecutiveCount = db.consecutive(dailyWinId, freq);
//                          //  System.out.println("##################################################consecutive count " + consecutiveCount);
//                          //  Log.i("BOOYAKASHA", "##################################################consecutive count " + consecutiveCount);
//                            adapter.swapCursor(db.selectCheckedRecordsByFreq(freq));
//
//                            String m =createNonBadgeMessage(adapter.getCursor().getString(4),adapter.getCursor().getInt(5),date);
//                            nailedIt.setTitle(m);
//                        }
//                        else{
//                            nailedIt.setTitle("Znachi ovo je vec cekirano");
//                        }
//
//                        nailedIt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                        nailedIt.setContentView(R.layout.nail_dialog);
//                        nailedIt.setCancelable(true);
//                        nailedIt.setCanceledOnTouchOutside(true);
//
//                        TextView poruka = (TextView) nailedIt.findViewById(R.id.plainMessage);
//                        poruka.setText(getMessage(MIN_RANGE, MAX_RANGE));
//
//                        ImageView badge = (ImageView) nailedIt.findViewById(R.id.badgeGlobal);
//                        ImageView badge2 = (ImageView) nailedIt.findViewById(R.id.badgeConsecutive);
//                        badge.setImageResource(R.drawable.yogaicon);
//                        badge2.setImageResource(R.drawable.checked);
//
//                        nailedIt.show();
//                    }
//
//                    if (swipeDetector.getAction() == SwipeDetector.Action.RL) {
//                        View Delete = listView.getChildAt(position);
//
//                        TextView deletion = (TextView) Delete.findViewById(R.id.itemChecked);
//                        if (deletion != null){
//                            if (deletion.getVisibility() == View.INVISIBLE){
//                                deletion.setVisibility(View.VISIBLE);
//                                deletion.animate().translationX(-100).setDuration(500).setStartDelay(3000).translationX(500);
//
//
//                            }
//                            else{
//                                deletion.animate().translationX(100).setDuration(500);
//                                deletion.setVisibility(View.INVISIBLE);
//
//                            }
//                        }
//
//
////                        new AlertDialog.Builder(self).setMessage("Are you serial?")
////                                .setPositiveButton("O yea", new DialogInterface.OnClickListener() {
////                                    public void onClick(DialogInterface dialog, int which) {
////                                        Cursor c = adapter.getCursor();
////                                        c.moveToPosition(position);
////                                        db.archiveEvent(c.getInt(0));
////                                        String freq = c.getString(4);
////
////
////                                        adapter.swapCursor(db.selectCheckedRecordsByFreq(freq));
////
////
////                                        dialog.cancel();
////                                    }
////                                })
////                                .setNegativeButton("Nop", new DialogInterface.OnClickListener() {
////                                    public void onClick(DialogInterface dialog, int which) {
////                                        dialog.cancel();
////                                 }
////                        }).show();
//                    }
//                    adapter.notifyDataSetChanged();
//                    return;
//                }
//                if (swipeDetector.getAction() == SwipeDetector.Action.None) {
//                    Cursor c = adapter.getCursor();
//                    String activity_id = c.getString(0);
//                    String activity_name = c.getString(1);
//                    String freq = c.getString(4);
//                    String importance = c.getString(5);
//
//                    //new AlertDialog.Builder(self).setTitle("Mesaž").setMessage("edit").show();
//                    Intent Edit = new Intent(self, Edit.class);
//                    Edit.putExtra("activity_id", activity_id);
//                    Edit.putExtra("activity_name", activity_name);
//                    Edit.putExtra("activity_freq", freq);
//                    Edit.putExtra("activity_importance", importance);
//                    startActivity(Edit);
//                }
//            }
//        });

        listView.setAdapter(adapter);

        dailyButton = (TextView) findViewById(R.id.dailyButton);
        weeklyButton = (TextView) findViewById(R.id.weeklyButton);
        randomButton = (TextView) findViewById(R.id.randomButton);
        dailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(0xFFEF3E36);
                dailyButton.setTextColor(0xFFFFFFFF);
                weeklyButton.setBackgroundColor(0xFF666);
                randomButton.setBackgroundColor(0xFF666);
                adapter.swapCursor(db.selectCheckedRecordsByFreq("daily"));
                frequency = "daily";

            }
        });
        weeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(0xFFEF3E36);
                weeklyButton.setTextColor(0xFFFFFFFF);
                dailyButton.setBackgroundColor(0xFF666);
                randomButton.setBackgroundColor(0xFF666);
                adapter.swapCursor(db.selectCheckedRecordsByFreq("weekly"));
                frequency = "weekly";
            }
        });
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(0xFFEF3E36);
                dailyButton.setBackgroundColor(0xFF666);
                weeklyButton.setBackgroundColor(0xFF666);
                randomButton.setTextColor(0xFFFFFFFF);
                adapter.swapCursor(db.selectCheckedRecordsByFreq("random"));
                frequency = "random";
            }
        });
    }

    private String getMessage(int min, int max) {
        randomNum = random.nextInt((max - min) + 1) + min;
        return db.getPlainMessage(randomNum);
    }

    private String createNonBadgeMessage(String frequency, int importance, Date timing) {
       return message.getRandomMessage(frequency,importance,timing);
    }

    public static MyActivity getInstance() {
        return self;
    }

}

