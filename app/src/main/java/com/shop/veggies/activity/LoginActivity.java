package com.shop.veggies.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.shop.veggies.R;
import com.shop.veggies.model.UserModel;
import com.shop.veggies.utils.BSession;
import com.shop.veggies.utils.LocaleHelper;
import com.shop.veggies.utils.ProductConfig;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    Button btn_login;
    TextView lost;
    String mobile;
    EditText new_pass;
    String pass;
    String phone;
    ProgressDialog progressDialog;
    TextView show;
    LinearLayout signup_lin;
    EditText txt_mobile;
    UserModel userModel = new UserModel();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_login);
        ProgressDialog progressDialog2 = new ProgressDialog(this);
        this.progressDialog = progressDialog2;
        progressDialog2.setMessage("Logging in.....");
        LocaleHelper.setLocale(this, "fr");
        init();
        NetworkConnection();
    }

    public void init() {
        this.txt_mobile = (EditText) findViewById(R.id.txt_mobile);
        this.btn_login = (Button) findViewById(R.id.btn_login);
        this.new_pass = (EditText) findViewById(R.id.new_pass);
        this.show = (TextView) findViewById(R.id.show);
        this.lost = (TextView) findViewById(R.id.lost);
        this.signup_lin = (LinearLayout) findViewById(R.id.signup_lin);
       /* this.lost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, LostPasswordActivity.class));
            }
        });*/
        this.signup_lin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        this.show.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (LoginActivity.this.show.getText().toString().equals("Show")) {
                    LoginActivity.this.show.setText("Hide");
                    LoginActivity.this.new_pass.setTransformationMethod((TransformationMethod) null);
                    return;
                }
                LoginActivity.this.show.setText("Show");
                LoginActivity.this.new_pass.setTransformationMethod(new PasswordTransformationMethod());
            }
        });
        this.btn_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LoginActivity.this.btn_login.startAnimation(AnimationUtils.loadAnimation(LoginActivity.this.getApplicationContext(), R.anim.fade_in));
                LoginActivity loginActivity = LoginActivity.this;
                loginActivity.phone = loginActivity.txt_mobile.getText().toString();
                if (LoginActivity.this.phone.isEmpty()) {
                    LoginActivity.this.txt_mobile.setError("*Enter The Mobile Number");
                    LoginActivity.this.txt_mobile.requestFocus();
                } else if (LoginActivity.this.phone == null || LoginActivity.this.phone.length() < 10 || LoginActivity.this.phone.length() > 13) {
                    LoginActivity.this.txt_mobile.setError("*Enter Valid Mobile Number");
                    LoginActivity.this.txt_mobile.requestFocus();
                } else if (LoginActivity.this.phone == null || LoginActivity.this.phone == "") {
                    Toast.makeText(LoginActivity.this, "Please enter Mobile Number", Toast.LENGTH_SHORT).show();
                } else {
                    LoginActivity.this.sendOTP();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void sendOTP() {
        this.phone = this.txt_mobile.getText().toString();
        this.pass = this.new_pass.getText().toString();
        final Map<String, String> params = new HashMap<>();
        this.progressDialog.show();
        Volley.newRequestQueue(this).add(new StringRequest(0, ProductConfig.userlogin + ("?user_mobile=" + this.phone), new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    LoginActivity.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                        Toast.makeText(LoginActivity.this.getApplicationContext(), "Mobile number not exit", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ProductConfig.userModel = new UserModel(jsonResponse);
                    BSession.getInstance().initialize(LoginActivity.this, "", "", jsonResponse.getString("user_mobile"), "", "", "", "");
                    LoginActivity.this.mobile = jsonResponse.getString("user_mobile");
                    Intent intent = new Intent(LoginActivity.this, CheckOtpActivity.class);
                    intent.putExtra("user_mobile", LoginActivity.this.mobile);
                    LoginActivity.this.startActivity(intent);
                    LoginActivity.this.finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    LoginActivity.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                LoginActivity.this.progressDialog.dismiss();
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                return params;
            }
        });
    }

    private void NetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();

            if (info == null || !info.isConnected()) {
                Toast.makeText(this, "Please check your network connection and try again.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Unable to check network connection.", Toast.LENGTH_SHORT).show();
        }
    }
}
