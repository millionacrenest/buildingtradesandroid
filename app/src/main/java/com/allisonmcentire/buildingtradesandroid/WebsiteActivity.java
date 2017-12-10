package com.allisonmcentire.buildingtradesandroid;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class WebsiteActivity extends BaseActivity {


    //private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);
//        mWebView = findViewById(R.id.myWebViewer2);
//
        Uri pdfURL = Uri.parse("https://www.seattlebt.info");
//
//        mWebView.loadUrl(pdfURL);


        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.setDataAndNormalize(pdfURL);
        webIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try{
            startActivity(webIntent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(WebsiteActivity.this, "Error: Please check your data connection", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(WebsiteActivity.this, WebsiteActivity.class));
    }
}
