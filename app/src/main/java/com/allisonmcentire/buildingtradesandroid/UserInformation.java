package com.allisonmcentire.buildingtradesandroid;

/**
 * Created by allisonmcentire on 11/22/17.
 */


public class UserInformation {

    public String UID;
    public String name;
    public double latitude;
    public double longitude;
    public String image;
    public String locationNotes;


    public UserInformation(){

    }
    public UserInformation(String UID,String name,double latitude,double longitude,String image,String locationNotes){
        this.UID=UID;
        this.name=name;
        this.latitude=latitude;
        this.longitude=longitude;
        this.image=image;
        this.locationNotes=locationNotes;

    }

}
