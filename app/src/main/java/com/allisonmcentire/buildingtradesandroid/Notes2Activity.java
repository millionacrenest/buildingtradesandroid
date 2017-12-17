package com.allisonmcentire.buildingtradesandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by allisonmcentire on 12/16/17.
 */

public class Notes2Activity extends BaseActivity implements View.OnClickListener {
    EditText mNoteTitle;
    EditText mNoteBody;
    Button mSaveButton;
    String title;
    String body;
    String field_uid;
    private DatabaseReference mNotesReference;
    private ValueEventListener mSiteListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes2);
        String uid = getUserID();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCamNotes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action

                Intent intent = new Intent(Notes2Activity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        mNotesReference = FirebaseDatabase.getInstance().getReference().child("notes");

        mNoteTitle = findViewById(R.id.editNoteTitle);
        mNoteBody = findViewById(R.id.editNoteBody);
        mSaveButton = findViewById(R.id.buttonNoteSave);
        title = mNoteTitle.getText().toString();
        body = mNoteBody.getText().toString();
        field_uid = getUserID();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Add value event listener to the post
        // [START post_value_event_listener]
        ValueEventListener siteListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Notes notes = dataSnapshot.getValue(Notes.class);
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
                Toast.makeText(Notes2Activity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mNotesReference.addValueEventListener(siteListener);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        mSiteListener = siteListener;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonNoteSave) {
            postNote();

        }

    }

    private void postNote(String title, String body, String field_uid) {


            Notes notes = new Notes(title, body, field_uid);



            mNotesReference.child("notes").child(uid).setValue(notes);


//        ValueEventListener siteListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Get Post object and use the values to update the UI
//                Notes notes = dataSnapshot.getValue(Notes.class);
//                // [START_EXCLUDE]
//                String title = mNoteTitle.getText().toString();
//                String body = mNoteBody.getText().toString();
//                String field_uid = getUserID();
//
//
//                notes = new Notes (title,body,field_uid);
//                mNotesReference.child(field_uid).push().setValue(notes);
//
//                // [END_EXCLUDE]
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//                // [START_EXCLUDE]
//                Toast.makeText(Notes2Activity.this, "Failed to load post.",
//                        Toast.LENGTH_SHORT).show();
//                // [END_EXCLUDE]
//            }
//        };
//        mNotesReference.addValueEventListener(siteListener);
//        // [END post_value_event_listener]
//
//        // Keep copy of post listener so we can remove it when app stops
//        mSiteListener = siteListener;

    }
}
