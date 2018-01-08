package com.allisonmcentire.buildingtradesandroid;

import java.util.Date;

/**
 * Created by allisonmcentire on 12/16/17.
 */

public class Notes {
    public String title;
    public String body;
    public String field_uid;
    public String noteImage;
    public Date date;

    public Notes() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Notes(String title, String body, String field_uid, String noteImage, Date date) {
        this.title = title;
        this.body = body;
        this.field_uid = field_uid;
        this.noteImage = noteImage;
        this.date = date;
    }
}
