package com.shop.veggies.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.shop.veggies.R;

public class ShippingandRetrun extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    TextView txt;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_shippingand_retrun);
        this.txt = (TextView) findViewById(R.id.txt);
        toolbar();
        if (Build.VERSION.SDK_INT >= 24) {
            this.txt.setText(Html.fromHtml("<h5> <font color='#5e5555'> Kirshnan Stores Delivery & Returns Policy <br/></h5><p>Cancellation of Order</p>\n<p>&nbsp;</p>\n<p>You can cancel your order at any time before we process and send the \"Processing\" email to you (usually within 4-5 hours of placing the order). Cancellation after sending the \"Processing\" email is not acceptable due to any reason.</p>\n<p>&nbsp;</p>\n<p>Refund policy</p>\n<p>&nbsp;</p>\n<p>In case of returns being taken as per our returns policy, for cash on delivery, we will return the money for the returned goods at the time of delivery itself. In case of credit/debit card payments we will credit the money back to your credit/debit card or Net banking which takes upto 8 to 10 working days to reflect in your statement. We will not be giving any cash refunds for purchase done using credit/debit card payments. Please contact customer support for further clarifications.</p>\n<p>&nbsp;</p>\n<p>&nbsp;</p>\n<p>Product Defects / Returns policy</p>\n<p>&nbsp;</p>\n<p>Please check the goods on delivery and ensure that they are supplied correctly. Goods sold will not be taken back unless the product is damaged, expired, or faulty during delivery time.If any of the goods prove to be defect, please return the same at the time of delivery. The item needs to be in its original unused condition and packaging, with any labels intact, then we will issue a full refund for the price you paid for the item.</p>", 63));
        } else {
            this.txt.setText(Html.fromHtml("<h2>Title</h2><br><p>Description here</p>"));
        }
    }

    private void toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ShippingandRetrun.this.startActivity(new Intent(ShippingandRetrun.this.getApplicationContext(), MainActivity.class));
                ShippingandRetrun.this.finish();
            }
        });
        getSupportActionBar().setTitle((CharSequence) "Delivery & Returns");
        toolbar.setTitleTextColor(-1);
    }
}
