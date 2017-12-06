package com.allisonmcentire.buildingtradesandroid;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by allisonmcentire on 11/26/17.
 */

public class BaseActivity extends AppBaseActivity {

    private ProgressDialog mProgressDialog;
    private DatabaseReference mUsers;
    public String mTag;


    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public String getUid() {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return String.valueOf(currentFirebaseUser.getUid());
    }

    public String getTag() {
        mUsers = FirebaseDatabase.getInstance().getReference("users");





        Query query = mUsers.orderByChild("field_uid").equalTo(getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot s : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        User user = s.getValue(User.class);
                        mTag = (user.nothing).toString();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return String.valueOf(mTag);











    }





}
