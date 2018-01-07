package com.allisonmcentire.buildingtradesandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import com.squareup.picasso.Picasso;

/**
 * Created by allisonmcentire on 11/26/17.
 */

public class BaseActivity extends AppBaseActivity {

    private ProgressDialog mProgressDialog;
    private static FirebaseDatabase mDatabase;
    public String uid;
    public String tag;
    public String name;
    private DatabaseReference mPostReference;
    private ValueEventListener mPostListener;





    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }
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

    public String getUserID() {
//        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        return String.valueOf(currentFirebaseUser.getUid());
//
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = null;


        if (user != null) {


            uid = user.getUid();




        }
        return uid;

    }






}
