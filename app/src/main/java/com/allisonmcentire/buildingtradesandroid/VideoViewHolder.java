package com.allisonmcentire.buildingtradesandroid;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

/**
 * Created by allisonmcentire on 12/3/17.
 */

public class VideoViewHolder extends RecyclerView.ViewHolder {


    public String videoLink;
    public VideoView myVideo;
    public TextView videoLinkTitle;



    public VideoViewHolder(View itemView) {
        super(itemView);

        myVideo = (VideoView) itemView.findViewById(R.id.myVideo);
        videoLinkTitle = itemView.findViewById(R.id.post_video_link);



    }

    public void bindToPost(Post post, View.OnClickListener starClickListener) {



        videoLink = post.field_media_video_embed_field;
        Uri vidUri = Uri.parse(videoLink);
        myVideo.setVideoURI(vidUri);
        videoLinkTitle.setText(post.field_media_video_embed_field);


    }
}

