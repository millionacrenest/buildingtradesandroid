package com.allisonmcentire.buildingtradesandroid;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

/**
 * Created by allisonmcentire on 12/3/17.
 */

public class VideoViewHolder extends RecyclerView.ViewHolder {


   // public String videoLink;
  //  public VideoView myVideoP;
    public TextView videoLink;
   // private YouTubePlayerView mYouTube;


    public VideoViewHolder(View itemView) {
        super(itemView);

//        myVideoP = (VideoView) itemView.findViewById(R.id.myVideo);
        videoLink = (TextView) itemView.findViewById(R.id.post_video_link);
       // mYouTube = (YouTubePlayerView) itemView.findViewById(R.id.player);



    }

    public void bindToPost(Post post, View.OnClickListener starClickListener) {




       videoLink.setText(post.field_media_video_embed_field);




    }
}

