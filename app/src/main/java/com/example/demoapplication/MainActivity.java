package com.example.demoapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private final Activity activity=this;
    //private AppBarLayout.LayoutParams imageViewPara;
    //private AppBarLayout.LayoutParams webViewPara;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    webView.loadUrl("http://192.168.0.13:8072");
                    return true;
                case R.id.navigation_dashboard:
                    webView.loadUrl("https://developer.android.com/guide/webapps/webview.html");
                    return true;
                case R.id.navigation_notifications:
                   webView.loadUrl("http://www.runoob.com/w3cnote/android-tutorial-relativelayout.html");
                    return true;
                case R.id.navigation_share:
                    Intent share = new Intent(MainActivity.this, share.class);
                    String WebUrl = webView.getUrl();
                    share.putExtra("URL",WebUrl);
                    startActivity(share);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intentIntegrator.setPrompt("Scan");
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null){
            if(result.getContents()==null){
                Toast.makeText(this,"scan canceled",Toast.LENGTH_LONG).show();
            }else
            {
                Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();

            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
