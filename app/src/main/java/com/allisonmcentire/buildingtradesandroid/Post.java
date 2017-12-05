package com.allisonmcentire.buildingtradesandroid;

import android.content.Context;
import android.net.Uri;

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
    public String field_image;
    public String nothing;
    public String field_document_file;
    public String field_media_video_embed_field;
    public String name;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String uid, String author, String title, String body, String field_media_image, String field_image, String nothing, String field_document_file, String field_media_video_embed_field, String name) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
        this.field_media_image = field_media_image;
        this.field_image = field_image;
        this.nothing = nothing;
        this.field_document_file = field_document_file;
        this.field_media_video_embed_field = field_media_video_embed_field;
        this.name = name;
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
        result.put("resource", field_document_file);
        result.put("video", field_media_video_embed_field);
        result.put("nothing", nothing);
        result.put("starCount", starCount);
        result.put("stars", stars);
        result.put("post_photo", field_image);
        result.put("post_name", name);

        return result;
    }
    // [END post_to_map]

}
