package com.example.dailywin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.example.dailywin.db.MyDB;

/**
 * Created by Zorana on 12/15/13.
 */
public class Profile extends Activity {

    private Profile self;
    private ImageButton historyButton;
    private ImageButton badgesButton;
    private LinearLayout historyLayout;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self = this;

        setContentView(R.layout.profile);
        historyButton = (ImageButton) findViewById(R.id.buttonHistory);
        badgesButton = (ImageButton) findViewById(R.id.buttonBadges);
        historyLayout = (LinearLayout) findViewById(R.id.historyLayout);
        historyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(self, History.class);
                startActivity(intent);
            }
        });

    }
}
