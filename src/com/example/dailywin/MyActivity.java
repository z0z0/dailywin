package com.example.dailywin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.example.dailywin.adapters.ListViewAdapter;
import com.example.dailywin.db.MyDB;
import com.example.dailywin.gestures.SwipeDetector;

public class MyActivity extends Activity {

    private Button addActivityButton;
    private ImageView profileImageView;

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
    @Override
    public void onCreate(Bundle savedInstanceState) {

        if(getIntent() != null && getIntent().getExtras() != null){
            frequency = getIntent().getStringExtra("freq_filter");
        }

        super.onCreate(savedInstanceState);
        self = this;
        setContentView(R.layout.main);
        addActivityButton = (Button) findViewById(R.id.addActivity);

        int resourceId = getResources().getIdentifier(frequency+"Button", "id", getPackageName());

        TextView btn = (TextView) findViewById(resourceId);
        btn.setBackgroundColor(0xFFA537FD);

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
//        Cursor cursor = db.selectRecordsWithCount();
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
                        if (adapter.getCursor().getInt(7) > 0) return;


                        new AlertDialog.Builder(self).setTitle("Poruka").setMessage("Upravo si cekirala aktivnost. Toliko.")
                                .setPositiveButton("Wohoo", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Cursor c = adapter.getCursor();
                                        String freq = c.getString(4);
                                        c.moveToPosition(position);
                                        db.createEvent(c.getInt(0));
                                        adapter.swapCursor(db.selectCheckedRecordsByFreq(freq));
                                        dialog.cancel();
                                    }
                                }).show();

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
                        new AlertDialog.Builder(self).setTitle("Poruka").setMessage("Maće maće ")
                                .setPositiveButton("M'kay", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).show();
                    }
                    adapter.notifyDataSetChanged();

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
                v.setBackgroundColor(0xFFA537FD);
                weeklyButton.setBackgroundColor(0xFF666);
                randomButton.setBackgroundColor(0xFF666);

                adapter.swapCursor(db.selectCheckedRecordsByFreq("daily"));
                frequency = "daily";

            }
        });
        weeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(0xFFA537FD);
                dailyButton.setBackgroundColor(0xFF666);
                randomButton.setBackgroundColor(0xFF666);

                adapter.swapCursor(db.selectCheckedRecordsByFreq("weekly"));
                frequency = "weekly";
            }
        });
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(0xFFA537FD);
                dailyButton.setBackgroundColor(0xFF666);
                weeklyButton.setBackgroundColor(0xFF666);

                adapter.swapCursor(db.selectCheckedRecordsByFreq("random"));
                frequency = "random";
            }
        });
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        adapter.swapCursor(db.selectCheckedRecordsByFreq("weekly"));
//
//    }
}

