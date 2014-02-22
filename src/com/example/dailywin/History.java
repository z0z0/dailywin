package com.example.dailywin;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.dailywin.adapters.ListViewAdapter;
import com.example.dailywin.db.MyDB;

/**
 * Created by Zorana on 12/15/13.
 */
public class History extends Activity {


    private MyDB db;
    private History self;
    private ListView historyList;
    private SimpleCursorAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self = this;
        setContentView(R.layout.history);

        db = new MyDB(this);
        Cursor cursor = db.selectItemsCountByPeriod();

        //getting history list
        historyList = (ListView) findViewById(R.id.historyView);
        adapter = new SimpleCursorAdapter(this, R.layout.history_item, cursor, new String[]{MyDB.WIN_NAME, "win_count"}, new int[]{R.id.historyItemLabel, R.id.historyItemTotal}, CursorAdapter.FLAG_AUTO_REQUERY);
        historyList.setAdapter(adapter);

    }
}
