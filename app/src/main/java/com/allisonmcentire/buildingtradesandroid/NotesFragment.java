package com.allisonmcentire.buildingtradesandroid;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by allisonmcentire on 12/17/17.
 */

public class NotesFragment extends NotesListFragment  {

    public NotesFragment() {}



    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys



        Query recentNotesQuery = databaseReference.child("notes").child("AAUx7vqLo3VeHGJoD5EgWYGxnbG2");
        // [END recent_posts_query]

        return recentNotesQuery;
    }
}

