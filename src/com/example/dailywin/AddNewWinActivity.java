package com.example.dailywin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import com.example.dailywin.db.MyDB;
import com.example.dailywin.services.NailItService;

/**
 * Created with IntelliJ IDEA.
 * User: gimlet
 * Date: 11/16/13
 * Time: 5:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddNewWinActivity extends Activity {

    private AddNewWinActivity self;
    private Button saveButton;
    private RadioGroup radioGroup;
    private SeekBar seekBar;
    private EditText name;
    private MyDB db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        String checked = "daily";
        if (getIntent() != null && getIntent().getExtras() != null) {
            checked = getIntent().getStringExtra("f_freq");
        }

        super.onCreate(savedInstanceState);
        self = this;

        int resourceId = getResources().getIdentifier(checked, "id", getPackageName());

        setContentView(R.layout.addnew);
        name = (EditText) findViewById(R.id.editText);
        saveButton = (Button) findViewById(R.id.saveButton);
        radioGroup = (RadioGroup) findViewById(R.id.radioFrequency);
        radioGroup.getCheckedRadioButtonId();
        radioGroup.check(resourceId);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        db = new MyDB(this);



        name.setFocusableInTouchMode(true);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(name, InputMethodManager.SHOW_IMPLICIT);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().trim().length() == 0) {
                    name.setError("Name is required!");
                    return;
                }

                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                String freq = checkedRadioButtonId == R.id.daily ? "daily" : checkedRadioButtonId == R.id.weekly ? "weekly" : "random";
                db.createRecord(name.getText().toString(), freq, seekBar.getProgress());

                Intent passCategory = new Intent(self, MyActivity.class);
                passCategory.putExtra("freq_filter", freq);
                startActivity(passCategory);

                //this is ServiceTest

                String strInputMsg = name.getText().toString();
                Intent msgIntent = new Intent(self, NailItService.class);
                msgIntent.putExtra(NailItService.PARAM_IN_MSG, strInputMsg);
                startService(msgIntent);
                System.out.println("@@@@@@@@@@@ servis " + name.getText().toString());
            }
        });

    }
}
