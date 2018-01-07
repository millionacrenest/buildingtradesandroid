package com.allisonmcentire.buildingtradesandroid;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WebsiteActivity extends BaseActivity {

//    private String pdfURL;
//    private WebView mWebView;
    private DatabaseReference mPostReference;
    private ValueEventListener mPostListener;
    private DatabaseReference mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);
      //  mWebView = findViewById(R.id.myWebViewer3);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //tag = settings.getString("tag", "null");
        String tag = "SeattleBT";

        mUsers = FirebaseDatabase.getInstance().getReference("settings").child(tag);

        mUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    Settings settings = s.getValue(Settings.class);


                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(settings.field_website_link));
                    startActivity(intent);
                    finish();
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(WebsiteActivity.this, WebsiteActivity.class));
    }
}
