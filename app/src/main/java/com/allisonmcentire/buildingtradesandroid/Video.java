package com.allisonmcentire.buildingtradesandroid;

/**
 * Created by allisonmcentire on 12/6/17.
 */

public class Video {

    public String name;
    public String field_media_video_embed_field;


    public Video() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Video(String name, String field_media_video_embed_field) {

        this.name = name;
        this.field_media_video_embed_field = field_media_video_embed_field;
    }
}
