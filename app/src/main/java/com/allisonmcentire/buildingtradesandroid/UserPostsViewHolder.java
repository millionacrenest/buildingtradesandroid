package com.allisonmcentire.buildingtradesandroid;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by allisonmcentire on 1/7/18.
 */

public class UserPostsViewHolder extends RecyclerView.ViewHolder {



    //  public TextView pdfLink;
    public TextView titleTextView;




    public UserPostsViewHolder(View itemView) {
        super(itemView);


        //   pdfLink = itemView.findViewById(R.id.post_link);
        titleTextView = itemView.findViewById(R.id.user_post_title);



    }

    public void bindToPost(UserInformation userPosts, View.OnClickListener starClickListener) {

        titleTextView.setText(userPosts.name + "\n" + userPosts.locationNotes);


//        pdfLink.setText(resource.field_document_file);



    }
}


