package com.allisonmcentire.buildingtradesandroid;

/**
 * Created by allisonmcentire on 11/26/17.
 */


import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by allisonmcentire on 11/26/17.
 */

public class FrontpageFragment extends PostListFragment {

    public FrontpageFragment() {}
//




    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        String tag = settings.getString("tag", "null");

        Query recentPostsQuery = databaseReference.child("frontpage").child(tag);
        // [END recent_posts_query]

        return recentPostsQuery;
    }
}
