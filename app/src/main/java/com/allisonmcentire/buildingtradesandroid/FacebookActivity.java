package com.allisonmcentire.buildingtradesandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class FacebookActivity extends BaseActivity {

    private String pdfURL;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        mWebView = findViewById(R.id.myWebViewer3);

        pdfURL = "https://www.facebook.com/WashingtonStateBuildingTrades/";

        mWebView.loadUrl(pdfURL);
    }
}