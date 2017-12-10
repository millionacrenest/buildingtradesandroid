package com.allisonmcentire.buildingtradesandroid;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Map2Activity extends BaseActivity implements View.OnClickListener{
    private Button fab;
    TextView locLatitude;
    TextView locLongitude;
    Button getLocation;
    EditText mSiteField;
    EditText mSiteName;
    Button mSiteTextButton;
//    ImageView mImageView;
    LocationTracker tracker;
    double latitude=0.0d,longitude=0.0d;
    private DatabaseReference mSiteReference;
    private ValueEventListener mSiteListener;
    private String thisImage;
    TextView mImageLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCam);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action

                Intent intent = new Intent(Map2Activity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        // Initialize Database
        mSiteReference = FirebaseDatabase.getInstance().getReference().child("nodeLocations").child("SeattleBT");


        locLatitude=(TextView)findViewById(R.id.location);
        locLongitude=(TextView)findViewById(R.id.location2);
       // getLocation=(Button)findViewById(R.id.getlocation);
        mSiteField=(EditText)findViewById(R.id.field_location_text2);
        mSiteTextButton=(Button)findViewById(R.id.button_post_site2);
        mSiteName=(EditText)findViewById(R.id.field_location_name);
  //      mImageView=(ImageView)findViewById(R.id.imageView3);
        mImageLink=(TextView)findViewById(R.id.imageLink);





        mSiteTextButton.setOnClickListener(this);
//        mSiteTextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //create LocationTracker Object
//                tracker = new LocationTracker(Map2Activity.this);
//                // check if location is available
//                if (tracker.isLocationEnabled) {
//                    latitude = tracker.getLatitude();
//                    longitude = tracker.getLongitude();
//                    locLatitude.setText(String.valueOf(latitude));
//                    locLongitude.setText(String.valueOf(longitude));
//
//                } else {
//                    // show dialog box to user to enable location
//                    tracker.askToOnLocation();
//                }
//            }
//        });
    }

    @Override
    public void onStart() {
        super.onStart();

        tracker = new LocationTracker(Map2Activity.this);
        // check if location is available
        if (tracker.isLocationEnabled) {
            latitude = tracker.getLatitude();
            longitude = tracker.getLongitude();
            locLatitude.setText(String.valueOf(latitude));
            locLongitude.setText(String.valueOf(longitude));

        } else {
            // show dialog box to user to enable location
            tracker.askToOnLocation();
        }

        String thisImage = getImageUrl();
        mImageLink.setText(thisImage);

        // Add value event listener to the post
        // [START post_value_event_listener]
        ValueEventListener siteListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                // [START_EXCLUDE]
                //mSiteName.setText(userInformation.name);
               // mSiteField.setText(userInformation.locationNotes);
              //  mImageUrl.setText(userInformation.image);
              //  latitude = tracker.getLatitude();
              //  longitude = tracker.getLongitude();


                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // [START_EXCLUDE]
                Toast.makeText(Map2Activity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mSiteReference.addValueEventListener(siteListener);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        mSiteListener = siteListener;


    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_post_site2) {
            postSite();

        }
    }


    private void postSite() {

        FirebaseDatabase.getInstance().getReference().child("nodeLocations").child("SeattleBT").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {



                        String uid = getUserID();
                        // Get user information
                        UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                        String name = mSiteName.getText().toString();

                        // Create new comment object
                        String locationNotes = mSiteField.getText().toString();
                        String localtag = "SeattleBT";
                        String sharedWith = "SeattleBT";

                        String image = mImageLink.getText().toString();

                        Double latitude = Double.parseDouble(locLatitude.getText().toString());
                        Double longitude = Double.parseDouble(locLongitude.getText().toString());

                                //String image = mImageUrl.getText().toString();
                        userInformation = new UserInformation(name,latitude,longitude,locationNotes,localtag,image,sharedWith,uid);

                        // Push the comment, it will appear in the list
                        mSiteReference.push().setValue(userInformation);
                       // display toast

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    public String getImageUrl() {
        String image = (String) getIntent().getStringExtra("Image URL");
//        if (image != null) {
//            Picasso.with(this)
//                    .load(image)
//                    .placeholder(R.drawable.ic_menu_gallery) //optional
//                    .centerCrop()                        //optional
//                    .into(mImageView);
//        } else {
//            //do nothing
//        }
        return String.valueOf(image);
    }
}

