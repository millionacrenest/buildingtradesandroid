package com.allisonmcentire.buildingtradesandroid;

/**
 * Created by allisonmcentire on 11/22/17.
 */


public class UserInformation {

   // public String UID;
    public String name;
    public double latitude;
    public double longitude;
    public String image;
    public String locationNotes;
    public String nothing;


    public UserInformation(){

    }
    public UserInformation(String name,double latitude,double longitude, String locationNotes, String nothing, String image){
      //  this.UID=UID;
        this.name=name;
        this.latitude=latitude;
        this.longitude=longitude;
        this.image=image;
        this.locationNotes=locationNotes;
        this.nothing=nothing;

    }

}
