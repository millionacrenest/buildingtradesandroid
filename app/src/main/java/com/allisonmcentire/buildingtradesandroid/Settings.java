package com.allisonmcentire.buildingtradesandroid;

/**
 * Created by allisonmcentire on 12/16/17.
 */

public class Settings {

    public String orgName;
    public String logo;
    public String field_facebook;
    public String field_website_link;


    public Settings() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Settings(String orgName, String field_facebook, String field_website_link, String logo) {
        this.orgName = orgName;
        this.logo = logo;
        this.field_facebook = field_facebook;
        this.field_website_link = field_website_link;
    }
}
