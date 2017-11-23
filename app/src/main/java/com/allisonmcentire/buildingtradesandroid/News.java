package com.allisonmcentire.buildingtradesandroid;

/**
 * Created by allisonmcentire on 11/23/17.
 */

public class News {



        public String title;
        public String field_media_image;
        public String body;

        public News(){

        }
        public News(String title,String field_media_image,String body){
            this.title = title;
            this.field_media_image = field_media_image;
            this.body  = body;
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







}
