package com.allisonmcentire.buildingtradesandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebsiteActivity extends BaseActivity {

    private String pdfURL;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);
        mWebView = findViewById(R.id.myWebViewer2);

        pdfURL = "https://www.seattlebt.info";

        mWebView.loadUrl(pdfURL);
    }
}
