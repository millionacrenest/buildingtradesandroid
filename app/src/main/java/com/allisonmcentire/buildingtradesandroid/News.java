package com.allisonmcentire.buildingtradesandroid;

/**
 * Created by allisonmcentire on 11/23/17.
 */

public class News {



        private String title;
        private String field_media_image;
        private String body;
        private boolean isExpandable;

        public News(String s, boolean b){

        }
        public News(String title,String field_media_image,String body){
            this.title = title;
            this.field_media_image = field_media_image;
            this.body  = body;
            this.isExpandable = isExpandable;
        }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getField_media_image() {
        return field_media_image;
    }

    public void setField_media_image(String field_media_image) {
        this.field_media_image = field_media_image;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }
}
