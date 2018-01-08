package com.allisonmcentire.buildingtradesandroid;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by allisonmcentire on 1/7/18.
 */

public class UsersFragment extends UserPostsFragment {

    public UsersFragment() {}



    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        String uid = settings.getString("uid", "9dc58735-7028-40df-9a07-8f62ec8748f1");
        Query recentUsersQuery = databaseReference.child("ids").child(uid).child("nodeLocations");
        // [END recent_posts_query]

        return recentUsersQuery;
    }
}
