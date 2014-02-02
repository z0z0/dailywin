package com.example.dailywin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.example.dailywin.db.MyDB;

/**
 * Created by Uros on 23.12.13..
 */
public class Edit extends Activity {

    private Edit self;
    private Button saveButton;
    private Button deleteButton;
    private RadioGroup radioGroup;
    private SeekBar seekBar;
    private EditText name;
    private MyDB db;
    private String activity_id = "";
    private String activity_f = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        String activity_name = "", activity_freq = "", activity_importance = "";
        if(getIntent() != null && getIntent().getExtras() != null){
            activity_id = getIntent().getStringExtra("activity_id");
            activity_name = getIntent().getStringExtra("activity_name");
            activity_f = getIntent().getStringExtra("activity_freq");
            activity_importance = getIntent().getStringExtra("activity_importance");
        }

        super.onCreate(savedInstanceState);
        self = this;
        int resourceId = getResources().getIdentifier(activity_f, "id", getPackageName());

        setContentView(R.layout.edit);
        name = (EditText) findViewById(R.id.editText);
        name.setText(activity_name);
        saveButton = (Button) findViewById(R.id.saveButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        radioGroup = (RadioGroup) findViewById(R.id.radioFrequency);
        radioGroup.getCheckedRadioButtonId();
        radioGroup.check(resourceId);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setProgress(Integer.parseInt(activity_importance));
        db = new MyDB(this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();

                String freq = checkedRadioButtonId == R.id.daily ? "daily" : checkedRadioButtonId == R.id.weekly ? "weekly" : "random";
                db.updateRecord(Integer.parseInt(activity_id), name.getText().toString(), null, freq, seekBar.getProgress());

                Intent passCategory = new Intent(self, MyActivity.class);
                passCategory.putExtra("freq_filter", freq);
                startActivity(passCategory);
                // finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(self).setTitle("Notice").setMessage("Are you serial?")
                        .setNegativeButton("Nja", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("M'kay", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                db.archiveEvent(Integer.parseInt(activity_id));

                                Intent passCategory = new Intent(self, MyActivity.class);
                                passCategory.putExtra("freq_filter", activity_f);
                                startActivity(passCategory);
                            }
                        }).show();
            }
        });
    }
}