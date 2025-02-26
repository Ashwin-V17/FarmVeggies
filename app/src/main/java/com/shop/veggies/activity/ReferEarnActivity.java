package com.shop.veggies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.shop.veggies.R;
import com.shop.veggies.utils.BSession;

public class ReferEarnActivity extends AppCompatActivity {
    Button btn_refer;
    TextView refer_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_earn);
        setupToolbar();

        refer_code = findViewById(R.id.refer_code);
        btn_refer = findViewById(R.id.btn_refer);

        final String refer = BSession.getInstance().getRefer_id(getApplicationContext());
        refer_code.setText("Refer Code: " + refer);

        btn_refer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");  // Fixed ContentType issue
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Refer Code: " + refer);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "\nRefer code: " + refer + "\n\n"
                            + "https://play.google.com/store/apps/details?id=" + getPackageName());

                    startActivity(Intent.createChooser(shareIntent, "Choose one"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Refer & Earn");
        }

        toolbar.setNavigationOnClickListener(view -> {
            startActivity(new Intent(ReferEarnActivity.this, MainActivity.class));
            finish();
        });
    }
}
