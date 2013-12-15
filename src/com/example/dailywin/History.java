package com.example.dailywin;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;

/**
 * Created by Zorana on 12/15/13.
 */
public class History extends Activity {


    private History self;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self = this;

        setContentView(R.layout.history);


//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
//                String freq = checkedRadioButtonId == R.id.daily ? "daily" : checkedRadioButtonId == R.id.weekly ? "weekly" : "random";
//                db.createRecord(name.getText().toString(), category.getSelectedItem().toString(), freq, seekBar.getProgress());
//                finish();
//            }
//        });
    }
}
