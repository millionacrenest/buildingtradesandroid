package com.allisonmcentire.buildingtradesandroid;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MapDetailActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "MapDetailActivity";
    public static final String EXTRA_POST_KEY = "EXTRA_POST_KEY";
    private DatabaseReference mMapReference;
    private ValueEventListener mMapListener;
    private String mMapKey;
    private TextView mTitleView;
    private TextView mBodyView;
    private TextView mAuthorView;
    private TextView mLocalView;
    private TextView mUserNameView;
    private ImageView mCommentImage;
    private TextView mImageCommentLink;
    private ImageView mImageView;
    private String imgURL;
    Context context=this;

   private String mAuthorName;
    String mAuthorUID;
    String mAuthorEmail;
    String mAuthorTag;
//
    private Button fab2;
    private DatabaseReference mCommentsReference;
    private DatabaseReference mUserReference;
    private CommentAdapter mAdapter;

    private EditText mCommentField;
    private Button mCommentButton;
    private RecyclerView mCommentsRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_detail);
      //  getUserInfo();



        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        tag = settings.getString("tag", "null");
        name = settings.getString("name", "null");
//        name = settings.getString("name", "null");
//        uid = settings.getString("uid", "null");
//        email = settings.getString("", "null");
//
//        editor.putString("name", name);
//        editor.putString("uid", uid);
//        editor.putString("email", email);






        Intent intent = getIntent();
        final String sitekey = intent.getStringExtra(EXTRA_POST_KEY);


        // Get post key from intent
        mMapKey = sitekey;
        if (mMapKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }




        mTitleView = findViewById(R.id.post_title2);
        mBodyView = findViewById(R.id.post_body2);

        mImageView = findViewById(R.id.imageView2);
        mAuthorView = findViewById(R.id.comment_author);
        mLocalView = findViewById(R.id.localtag);
        mUserNameView = findViewById(R.id.userName);
        mLocalView.setText(tag);
        mUserNameView.setText(name);


        mCommentsReference = FirebaseDatabase.getInstance().getReference().child("post-comments").child(mMapKey);
        mCommentField = findViewById(R.id.field_comment_text2);
        mCommentButton = findViewById(R.id.button_post_comment2);
        mCommentsRecycler = findViewById(R.id.recycler_comments2);
//
        mCommentButton.setOnClickListener(this);
        mCommentsRecycler.setLayoutManager(new LinearLayoutManager(this));



        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fabCommentPhoto);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(MapDetailActivity.this, CameraCommentActivity.class);
                intent.putExtra("EXTRA_POST_KEY", sitekey);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mMapReference = FirebaseDatabase.getInstance().getReference().child("nodeLocations").child(tag).child(mMapKey);
        // Add value event listener to the post
        // [START post_value_event_listener]
        ValueEventListener mapListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                // [START_EXCLUDE]

                mTitleView.setText(userInformation.name);
                mBodyView.setText(userInformation.locationNotes);

                imgURL = userInformation.image;


                Picasso.with(context)
                        .load(imgURL)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(mImageView);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(MapDetailActivity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mMapReference.addValueEventListener(mapListener);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        mMapListener = mapListener;



       //  Listen for comments
        mAdapter = new CommentAdapter(this, mCommentsReference);
        mCommentsRecycler.setAdapter(mAdapter);


    }


    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
        if (mMapListener != null) {
            mMapReference.removeEventListener(mMapListener);
        }

        mAdapter.cleanupListener();

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_post_comment2) {
            postComment();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(MapDetailActivity.this, MapsActivity.class));
    }




    private void postComment() {

        FirebaseDatabase.getInstance().getReference().child("nodeLocations").child(tag).child(mMapKey)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user information


                        String commentImage = getImageUrl();
                        // Create new comment object
                        String commentText = mCommentField.getText().toString();
                        String mAuthorName = mUserNameView.getText().toString();
                        String mUID = getUserID();




                        Comment comment = new Comment(commentText,mAuthorName,commentImage,mUID);
                        String uid = getUserID();
                        // Push the comment, it will appear in the list
                        mCommentsReference.push().setValue(comment);
                        mUserReference = FirebaseDatabase.getInstance().getReference().child("ids");
                        mUserReference.child(uid).child("comment").push().setValue(comment);

                        // Clear the field
                        mCommentField.setText(null);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private static class CommentViewHolder extends RecyclerView.ViewHolder {

        //public TextView authorView;
        public TextView bodyView;
        public TextView authorView;
        public ImageView commentImageView;



        public CommentViewHolder(View itemView) {
            super(itemView);

            authorView = itemView.findViewById(R.id.comment_author);
            bodyView = itemView.findViewById(R.id.comment_body);
            commentImageView = itemView.findViewById(R.id.comment_photo);


        }

    }

    private class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

        private Context mContext;
        private DatabaseReference mDatabaseReference;
        private ChildEventListener mChildEventListener;

        private List<String> mCommentIds = new ArrayList<>();
        private List<Comment> mComments = new ArrayList<>();

        public CommentAdapter(final Context context, DatabaseReference ref) {
            mContext = context;
            mDatabaseReference = ref;

            // Create child event listener
            // [START child_event_listener_recycler]
            ChildEventListener childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                    Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                    // A new comment has been added, add it to the displayed list
                    Comment comment = dataSnapshot.getValue(Comment.class);

                    // [START_EXCLUDE]
                    // Update RecyclerView
                    mCommentIds.add(dataSnapshot.getKey());
                    mComments.add(comment);
                    notifyItemInserted(mComments.size() - 1);
                    // [END_EXCLUDE]
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                    Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                    // A comment has changed, use the key to determine if we are displaying this
                    // comment and if so displayed the changed comment.
                    Comment newComment = dataSnapshot.getValue(Comment.class);
                    String commentKey = dataSnapshot.getKey();

                    // [START_EXCLUDE]
                    int commentIndex = mCommentIds.indexOf(commentKey);
                    if (commentIndex > -1) {
                        // Replace with the new data
                        mComments.set(commentIndex, newComment);

                        // Update the RecyclerView
                        notifyItemChanged(commentIndex);
                    } else {
                        Log.w(TAG, "onChildChanged:unknown_child:" + commentKey);
                    }
                    // [END_EXCLUDE]
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                    // A comment has changed, use the key to determine if we are displaying this
                    // comment and if so remove it.
                    String commentKey = dataSnapshot.getKey();

                    // [START_EXCLUDE]
                    int commentIndex = mCommentIds.indexOf(commentKey);
                    if (commentIndex > -1) {
                        // Remove data from the list
                        mCommentIds.remove(commentIndex);
                        mComments.remove(commentIndex);

                        // Update the RecyclerView
                        notifyItemRemoved(commentIndex);
                    } else {
                        Log.w(TAG, "onChildRemoved:unknown_child:" + commentKey);
                    }
                    // [END_EXCLUDE]
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                    Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                    // A comment has changed position, use the key to determine if we are
                    // displaying this comment and if so move it.
                    Comment movedComment = dataSnapshot.getValue(Comment.class);
                    String commentKey = dataSnapshot.getKey();

                    // ...
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                    Toast.makeText(mContext, "Failed to load comments.",
                            Toast.LENGTH_SHORT).show();
                }
            };
            ref.addChildEventListener(childEventListener);
            // [END child_event_listener_recycler]

            // Store reference to listener so it can be removed on app stop
            mChildEventListener = childEventListener;
        }

        @Override
        public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.item_comment, parent, false);
            return new CommentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CommentViewHolder holder, int position) {



            Comment comment = mComments.get(position);
              holder.authorView.setText(comment.author);
            holder.bodyView.setText(comment.text);




            Picasso.with(context)
                    .load(comment.commentImage)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.commentImageView);



        }

        @Override
        public int getItemCount() {
            return mComments.size();
        }

        public void cleanupListener() {
            if (mChildEventListener != null) {
                mDatabaseReference.removeEventListener(mChildEventListener);
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }



    public String getImageUrl() {
        String image = (String) getIntent().getStringExtra("Image Comment URL");
//        if (image != null) {
//            Picasso.with(this)
//                    .load(image)
//                    .placeholder(R.drawable.ic_menu_gallery) //optional
//                    .centerCrop()                        //optional
//                    .into(mImageView);
//        } else {
//            //do nothing
//        }
        return String.valueOf(image);
    }


}


