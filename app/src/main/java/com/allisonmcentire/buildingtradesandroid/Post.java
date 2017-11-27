package com.allisonmcentire.buildingtradesandroid;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by allisonmcentire on 11/26/17.
 */

public class Post {

    public String uid;
    public String author;
    public String title;
    public String body;
    public String field_media_image;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String uid, String author, String title, String body, String field_media_image) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
        this.field_media_image = field_media_image;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("image", field_media_image);
        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
    }
    // [END post_to_map]

}
