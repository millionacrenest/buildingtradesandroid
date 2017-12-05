package com.allisonmcentire.buildingtradesandroid;

/**
 * Created by allisonmcentire on 11/26/17.
 */

public class Comment {


//    public String uid;
//    public String author;
    public String text;

    public Comment() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public Comment(String text) {
//        this.uid = uid;
//        this.author = author;
        this.text = text;
    }
}
