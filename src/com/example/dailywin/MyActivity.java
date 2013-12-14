package com.example.dailywin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.dailywin.db.MyDB;

public class MyActivity extends Activity {

    private Button addActivityButton;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        self = this;
        setContentView(R.layout.main);
        addActivityButton = (Button) findViewById(R.id.addActivity);

        addActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(self, AddNewWinActivity.class);
                startActivity(intent);
            }
        });

        db = new MyDB(this);
//        Cursor cursor = db.selectRecordsWithCount();
        Cursor cursor = db.selectCheckedRecordsByFreq("daily");
        listView = (ListView) findViewById(R.id.listView);

        final SwipeDetector swipeDetector = new SwipeDetector();
        listView.setOnTouchListener(swipeDetector);

        adapter = new SimpleCursorAdapter(this, R.layout.list_item, cursor, new String[]{MyDB.WIN_NAME, "count1", "checked"}, new int[]{R.id.listItemLabel, R.id.itemCount, R.id.itemChecked}, CursorAdapter.FLAG_AUTO_REQUERY);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                if (swipeDetector.swipeDetected()) {
                    if (swipeDetector.getAction() == SwipeDetector.Action.LR) {

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
//                adapter.swapCursor(db.selectRecordsByFreq("daily"));
                adapter.swapCursor(db.selectCheckedRecordsByFreq("daily"));

            }
        });
        weeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                adapter.swapCursor(db.selectRecordsByFreq("weekly"));
                adapter.swapCursor(db.selectCheckedRecordsByFreq("weekly"));
            }
        });
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                adapter.swapCursor(db.selectRecordsWithCount());
                adapter.swapCursor(db.selectCheckedRecordsByFreq("random"));
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        adapter.swapCursor(db.selectCheckedRecordsByFreq("daily"));

    }
}
