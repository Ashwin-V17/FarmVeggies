package com.shop.veggies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.shop.veggies.R;

public class AboutausActivity extends AppCompatActivity {
    TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutaus);

        setupToolbar();

        version = findViewById(R.id.version_name);
        version.setText("version : 1.6");

        WebView webView = findViewById(R.id.webView_about);
        String aboutUsHtml = "<html><body style=\"text-align:justify; color:#5e5555;\">"
                + "<h3>farmveggies.co.in is an online grocery store catering to the needs of people of Selaiyur, Chennai-600073</h3>"
                + "<p>Started in 2022, with a vision of giving an e-Advantage to renowned products even to the southern urban places of Chennai. "
                + "It currently lists more than 100 farm-fresh products of reputation which are delivered to the doorsteps of more than 200 customers...</p>"
                + "<h3>Wholesale Orders:</h3>"
                + "<p>We can deliver wholesale orders. For making wholesale or bulk purchases, please send us your order list to the email farmveggies2022@gmail.com. "
                + "We will send the details & price and process the order.</p>"
                + "</body></html>";

        webView.loadDataWithBaseURL(null, aboutUsHtml, "text/html", "utf-8", null);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("About Us");
        }

        toolbar.setTitleTextColor(-1);
        toolbar.setNavigationOnClickListener(view -> {
            startActivity(new Intent(AboutausActivity.this, MainActivity.class));
            finish();
        });
    }
}
