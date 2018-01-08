package com.allisonmcentire.buildingtradesandroid;

/**
 * Created by allisonmcentire on 1/7/18.
 */

public class UserPosts {


    public String title;
//    public String key;
//    public String body;
//    public String image;

    public UserPosts() {


        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public UserPosts(String title) {

        this.title = title;
//        this.key = key;
//        this.body = body;
//        this.image = image;
    }


}

