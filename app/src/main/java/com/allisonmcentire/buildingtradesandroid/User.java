package com.allisonmcentire.buildingtradesandroid;

/**
 * Created by allisonmcentire on 11/26/17.
 */

public class User {

    public String field_full_name;
    public String field_profile_picture;
    public String field_uid;
    public String name;
    public String localtag;
    public String nothing;
    public String field_phone_number;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String field_full_name, String field_uid, String name, String localtag, String nothing, String field_profile_picture, String field_phone_number) {
        this.field_full_name = field_full_name;
        this.field_profile_picture = field_profile_picture;
        this.field_uid = field_uid;
        this.name = name;
        this.localtag = localtag;
        this.nothing = nothing;
        this.field_phone_number = field_phone_number;
    }



}
