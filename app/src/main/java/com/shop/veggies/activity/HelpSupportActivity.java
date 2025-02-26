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

public class HelpSupportActivity extends AppCompatActivity {
    String email;
    EditText et_email;
    EditText et_message;
    EditText et_name;
    EditText et_phone;
    String message;
    String mobile;
    String name;
    ProgressDialog progressDialog;
    Button submit;
    UserModel userModel = new UserModel();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_help_support);
        ProgressDialog progressDialog2 = new ProgressDialog(this);
        this.progressDialog = progressDialog2;
        progressDialog2.setMessage("Sending feedback.....");
        toolbar();
        this.et_name = (EditText) findViewById(R.id.et_name);
        this.et_phone = (EditText) findViewById(R.id.et_phone);
        this.et_email = (EditText) findViewById(R.id.et_email);
        String customer_name = BSession.getInstance().getUser_name(this);
        String customer_mobile = BSession.getInstance().getUser_mobile(this);
        this.et_name.setText(customer_name);
        this.et_phone.setText(customer_mobile);
        this.et_message = (EditText) findViewById(R.id.et_message);
        Button button = (Button) findViewById(R.id.submit_btn);
        this.submit = button;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HelpSupportActivity helpSupportActivity = HelpSupportActivity.this;
                helpSupportActivity.name = helpSupportActivity.et_name.getText().toString().trim();
                HelpSupportActivity helpSupportActivity2 = HelpSupportActivity.this;
                helpSupportActivity2.mobile = helpSupportActivity2.et_phone.getText().toString().trim();
                HelpSupportActivity helpSupportActivity3 = HelpSupportActivity.this;
                helpSupportActivity3.message = helpSupportActivity3.et_message.getText().toString().trim();
                if (HelpSupportActivity.this.name.isEmpty()) {
                    HelpSupportActivity.this.et_name.setError("*Enter your name");
                    HelpSupportActivity.this.et_name.requestFocus();
                } else if (HelpSupportActivity.this.mobile.isEmpty()) {
                    HelpSupportActivity.this.et_phone.setError("*Enter your mobile number");
                    HelpSupportActivity.this.et_phone.requestFocus();
                } else if (HelpSupportActivity.this.mobile == null || HelpSupportActivity.this.mobile.length() < 10 || HelpSupportActivity.this.mobile.length() > 13) {
                    HelpSupportActivity.this.et_phone.setError("*Enter Valid Mobile Number");
                    HelpSupportActivity.this.et_phone.requestFocus();
                } else if (HelpSupportActivity.this.message.isEmpty()) {
                    HelpSupportActivity.this.et_message.setError("*Enter your message");
                    HelpSupportActivity.this.et_message.requestFocus();
                } else {
                    if (!(HelpSupportActivity.this.name == null || HelpSupportActivity.this.name == "" || HelpSupportActivity.this.mobile == null)) {
                        boolean z = true;
                        boolean z2 = HelpSupportActivity.this.mobile != "";
                        if (HelpSupportActivity.this.message == null) {
                            z = false;
                        }
                        if ((z2 && z) && HelpSupportActivity.this.message != "") {
                            HelpSupportActivity.this.sendFeedback();
                            return;
                        }
                    }
                    Toast.makeText(HelpSupportActivity.this, "Please enter required information", Toast.LENGTH_SHORT).show();
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
                HelpSupportActivity.this.startActivity(new Intent(HelpSupportActivity.this.getApplicationContext(), MainActivity.class));
                HelpSupportActivity.this.finish();
            }
        });
        getSupportActionBar().setTitle((CharSequence) "Support");
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
        Volley.newRequestQueue(this).add(new StringRequest(0, ProductConfig.feedback + ("?user_mobile=" + this.mobile) + ("&user_name=" + this.name) + ("&user_id=" + customer_id) + ("&message=" + this.message) + ("&user_email=" + this.email), new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    HelpSupportActivity.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                        Toast.makeText(HelpSupportActivity.this.getApplicationContext(), "Feedback submit failed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(HelpSupportActivity.this.getApplicationContext(), "Feedback sent", Toast.LENGTH_SHORT).show();
                    HelpSupportActivity.this.startActivity(new Intent(HelpSupportActivity.this, MainActivity.class));
                    HelpSupportActivity.this.finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    HelpSupportActivity.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                HelpSupportActivity.this.progressDialog.dismiss();
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                return params;
            }
        });
    }
}
