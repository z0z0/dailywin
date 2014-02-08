package com.example.dailywin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.dailywin.adapters.ListViewAdapter;
import com.example.dailywin.db.MyDB;
import com.example.dailywin.gestures.SwipeDetector;
import com.example.dailywin.messages.Message;
import com.example.dailywin.messages.MessageMan;
import com.example.dailywin.utils.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MyActivity extends Activity {

    private Button addActivityButton;
    private ImageView profileImageView;
    public static Map<Integer, String> messageMap = new HashMap<Integer, String>();
    Random random = new Random();
    private static final int MIN_RANGE=1;
    private static final int MAX_RANGE=35;

    static {
        messageMap.put(1, "Nailed it!");
        messageMap.put(2, "Rocked it today!");
        messageMap.put(3, "Wow. Impressive.");
        messageMap.put(4, "Little things matter!");
        messageMap.put(5, "Nicely done!");
        messageMap.put(6, "Plain awesome.");
    }

    /**
     * Called when the activity is first created.
     */
    private MyActivity self;
    private MyDB db;
    private ListView listView;
    private SimpleCursorAdapter adapter;
    private TextView dailyButton;
    private TextView weeklyButton;
    private TextView randomButton;
    String frequency = "daily";
    private int randomNum;
    private long consecutiveCount;
    private Message message = new Message("mesazzz");

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
        btn.setBackgroundColor(0xFF421C52);

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
        listView.setOnTouchListener(swipeDetector);
        adapter = new ListViewAdapter(this, R.layout.list_item, cursor, new String[]{MyDB.WIN_NAME, "checked"}, new int[]{R.id.listItemLabel, R.id.itemChecked}, CursorAdapter.FLAG_AUTO_REQUERY);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                if (swipeDetector.swipeDetected()) {
                    if (swipeDetector.getAction() == SwipeDetector.Action.LR) {

                        //this counts how many activities were checked that day, and if it's more than once,
                        // we do wont let them check it again
                        // the pic should also be different

                        Cursor c = adapter.getCursor();
                        String freq = c.getString(4);
                        c.moveToPosition(position);
                        int dailyWinId = c.getInt(0);


                        //calculate consecutive days
                        consecutiveCount = db.consecutive(dailyWinId);
                        System.out.println("consecutive count " + consecutiveCount);
                        adapter.swapCursor(db.selectCheckedRecordsByFreq(freq));

                        Dialog nailedIt = new Dialog(self, R.style.alertDialog);

                        nailedIt.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        nailedIt.setContentView(R.layout.nail_dialog);


                        if (adapter.getCursor().getInt(7) == 0) {
                            SimpleDateFormat df = new SimpleDateFormat(DateTimeUtil.dateTimeFormat);
                            Date d = new Date();
                            db.createEvent(dailyWinId,df.format(d));
                            message.setStrategy(new MessageMan(adapter.getCursor().getString(4),adapter.getCursor().getInt(5),d));
                            nailedIt.setTitle(message.getMessage());
                        }
                        else{
                            nailedIt.setTitle("Znachi ovo je vec cekirano");
                        }


                        nailedIt.setCancelable(true);
                        nailedIt.setCanceledOnTouchOutside(true);

                        TextView poruka = (TextView) nailedIt.findViewById(R.id.plainMessage);
                        poruka.setText(getMessage(MIN_RANGE, MAX_RANGE));

                        ImageView badge = (ImageView) nailedIt.findViewById(R.id.badgeGlobal);
                        ImageView badge2 = (ImageView) nailedIt.findViewById(R.id.badgeConsecutive);
                        badge.setImageResource(R.drawable.yogaicon);
                        badge2.setImageResource(R.drawable.checked);

                        nailedIt.show();

//                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(self, R.style.alertDialog);
//                        alertDialog.setTitle("Poruka").setMessage(getMessage(MIN_RANGE,MAX_RANGE)+" "+consecutiveCount)
//                                .setPositiveButton("Wohoo", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                       dialog.cancel();
//                                    }
//                                }).show();
                    }

                    if (swipeDetector.getAction() == SwipeDetector.Action.RL) {
                        Cursor c = adapter.getCursor();
                        //gets frequency
                        String freq = c.getString(4);
                        Log.i("MyActivity", freq);
                        Log.i("DB__________________", c.getString(3));
                        System.out.println("  ------------------------ " + freq);
                        c.moveToPosition(position);
                        db.archiveEvent(c.getInt(0));
                        adapter.swapCursor(db.selectCheckedRecordsByFreq(freq));
                        new AlertDialog.Builder(self).setMessage("Deleted.")
                                .setPositiveButton("M'kay", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).show();
                    }
                    adapter.notifyDataSetChanged();
                    return;
                }
                if (swipeDetector.getAction() == SwipeDetector.Action.None) {
                    Cursor c = adapter.getCursor();
                    String activity_id = c.getString(0);
                    String activity_name = c.getString(1);
                    String freq = c.getString(4);
                    String importance = c.getString(5);

                    //new AlertDialog.Builder(self).setTitle("Mesaž").setMessage("edit").show();
                    Intent Edit = new Intent(self, Edit.class);
                    Edit.putExtra("activity_id", activity_id);
                    Edit.putExtra("activity_name", activity_name);
                    Edit.putExtra("activity_freq", freq);
                    Edit.putExtra("activity_importance", importance);
                    startActivity(Edit);
                }
            }
        });

        listView.setAdapter(adapter);

        dailyButton = (TextView) findViewById(R.id.dailyButton);
        weeklyButton = (TextView) findViewById(R.id.weeklyButton);
        randomButton = (TextView) findViewById(R.id.randomButton);
        dailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(0xFF421C52);
                weeklyButton.setBackgroundColor(0xFF666);
                randomButton.setBackgroundColor(0xFF666);
                adapter.swapCursor(db.selectCheckedRecordsByFreq("daily"));
                frequency = "daily";

            }
        });
        weeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(0xFF421C52);
                dailyButton.setBackgroundColor(0xFF666);
                randomButton.setBackgroundColor(0xFF666);
                adapter.swapCursor(db.selectCheckedRecordsByFreq("weekly"));
                frequency = "weekly";
            }
        });
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(0xFF421C52);
                dailyButton.setBackgroundColor(0xFF666);
                weeklyButton.setBackgroundColor(0xFF666);
                adapter.swapCursor(db.selectCheckedRecordsByFreq("random"));
                frequency = "random";
            }
        });
    }

    private String getMessage(int min, int max) {
        randomNum = random.nextInt((max - min) + 1) + min;
       // return messageMap.get(randomNum);
        return db.getPlainMessage(randomNum);
    }
}

