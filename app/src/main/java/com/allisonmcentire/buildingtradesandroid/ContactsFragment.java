package com.allisonmcentire.buildingtradesandroid;

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
        Query recentUsersQuery = databaseReference.child("users")
                .limitToFirst(1000);
        // [END recent_posts_query]

        return recentUsersQuery;
    }
}