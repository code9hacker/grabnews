package com.bitfault.grabnews.screen.webview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitfault.grabnews.R;
import com.bitfault.grabnews.common.util.AppConstants;
import com.bitfault.grabnews.common.util.StringUtils;

public class WebViewActivity extends AppCompatActivity {

    private static final String LOG_TAG = "WebViewActivity";

    private ImageButton tvBackButton;
    private TextView tvTitle;
    private WebView webView;
    private RelativeLayout progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        tvBackButton = findViewById(R.id.back_button);
        tvTitle = findViewById(R.id.tv_header_text);
        webView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.progress_bar);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
        tvBackButton.setOnClickListener((__) -> finish());
        if (getIntent().getExtras() != null) {
            String title = getIntent().getExtras().getString(AppConstants.BUNDLE_KEY_WEBVIEW_TITLE);
            if (StringUtils.isNotNullEmpty(title)) {
                tvTitle.setText(title);
            }
            String url = getIntent().getExtras().getString(AppConstants.BUNDLE_KEY_WEBVIEW_URL);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(url);
        } else {
            Log.e(LOG_TAG, "Invalid URL provided in intent arguments");
        }
    }
}
