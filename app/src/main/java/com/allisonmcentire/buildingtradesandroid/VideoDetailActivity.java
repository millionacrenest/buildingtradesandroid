package com.allisonmcentire.buildingtradesandroid;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allisonmcentire on 12/3/17.
 */

public class VideoDetailActivity extends BaseActivity {

    private static final String TAG = "VideoDetailActivity";

    public static final String EXTRA_POST_KEY = "post_key";

    private DatabaseReference mPostReference;
    private ValueEventListener mPostListener;
    private String mPostKey;
    private String mVideoLink;


   // private WebView mYoutTubeView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String tag = settings.getString("tag", "null");
        // Get post key from intent
        mPostKey = getIntent().getStringExtra(EXTRA_POST_KEY);
        if (mPostKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }

        // Initialize Database
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("videos").child(tag).child(mPostKey);


        // Initialize Views


     //   mYoutTubeView = findViewById(R.id.myWebViewer3);



    }

    @Override
    public void onStart() {
        super.onStart();

        // Add value event listener to the post
        // [START post_value_event_listener]
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Video video = dataSnapshot.getValue(Video.class);
                // [START_EXCLUDE]

             //   Uri vidLink = Uri.parse(video.field_media_video_embed_field);
                //mYoutTubeView.loadUrl(post.field_media_video_embed_field);
//
//                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.field_media_video_embed_field));
//               // Intent webIntent = new Intent(Intent.ACTION_VIEW,
//                       // Uri.parse(video.field_media_video_embed_field));
//                startActivity(appIntent);

               //  startActivity(webIntent);

                // [END_EXCLUDE]

                Uri uri = Uri.parse(video.field_media_video_embed_field);
                Intent videoIntent = new Intent(Intent.ACTION_VIEW);
                videoIntent.setData(uri);
                videoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                try{
                    startActivity(videoIntent);
                    finish();
                }catch(ActivityNotFoundException e){
                    Toast.makeText(VideoDetailActivity.this, "Error: Please check your connection and try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(VideoDetailActivity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mPostReference.addValueEventListener(postListener);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        mPostListener = postListener;




    }

    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
        if (mPostListener != null) {
            mPostReference.removeEventListener(mPostListener);
        }




    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }









}
