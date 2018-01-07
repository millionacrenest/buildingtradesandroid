package com.allisonmcentire.buildingtradesandroid;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class UserActivity extends BaseActivity {

    private static final String TAG = "UserActivity";
    private DatabaseReference mUserReference;
    private DatabaseReference mSiteReference;
    private DatabaseReference mContactReference;
    private DatabaseReference mIDReference;
    private TextView mUserName;
    private TextView mLocaltag;
    private TextView mEmail;
    private DatabaseReference mPostReference;
    private ValueEventListener mPostListener;
    public String uid;
    public String tag;
    public String name;
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    boolean success = false;
    Button subscribeButton;


    private static final int PREFERENCE_MODE_PRIVATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mUserName = findViewById(R.id.userName);
        mLocaltag = findViewById(R.id.userLocaltag);
        mEmail = findViewById(R.id.userEmail);
        uid = getUserID();
        getTag();
    //    name = getName();








       // mPostReference = FirebaseDatabase.getInstance().getReference().child("ids").child(uid);







//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            // Name, email address, and profile photo Url
////            String name = user.getDisplayName();
////            String email = user.getEmail();
////            Uri photoUrl = user.getPhotoUrl();
//
//            // Check if user's email is verified
//           // boolean emailVerified = user.isEmailVerified();
//
//            // The user's ID, unique to the Firebase project. Do NOT use this value to
//            // authenticate with your backend server, if you have one. Use
//            // FirebaseUser.getToken() instead.
//            String uid = user.getUid();
//            T
//        } else {
//            Toast.makeText(UserActivity.this, "user is null", Toast.LENGTH_SHORT).show();
//        }

        //Toast.makeText(UserActivity.this, getTag(), Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        // [END handle_data_extras]

        final Button subscribeBtn = findViewById(R.id.subscribeButton);
        subscribeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                // [START subscribe_topics]
                //mTag = mLocaltag.getText().toString();

                if (tag != null) {




                    FirebaseMessaging.getInstance().subscribeToTopic("all");
                    FirebaseMessaging.getInstance().subscribeToTopic(tag);
                    FirebaseMessaging.getInstance().subscribeToTopic(uid);
                  //  FirebaseMessaging.getInstance().subscribeToTopic(name);
                   // Toast.makeText(UserActivity.this, tag, Toast.LENGTH_SHORT).show();


                    // [END subscribe_topics]

                    // Log and toast
                    String msg = getString(R.string.msg_subscribed);
                    Log.d(TAG, msg);
                    Toast.makeText(UserActivity.this, msg+ " to "+ tag, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(UserActivity.this, "An error occured. Please check your network connection and try again.", Toast.LENGTH_SHORT).show();

                }


            }
        });



    }



    @Override
    protected void onStart() {
        super.onStart();



        //  what I need to do is output the data from drupal so it makes the uid the key automatically

//


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }








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


    public void getTag() {

        getUserID();

        if (uid != null) {


            mPostReference = FirebaseDatabase.getInstance().getReference().child("ids").child(uid);
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    User user = dataSnapshot.getValue(User.class);
                    // [START_EXCLUDE]

                    tag = user.localtag;
                    name = user.name;

                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(UserActivity.this);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("tag", tag);
                    editor.putString("name", name);

                    //  editor.putString("name", name);


                    editor.apply();


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message

                    // [END_EXCLUDE]
                }
            };
            mPostReference.addValueEventListener(postListener);
            // [END post_value_event_listener]

            // Keep copy of post listener so we can remove it when app stops
            mPostListener = postListener;


        }

       // return tag;

    }

//    public String getName() {
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
//                    name = user.name;
//
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
//        return name;
//
//    }





}
