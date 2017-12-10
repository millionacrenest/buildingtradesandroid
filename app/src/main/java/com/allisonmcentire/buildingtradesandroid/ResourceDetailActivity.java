package com.allisonmcentire.buildingtradesandroid;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by allisonmcentire on 12/4/17.
 */

public class ResourceDetailActivity extends BaseActivity {

    private static final String TAG = "ResourceDetailActivity";

    public static final String EXTRA_POST_KEY = "post_key";

    private DatabaseReference mPostReference;
    private ValueEventListener mPostListener;
    private String mPostKey;


//    private TextView mTitleView;
//    private TextView mPDFLink;
    private String pdfURL;
//    private WebView mWebView;
    private PDFView mPDFView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_detail);

        // Get post key from intent
        mPostKey = getIntent().getStringExtra(EXTRA_POST_KEY);
        if (mPostKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }

//        // Initialize Database
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("resources").child(mPostKey);

        mPDFView = findViewById(R.id.pdfViewHere);
//
//
//        // Initialize Views
////
////        mTitleView = findViewById(R.id.post_name);
////        mPDFLink = findViewById(R.id.post_link);
//
//        mWebView = findViewById(R.id.myWebViewer);





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
                Post post = dataSnapshot.getValue(Post.class);
                // [START_EXCLUDE]
                Uri uri = Uri.parse(post.field_document_file);
//                mTitleView.setText(post.name);
//                mPDFLink.setText(post.field_document_file);
               // pdfURL = ("http://docs.google.com/gview?embedded=true&url="+post.field_document_file);
             //  mPDFView.fromUri(uri);





                Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                pdfIntent.setDataAndType(uri, "application/pdf");
                pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                try{
                    startActivity(pdfIntent);
                }catch(ActivityNotFoundException e){
                    Toast.makeText(ResourceDetailActivity.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
                }







            //    mWebView.loadUrl(pdfURL);
                // [END_EXCLUDE]
                Toast.makeText(ResourceDetailActivity.this, uri.toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(ResourceDetailActivity.this, "Failed to load post.",
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

        startActivity(new Intent(ResourceDetailActivity.this, ResourceActivity.class));
    }



}
