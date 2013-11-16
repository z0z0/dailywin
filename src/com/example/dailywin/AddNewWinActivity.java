package com.example.dailywin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self = this;
        setContentView(R.layout.addnew);

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
