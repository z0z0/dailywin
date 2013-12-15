    package com.example.dailywin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.dailywin.db.MyDB;

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
    private Spinner category;
    private RadioGroup radioGroup;
    private SeekBar seekBar;
    private EditText name;
    private MyDB db;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        String checked = "daily";
        if(getIntent() != null && getIntent().getExtras() != null){
           checked = getIntent().getStringExtra("f_freq");
        }



        super.onCreate(savedInstanceState);
        self = this;

        int resourceId = getResources().getIdentifier(checked, "id", getPackageName());


        setContentView(R.layout.addnew);
        name = (EditText) findViewById(R.id.editText);
        category = (Spinner) findViewById(R.id.category);
        category.setAdapter(ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item));
        saveButton = (Button) findViewById(R.id.saveButton);
        radioGroup = (RadioGroup) findViewById(R.id.radioFrequency);
        radioGroup.getCheckedRadioButtonId();
        radioGroup.check(resourceId);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        db = new MyDB(this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                String freq = checkedRadioButtonId == R.id.daily ? "daily" : checkedRadioButtonId == R.id.weekly ? "weekly" : "random";
                db.createRecord(name.getText().toString(), category.getSelectedItem().toString(), freq, seekBar.getProgress());

                Intent passCategory = new Intent(self, MyActivity.class);
                passCategory.putExtra("freq_filter", freq);
                startActivity(passCategory);

               // finish();
            }
        });

    }

}
