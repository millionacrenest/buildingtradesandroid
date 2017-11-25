package com.allisonmcentire.buildingtradesandroid;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by allisonmcentire on 11/23/17.
 */

public class PushActivity extends AppCompatActivity {

    TextView textView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.push_activity);
        textView = (TextView) findViewById(R.id.textView);
    }
}
