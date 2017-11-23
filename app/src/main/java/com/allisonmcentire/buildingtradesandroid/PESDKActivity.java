package com.allisonmcentire.buildingtradesandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ly.img.android.PESDK;

public class PESDKActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesdk);
        PESDK.init(this);
    }
}
