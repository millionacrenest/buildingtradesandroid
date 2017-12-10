package com.allisonmcentire.buildingtradesandroid;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
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

public class UserActivity extends BaseActivity {

    private static final String TAG = "UserActivity";
    private DatabaseReference mUserReference;
    private DatabaseReference mSiteReference;
    private DatabaseReference mContactReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);






     //   Toast.makeText(UserActivity.this, mTag, Toast.LENGTH_SHORT).show();



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
//            Toast.makeText(UserActivity.this, uid, Toast.LENGTH_SHORT).show();
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

        Button subscribeButton = findViewById(R.id.subscribeButton);
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [START subscribe_topics]
                FirebaseMessaging.getInstance().subscribeToTopic("all");

                // [END subscribe_topics]

                // Log and toast
                String msg = getString(R.string.msg_subscribed);
                Log.d(TAG, msg);
                Toast.makeText(UserActivity.this, msg, Toast.LENGTH_SHORT).show();
                postUser();
            }
        });

        Button logTokenButton = findViewById(R.id.logTokenButton);
        logTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get token
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UserActivity.this, SignInActivity.class));
                finish();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void postUser(){


      //  what I need to do is output the data from drupal so it makes the uid the key automatically


        String uid = getUserID();






        String name = "Allison McEntire";

// Create new comment object

        String localtag = "SeattleBT";
        String email = "allisonmcentire@gmail.com";


//String image = mImageUrl.getText().toString();
        User user = new User(name,uid,email,localtag);


        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("ids").child(uid).setValue(user);


        Toast.makeText(UserActivity.this, "success", Toast.LENGTH_SHORT).show();
//        // Get user information
//        User user = dataSnapshot.getValue(User.class);
//        String name = user.field_full_name;
//
//// Create new comment object
//
//        String localtag = user.nothing;
//        String email = user.name;
//
//
////String image = mImageUrl.getText().toString();
//        user = new User(name, localtag, uid, email);
//
//// Push the comment, it will appear in the list
//        mSiteReference.push().setValue(user);
//
//
    }


}
