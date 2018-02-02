package com.slotonline.ofiiak;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.slotonline.ofiiak.game_mania.Current_Game_Activity;


public class Next_Game_Activity extends Activity {

    private static final String TAG = Next_Game_Activity.class.getSimpleName();

    private static LogNode mLogNode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webgame);
        getConfigData();

        mLogNode = new LogWrapper();
    }

    private void getConfigData() {
        String url = getIntent().getStringExtra(Start_Screen_Activity.BASE_KEY_URL);
        update(url);
    }

    public static void println(int priority, String tag, String msg, Throwable tr) {
        try {
            if (mLogNode != null) {
                mLogNode.println(priority, tag, msg, tr);
            }
        } catch (Exception ignore){}

    }


    private void update(String url) {

        println(1, TAG, "open game", null);

        WebView webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error) {
                super.onReceivedError(view, request, error);
                println(1, TAG, "error open game", null);
                openGame();
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request,
                                            WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                openGame();
            }
        });
        init(webView.getSettings());
        webView.loadUrl(url);

    }


    @SuppressLint("SetJavaScriptEnabled")
    private void init(WebSettings webSettings) {
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
    }

    private void openGame() {
        Intent intent = new Intent(this, Current_Game_Activity.class);
        startActivity(intent);
        finish();
    }
}
