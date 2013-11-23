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
    private Button dailyButton;
    private Button weeklyButton;
    private Button randomButton;

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
        Cursor cursor = db.selectRecordsWithCount();
        listView = (ListView) findViewById(R.id.listView);
        adapter = new SimpleCursorAdapter(this, R.layout.list_item, cursor, new String[]{MyDB.EMP_NAME, "count1"}, new int[]{R.id.listItemLabel, R.id.itemCount}, CursorAdapter.FLAG_AUTO_REQUERY);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(self)
                        .setTitle("Did you done it?")
                        .setMessage("Did you done it?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Cursor c = adapter.getCursor();
                                c.moveToPosition(position);
                                db.createEvent(c.getInt(0));
                                adapter.swapCursor(db.selectRecordsWithCount());
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });
        listView.setAdapter(adapter);

        dailyButton = (Button) findViewById(R.id.dailyButton);
        weeklyButton = (Button) findViewById(R.id.weeklyButton);
        randomButton = (Button) findViewById(R.id.randomButton);
        dailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.swapCursor(db.selectRecordsByFreq("daily"));
            }
        });
        weeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.swapCursor(db.selectRecordsByFreq("weekly"));
            }
        });
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.swapCursor(db.selectRecordsWithCount());
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        adapter.swapCursor(db.selectRecordsWithCount());

    }
}
