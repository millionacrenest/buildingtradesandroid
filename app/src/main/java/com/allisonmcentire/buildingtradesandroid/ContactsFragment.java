package com.allisonmcentire.buildingtradesandroid;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by allisonmcentire on 12/3/17.
 */

public class ContactsFragment extends UserListFragment {

    public ContactsFragment() {}



    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        String tag = settings.getString("tag", "null");
        Query recentUsersQuery = databaseReference.child("users").child(tag);
        // [END recent_posts_query]

        return recentUsersQuery;
    }
}