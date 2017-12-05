package com.allisonmcentire.buildingtradesandroid;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;

public class Map2Activity extends BaseActivity implements View.OnClickListener{
    private Button fab;
    TextView location;
    Button getLocation;
    EditText mSiteField;
    EditText mSiteName;
    Button mSiteTextButton;
    TextView mImageUrl;
    LocationTracker tracker;
    double latitude=0.0d,longitude=0.0d;
    private DatabaseReference mSiteReference;
    private ValueEventListener mSiteListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCam);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Click action
//                Intent intent = new Intent(Map2Activity.this, CameraActivity.class);
//                startActivity(intent);
//            }
//        });

        // Initialize Database
        mSiteReference = FirebaseDatabase.getInstance().getReference().child("nodeLocations");


        location=(TextView)findViewById(R.id.location);
        getLocation=(Button)findViewById(R.id.getlocation);
        mSiteField=(EditText)findViewById(R.id.field_location_text2);
        mSiteTextButton=(Button)findViewById(R.id.button_post_site2);
        mSiteName=(EditText)findViewById(R.id.field_location_name);
       // mImageUrl=(TextView)findViewById(R.id.textViewImageURL);



        mSiteTextButton.setOnClickListener(this);
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create LocationTracker Object
                tracker = new LocationTracker(Map2Activity.this);
                // check if location is available
                if (tracker.isLocationEnabled) {
                    latitude = tracker.getLatitude();
                    longitude = tracker.getLongitude();
                    location.setText("Your Location is Latitude= " + latitude + " Longitude= " + longitude);

                } else {
                    // show dialog box to user to enable location
                    tracker.askToOnLocation();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

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
        final String uid = getUid();
        FirebaseDatabase.getInstance().getReference().child("nodeLocations")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user information
                        UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                        String name = mSiteName.getText().toString();

                        // Create new comment object
                        String locationNotes = mSiteField.getText().toString();

                        //String image = mImageUrl.getText().toString();
                        userInformation = new UserInformation(uid,name,latitude,longitude,locationNotes);

                        // Push the comment, it will appear in the list
                        mSiteReference.push().setValue(userInformation);
                       // display toast

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }
}

