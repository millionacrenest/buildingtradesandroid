package com.allisonmcentire.buildingtradesandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ly.img.android.PESDK;


public class PESDKActivity extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        PESDK.init(this);
    }
}
