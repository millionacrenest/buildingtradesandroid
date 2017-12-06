package com.allisonmcentire.buildingtradesandroid;

import ly.img.android.PESDK;

/**
 * Created by allisonmcentire on 12/5/17.
 */

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        PESDK.init(this);
    }

}
