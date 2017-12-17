package com.allisonmcentire.buildingtradesandroid;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

public class TestActivity extends BaseActivity {

    private TextView userTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        userTag = findViewById(R.id.textView2);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String tag = settings.getString("tag", "null");
        userTag.setText(tag);
        //Button testButton = findViewById(R.id.button2);

//
//        testButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // [START subscribe_topics]
//                //mTag = mLocaltag.getText().toString();
//
//
//
//
//            }
//        });

    }


}
