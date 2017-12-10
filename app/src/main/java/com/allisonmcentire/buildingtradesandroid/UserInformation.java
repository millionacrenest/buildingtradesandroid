package com.allisonmcentire.buildingtradesandroid;

/**
 * Created by allisonmcentire on 11/22/17.
 */


public class UserInformation {

   // public String UID;
    public String name;
    public double latitude;
    public double longitude;
    public String locationNotes;
    public String localtag;
    public String image;
    public String sharedWith;
    public String UID;


    public UserInformation(){

    }
    public UserInformation(String name,double latitude,double longitude, String locationNotes, String localtag, String image, String sharedWith, String UID){
      //  this.UID=UID;
        this.name=name;
        this.latitude=latitude;
        this.longitude=longitude;
        this.locationNotes=locationNotes;
        this.localtag=localtag;
        this.image=image;
        this.sharedWith=sharedWith;
        this.UID=UID;

    }

}
