package com.allisonmcentire.buildingtradesandroid;

/**
 * Created by allisonmcentire on 11/26/17.
 */


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by allisonmcentire on 11/26/17.
 */

public class FrontpageFragment extends PostListFragment {

    public FrontpageFragment() {}


    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        Query recentPostsQuery = databaseReference.child("frontpage")
                .limitToFirst(100);
        // [END recent_posts_query]

        return recentPostsQuery;
    }
}
