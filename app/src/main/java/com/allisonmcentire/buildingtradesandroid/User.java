package com.allisonmcentire.buildingtradesandroid;

/**
 * Created by allisonmcentire on 11/26/17.
 */

public class User {

    public String field_full_name;
    public String field_profile_picture;
    public String field_uid;
    public String name;
    public String nothing;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String field_full_name, String field_profile_picture, String field_uid, String name, String nothing) {
        this.field_full_name = field_full_name;
        this.field_profile_picture = field_profile_picture;
        this.field_uid = field_uid;
        this.name = name;
        this.nothing = nothing;
    }
}
