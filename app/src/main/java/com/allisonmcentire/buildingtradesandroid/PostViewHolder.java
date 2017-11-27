package com.allisonmcentire.buildingtradesandroid;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by allisonmcentire on 11/26/17.
 */

public class PostViewHolder extends RecyclerView.ViewHolder {


    public TextView titleView;
    public TextView authorView;
    public TextView bodyView;


    public PostViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.post_title);
        authorView = itemView.findViewById(R.id.post_author);
        bodyView = itemView.findViewById(R.id.post_body);

    }

    public void bindToPost(Post post, View.OnClickListener starClickListener) {
        titleView.setText(post.title);
        authorView.setText(post.author);
        bodyView.setText(post.body);


    }
}
