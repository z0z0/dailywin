package com.example.dailywin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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
        Cursor cursor = db.selectRecords();
        listView = (ListView) findViewById(R.id.listView);
        adapter = new SimpleCursorAdapter(this, R.layout.list_item, cursor, new String[]{MyDB.EMP_NAME}, new int[]{R.id.listItemLabel}, CursorAdapter.FLAG_AUTO_REQUERY);
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
                adapter.swapCursor(db.selectRecordsByFreq("random"));
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        adapter.swapCursor(db.selectRecords());

    }
}
