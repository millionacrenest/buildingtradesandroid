package com.allisonmcentire.buildingtradesandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by allisonmcentire on 12/16/17.
 */

public class Notes2Activity extends BaseActivity implements View.OnClickListener {
    EditText mNoteTitle;
    EditText mNoteBody;
    Button mSaveButton;
    String title;
    String body;
    String field_uid;
    String notesImage;
    private DatabaseReference mNotesReference;
    private ValueEventListener mSiteListener;
    private Uri mImageUri = null;
    private Button btn,mSubmitBtn;
    private ImageView imageview;
    private int GALLERY = 1, CAMERA = 2;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    TextView mImageLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes2);
        String uid = getUserID();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCamNotes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                showPictureDialog();
//                Intent intent = new Intent(Notes2Activity.this, CameraActivity.class);
//                startActivity(intent);
//                finish();
            }
        });

        mNotesReference = FirebaseDatabase.getInstance().getReference().child("notes");

        mNoteTitle = findViewById(R.id.editNoteTitle);
        mNoteBody = findViewById(R.id.editNoteBody);
        mSaveButton = findViewById(R.id.buttonNoteSave);
        mImageLink=(TextView)findViewById(R.id.imageLink2);



        mStorage = FirebaseStorage.getInstance().getReference("images");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Upload");

        mSaveButton.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonNoteSave) {
            postNote();
            startPosting();
            Toast.makeText(Notes2Activity.this, uid,
                    Toast.LENGTH_SHORT).show();

        }

    }

    private void postNote() {
        title = mNoteTitle.getText().toString();
        body = mNoteBody.getText().toString();
        field_uid = getUserID();
        notesImage = getImageUrl();

            Notes notes = new Notes(title, body);



            mNotesReference.child(field_uid).push().setValue(notes);



//        ValueEventListener siteListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Get Post object and use the values to update the UI
//                Notes notes = dataSnapshot.getValue(Notes.class);
//                // [START_EXCLUDE]
//                String title = mNoteTitle.getText().toString();
//                String body = mNoteBody.getText().toString();
//                String field_uid = getUserID();
//
//
//                notes = new Notes (title,body,field_uid);
//                mNotesReference.child(field_uid).push().setValue(notes);
//
//                // [END_EXCLUDE]
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//                // [START_EXCLUDE]
//                Toast.makeText(Notes2Activity.this, "Failed to load post.",
//                        Toast.LENGTH_SHORT).show();
//                // [END_EXCLUDE]
//            }
//        };
//        mNotesReference.addValueEventListener(siteListener);
//        // [END post_value_event_listener]
//
//        // Keep copy of post listener so we can remove it when app stops
//        mSiteListener = siteListener;

    }

/// /    public String getNotesImageUrl() {
//        String notesImage = (String) getIntent().getStringExtra("notesImageURL");
////        if (image != null) {
////            Picasso.with(this)
////                    .load(image)
////                    .placeholder(R.drawable.ic_menu_gallery) //optional
////                    .centerCrop()                        //optional
////                    .into(mImageView);
////        } else {
////            //do nothing
////        }
//        return String.valueOf(notesImage);
//    }
private void showPictureDialog(){
    AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
    pictureDialog.setTitle("Select Action");
    String[] pictureDialogItems = {
            "Select photo from gallery",
            "Capture photo from camera" };
    pictureDialog.setItems(pictureDialogItems,
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            choosePhotoFromGallary();
                            break;
                        case 1:
                            takePhotoFromCamera();
                            break;
                    }
                }
            });
    pictureDialog.show();
}

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    ////////////////////////////
    private void startPosting(){

        if (mImageUri !=null){

            StorageReference filepath = mStorage.child("images").child(mImageUri.getLastPathSegment());


            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    DatabaseReference newPost = mDatabase.push();//push kreira uniq random id


                    newPost.child("image").setValue(downloadUrl.toString());

                }
            });
        }
    }

    ////////////////////////
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                mImageUri = data.getData();
                imageview.setImageURI(mImageUri);
                mImageLink.setText(mImageUri.toString());
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(thumbnail);
            imageview.setImageURI(mImageUri);
            mImageLink.setText(mImageUri.toString());

        }
    }

    public String getImageUrl() {
        String image = (String) getIntent().getStringExtra("Image URL");
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
