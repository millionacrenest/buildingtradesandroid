package com.allisonmcentire.buildingtradesandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ChildEventListener mChildEventListener;
    private DatabaseReference mUsers;
    private Button fab;
    Marker marker;
    private SharedPreferences preferenceSettings;
    private DatabaseReference mPostReference;
    private ValueEventListener mPostListener;
    private String uid;
    private String tag;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //uid = preferenceSettings.getString("uid","null");

        uid = getUserID();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        tag = settings.getString("tag", "null");



        //tag = getTag();
       // FirebaseMessaging.getInstance().subscribeToTopic("all");
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ChildEventListener mChildEventListener;

        //tag = getTag();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(MapsActivity.this, Map2Activity.class);
                startActivity(intent);
            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();

        //  what I need to do is output the data from drupal so it makes the uid the key automatically





    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Toast.makeText(MapsActivity.this, tag, Toast.LENGTH_SHORT).show();
        mUsers = FirebaseDatabase.getInstance().getReference("nodeLocations").child(tag);


        googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(47.6062,-122.3321) , 10.0f) );
        googleMap.setOnMarkerClickListener(this);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        mUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    UserInformation user = s.getValue(UserInformation.class);
                    LatLng location = new LatLng(user.latitude, user.longitude);
                    String sitekey = s.getKey();
                    mMap.addMarker(new MarkerOptions().position(location).title(user.name).snippet(sitekey)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                    mUsers.push().setValue(marker);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else  {
            mMap.setMyLocationEnabled(true);
        }

    }

    @Override
    public void onLocationChanged(Location location) {


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        startActivity(new Intent(MapsActivity.this, MapDetailActivity.class));
       // Toast.makeText(MapsActivity.this, marker.getSnippet(), Toast.LENGTH_SHORT).show();// display toast
        Intent intent = new Intent(this, MapDetailActivity.class);
        intent.putExtra("EXTRA_POST_KEY", marker.getSnippet());
      //  intent.putExtra("userTag", mTag);
        startActivity(intent);


        return true;

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(MapsActivity.this, FrontpageActivity.class));
    }



//    public String getTag() {
//
//        getUserID();
//
//        if (uid != null) {
//
//
//            mPostReference = FirebaseDatabase.getInstance().getReference().child("ids").child(uid);
//            ValueEventListener postListener = new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    // Get Post object and use the values to update the UI
//                    User user = dataSnapshot.getValue(User.class);
//                    // [START_EXCLUDE]
//
//                    tag = user.localtag;
//
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    // Getting Post failed, log a message
//
//                    // [END_EXCLUDE]
//                }
//            };
//            mPostReference.addValueEventListener(postListener);
//            // [END post_value_event_listener]
//
//            // Keep copy of post listener so we can remove it when app stops
//            mPostListener = postListener;
//
//
//        }
//
//        return tag;
//
//    }

//

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}