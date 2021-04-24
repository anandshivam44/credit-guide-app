package emeraldrating.creditguideapp;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        WebView webView = (WebView) findViewById(R.id.webview_about_us);
        webView.loadUrl("https://emeraldrating.com/about-us/");
        webView.getSettings().setJavaScriptEnabled(true);
    }
}