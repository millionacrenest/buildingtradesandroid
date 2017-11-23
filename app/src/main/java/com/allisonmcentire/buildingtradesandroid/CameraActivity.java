package com.allisonmcentire.buildingtradesandroid;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;

import ly.img.android.sdk.models.constant.Directory;
import ly.img.android.sdk.models.state.CameraSettings;
import ly.img.android.sdk.models.state.EditorSaveSettings;
import ly.img.android.sdk.models.state.manager.SettingsList;
import ly.img.android.ui.activities.CameraPreviewBuilder;
import ly.img.android.ui.activities.ImgLyActivity;
import ly.img.android.ui.utilities.PermissionRequest;

public class CameraActivity extends PESDKActivity implements PermissionRequest.Response {

    public static int CAMERA_PREVIEW_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SettingsList settingsList = new SettingsList();
        settingsList
                // Set custom camera export settings
                .getSettingsModel(CameraSettings.class)
               // .setExportDir(Directory.DCIM, FOLDER)
                .setExportPrefix("camera_")
                // Set custom editor export settings
                .getSettingsModel(EditorSaveSettings.class)
               // .setExportDir(Directory.DCIM, FOLDER)
                .setExportPrefix("result_")
                .setSavePolicy(
                        EditorSaveSettings.SavePolicy.RETURN_ALWAYS_ONLY_OUTPUT
                );

        new CameraPreviewBuilder(this)
                .setSettingsList(settingsList)
                .startActivityForResult(this, CAMERA_PREVIEW_RESULT);
    }

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

    // Important permission request for Android 6.0 and above, don't forget this!
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
        // The Permission was rejected by the user. The Editor was not opened, as it could not save the result image.
        // TODO for you: Show a Hint to the User
    }
}