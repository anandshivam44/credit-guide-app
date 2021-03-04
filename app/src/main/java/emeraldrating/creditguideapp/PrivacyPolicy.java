package emeraldrating.creditguideapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class PrivacyPolicy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        WebView webView = (WebView) findViewById(R.id.webview_privacy_policy);
        webView.loadUrl("https://emeraldrating.com/privacy-statement/");
        webView.getSettings().setJavaScriptEnabled(true);
    }
}