package com.allisonmcentire.buildingtradesandroid;

import android.content.Context;
import android.net.Uri;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by allisonmcentire on 11/26/17.
 */

public class Resource {


    public String field_document_file;
    public String name;

    public Resource() {


        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Resource(String field_document_file, String name) {

        this.field_document_file = field_document_file;
        this.name = name;
    }


}
