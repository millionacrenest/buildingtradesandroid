package com.allisonmcentire.buildingtradesandroid;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by allisonmcentire on 12/3/17.
 */

public class VideoFragment extends VideoListFragment {

    public VideoFragment() {}



    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        Query recentPostsQuery = databaseReference.child("video")
                .limitToFirst(100);
        // [END recent_posts_query]

        return recentPostsQuery;
    }
}

