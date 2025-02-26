package com.shop.veggies.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.shop.veggies.R;
import com.shop.veggies.model.UserModel;
import com.shop.veggies.utils.BSession;
import com.shop.veggies.utils.ProductConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReturnProductActivity extends AppCompatActivity {
    String email;
    EditText et_email;
    EditText et_message;
    EditText et_name;
    EditText et_orderid;
    EditText et_phone;
    String message;
    String mobile;
    String name;
    String orderid;
    ProgressDialog progressDialog;
    Button submit;
    UserModel userModel = new UserModel();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_return_product);
        ProgressDialog progressDialog2 = new ProgressDialog(this);
        this.progressDialog = progressDialog2;
        progressDialog2.setMessage("Sending feedback.....");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.orderid = extras.getString("orderid");
        }
        toolbar();
        this.et_name = (EditText) findViewById(R.id.et_name);
        this.et_phone = (EditText) findViewById(R.id.et_phone);
        this.et_email = (EditText) findViewById(R.id.et_email);
        this.et_orderid = (EditText) findViewById(R.id.et_orderid);
        String customer_name = BSession.getInstance().getUser_name(this);
        String customer_mobile = BSession.getInstance().getUser_mobile(this);
        String customer_email = BSession.getInstance().getUser_email(this);
        this.et_name.setText(customer_name);
        this.et_phone.setText(customer_mobile);
        this.et_email.setText(customer_email);
        this.et_orderid.setText(this.orderid);
        this.et_message = (EditText) findViewById(R.id.et_message);
        Button button = (Button) findViewById(R.id.submit_btn);
        this.submit = button;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ReturnProductActivity returnProductActivity = ReturnProductActivity.this;
                returnProductActivity.name = returnProductActivity.et_name.getText().toString().trim();
                ReturnProductActivity returnProductActivity2 = ReturnProductActivity.this;
                returnProductActivity2.mobile = returnProductActivity2.et_phone.getText().toString().trim();
                ReturnProductActivity returnProductActivity3 = ReturnProductActivity.this;
                returnProductActivity3.message = returnProductActivity3.et_message.getText().toString().trim();
                if (ReturnProductActivity.this.name.isEmpty()) {
                    ReturnProductActivity.this.et_name.setError("*Enter your name");
                    ReturnProductActivity.this.et_name.requestFocus();
                } else if (ReturnProductActivity.this.mobile.isEmpty()) {
                    ReturnProductActivity.this.et_phone.setError("*Enter your mobile number");
                    ReturnProductActivity.this.et_phone.requestFocus();
                } else if (ReturnProductActivity.this.message.isEmpty()) {
                    ReturnProductActivity.this.et_message.setError("*Enter your message");
                    ReturnProductActivity.this.et_message.requestFocus();
                } else {
                    if (!(ReturnProductActivity.this.name == null || ReturnProductActivity.this.name == "" || ReturnProductActivity.this.mobile == null)) {
                        boolean z = true;
                        boolean z2 = ReturnProductActivity.this.mobile != "";
                        if (ReturnProductActivity.this.message == null) {
                            z = false;
                        }
                        if ((z2 && z) && ReturnProductActivity.this.message != "") {
                            ReturnProductActivity.this.sendFeedback();
                            return;
                        }
                    }
                    Toast.makeText(ReturnProductActivity.this, "Please enter required information", 0).show();
                }
            }
        });
    }

    private void toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ReturnProductActivity.this.startActivity(new Intent(ReturnProductActivity.this.getApplicationContext(), MainActivity.class));
                ReturnProductActivity.this.finish();
            }
        });
        getSupportActionBar().setTitle((CharSequence) "Return Product");
        toolbar.setTitleTextColor(-1);
    }

    /* access modifiers changed from: private */
    public void sendFeedback() {
        String customer_id = BSession.getInstance().getUser_id(this);
        this.name = this.et_name.getText().toString().trim();
        this.mobile = this.et_phone.getText().toString().trim();
        this.message = this.et_message.getText().toString().trim();
        this.email = this.et_email.getText().toString().trim();
        final Map<String, String> params = new HashMap<>();
        this.progressDialog.show();
        String para_str2 = "?user_id=" + customer_id;
        Volley.newRequestQueue(this).add(new StringRequest(0, ProductConfig.returnpolicy + para_str2 + ("&user_mobile=" + this.mobile) + ("&user_name=" + this.name) + ("&message=" + this.message) + ("&user_address=" + this.email) + ("&order_id=" + this.orderid), new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    ReturnProductActivity.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                        Toast.makeText(ReturnProductActivity.this.getApplicationContext(), "Return Product submit failed", 1).show();
                        return;
                    }
                    Toast.makeText(ReturnProductActivity.this.getApplicationContext(), "Return Product sent", 1).show();
                    ReturnProductActivity.this.startActivity(new Intent(ReturnProductActivity.this, OrderDetailActivity.class));
                    ReturnProductActivity.this.finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    ReturnProductActivity.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                ReturnProductActivity.this.progressDialog.dismiss();
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                return params;
            }
        });
    }
}
