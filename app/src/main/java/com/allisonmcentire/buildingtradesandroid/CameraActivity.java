package com.allisonmcentire.buildingtradesandroid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ly.img.android.ui.activities.ImgLyActivity;
import ly.img.android.ui.utilities.PermissionRequest;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Random;
import java.util.Timer;

import ly.img.android.PESDK;
import ly.img.android.acs.GPSLocationProvider;
import ly.img.android.sdk.models.constant.Directory;
import ly.img.android.sdk.models.state.CameraSettings;
import ly.img.android.sdk.models.state.EditorSaveSettings;
import ly.img.android.sdk.models.state.manager.SettingsList;
import ly.img.android.ui.activities.CameraPreviewBuilder;
import ly.img.android.ui.activities.ImgLyIntent;
import ly.img.android.ui.utilities.PermissionRequest;


public class CameraActivity extends Activity implements PermissionRequest.Response {


    private static final String FOLDER = "ImgLy";
    public static int CAMERA_PREVIEW_RESULT = 1;
    Uri filePath;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://building-trades-1891a.appspot.com/images");    //change the url according to your firebase app
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SettingsList settingsList = new SettingsList();


        settingsList
                .getSettingsModel(CameraSettings.class)
                .setExportDir(Directory.DCIM, FOLDER)
                .setExportPrefix("camera_")

                .getSettingsModel(EditorSaveSettings.class)
                .setExportDir(Directory.DCIM, FOLDER)
                .setExportPrefix("result_")
                .setJpegQuality(80, false)
                .setSavePolicy(EditorSaveSettings.SavePolicy.KEEP_SOURCE_AND_CREATE_ALWAYS_OUTPUT);

        new CameraPreviewBuilder(this)
                .setSettingsList(settingsList)
                .startActivityForResult(this, CAMERA_PREVIEW_RESULT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CAMERA_PREVIEW_RESULT) {

            String resultPath = data.getStringExtra(ImgLyIntent.RESULT_IMAGE_PATH);
            String sourcePath = data.getStringExtra(ImgLyIntent.SOURCE_IMAGE_PATH);




            if (resultPath != null) {
                // Add result file to Gallery
               // String resultString = String.valueOf(resultCode);
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(resultPath))));
                final StorageReference childRef = storageRef.child(random());
                Uri uri = Uri.fromFile(new File(resultPath));

                //uploading the image
                UploadTask uploadTask = childRef.putFile(uri);

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        childRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageURL = String.valueOf(uri);
                                Intent intent = new Intent(CameraActivity.this, Map2Activity.class);
                                intent.putExtra("Image URL", imageURL);
                                startActivity(intent);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                        Toast.makeText(CameraActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CameraActivity.this, Map2Activity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(CameraActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            if (sourcePath != null) {
                // Add sourceType file to Gallery
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(sourcePath))));
            }

            Toast.makeText(PESDK.getAppContext(), "Image saved on: " + resultPath, Toast.LENGTH_LONG).show();
        } else if (resultCode == RESULT_CANCELED && requestCode == CAMERA_PREVIEW_RESULT && data != null) {
            String sourcePath = data.getStringExtra(ImgLyIntent.SOURCE_IMAGE_PATH);
            Toast.makeText(PESDK.getAppContext(), "Editor canceled, sourceType image is:\n" + sourcePath, Toast.LENGTH_LONG).show();
        } else {
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionRequest.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void permissionGranted() {

    }

    @Override
    public void permissionDenied() {
        finish();
        System.exit(0);
    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(12);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // do your stuff
        } else {

        }
    }
}

//    FirebaseStorage storage = FirebaseStorage.getInstance();
//    StorageReference storageReference = storage.getReferenceFromUrl("gs://building-trades-1891a.appspot.com").child("images");
//
//    public static int CAMERA_PREVIEW_RESULT = 1;
//    private static final String FOLDER = "ImgLy";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //setContentView(R.layout.activity_camera);
//
//        final File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "address");
//
//        if (!f.exists()) {
//
//            boolean rv = f.mkdir();
//
//        } else {
//
//        }
//
//
//        SettingsList settingsList = new SettingsList();
//        settingsList
//                // Set custom camera export settings
//                .getSettingsModel(CameraSettings.class)
//              // .setExportDir(Directory.DCIM, FOLDER)
//                .setExportPrefix("camera_")
//                // Set custom editor export settings
//                .getSettingsModel(EditorSaveSettings.class)
//               // .setExportDir(Directory.DCIM, FOLDER)
//                .setExportPrefix("result_")
//                .setSavePolicy(
//                        EditorSaveSettings.SavePolicy.RETURN_ALWAYS_ONLY_OUTPUT
//                );
//
//        new CameraPreviewBuilder(this)
//                .setSettingsList(settingsList)
//                .startActivityForResult(this, CAMERA_PREVIEW_RESULT);
//
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode == CAMERA_PREVIEW_RESULT) {
//            String resultPath =
//                    data.getStringExtra(ImgLyActivity.RESULT_IMAGE_PATH);
//            String sourcePath =
//                    data.getStringExtra(ImgLyActivity.SOURCE_IMAGE_PATH);
//
//            if (resultPath != null) {
//                // Scan result file
//                File file =  new File(resultPath);
//                Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                Uri contentUri = Uri.fromFile(file);
//                scanIntent.setData(contentUri);
//                sendBroadcast(scanIntent);
//            }
//
//            if (sourcePath != null) {
//                // Scan camera file
//                File file =  new File(sourcePath);
//                Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                Uri contentUri = Uri.fromFile(file);
//                scanIntent.setData(contentUri);
//                sendBroadcast(scanIntent);
//            }
//
//            Toast.makeText(this, "Image Save on: " + resultPath, Toast.LENGTH_LONG).show();
//        }
//    }
//
//    // Important permission request for Android 6.0 and above, don't forget this!
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        PermissionRequest.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//
//    @Override
//    public void permissionGranted() {
//
//    }
//
//    @Override
//    public void permissionDenied() {
//        // The Permission was rejected by the user. The Editor was not opened, as it could not save the result image.
//        // TODO for you: Show a Hint to the User
//    }
//}