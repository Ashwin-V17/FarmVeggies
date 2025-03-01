package com.shop.veggies.activity;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.shop.veggies.R;
import com.shop.veggies.model.UserModel;
import com.shop.veggies.utils.BSession;
import com.shop.veggies.utils.LocaleHelper;
import com.shop.veggies.utils.ProductConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Button btn_login;
    TextView lost, registerText;
    String mobile;
    EditText new_pass;
    String pass;
    String phone;
    ProgressDialog progressDialog;
    TextView show;
    EditText txt_mobile;
    UserModel userModel = new UserModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in.....");

        LocaleHelper.setLocale(this, "fr");

        init();
        checkNetworkConnection();
    }

    public void init() {
        txt_mobile = findViewById(R.id.txt_mobile);
        btn_login = findViewById(R.id.btn_login);
        new_pass = findViewById(R.id.new_pass);
        show = findViewById(R.id.show);
        lost = findViewById(R.id.lost);
        registerText = findViewById(R.id.register_text); // Added register text

        // Navigate to RegisterActivity when clicked
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (show.getText().toString().equals("Show")) {
                    show.setText("Hide");
                    new_pass.setTransformationMethod((TransformationMethod) null);
                } else {
                    show.setText("Show");
                    new_pass.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                btn_login.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in));
                phone = txt_mobile.getText().toString();

                if (phone.isEmpty()) {
                    txt_mobile.setError("*Enter The Mobile Number");
                    txt_mobile.requestFocus();
                } else if (phone.length() < 10 || phone.length() > 13) {
                    txt_mobile.setError("*Enter Valid Mobile Number");
                    txt_mobile.requestFocus();
                } else {
                    sendOTP();
                }
            }
        });
    }

//    private void sendOTP() {
//        phone = txt_mobile.getText().toString();
//        pass = new_pass.getText().toString();
//        final Map<String, String> params = new HashMap<>();
//
//        progressDialog.show();
//        Volley.newRequestQueue(this).add(new StringRequest(0, ProductConfig.userlogin + ("?user_mobile=" + phone), new Response.Listener<String>() {
//            public void onResponse(String response) {
//                Log.e("Response", response.toString());
//                try {
//                    progressDialog.dismiss();
//                    JSONObject jsonResponse = new JSONObject(response);
//                    if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
//                        Toast.makeText(getApplicationContext(), "Mobile number not found", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    ProductConfig.userModel = new UserModel(jsonResponse);
//                    BSession.getInstance().initialize(LoginActivity.this, "", "", jsonResponse.getString("user_mobile"), "", "", "", "");
//                    mobile = jsonResponse.getString("user_mobile");
//
//                    Intent intent = new Intent(LoginActivity.this, CheckOtpActivity.class);
//                    intent.putExtra("user_mobile", mobile);
//                    startActivity(intent);
//                    finish();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    progressDialog.dismiss();
//                }
//            }
//        }, new Response.ErrorListener() {
//            public void onErrorResponse(VolleyError error) {
//                Log.e("Error", error.toString());
//                progressDialog.dismiss();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                return params;
//            }
//        });
//    }
private void sendOTP() {
    phone = txt_mobile.getText().toString();
    pass = new_pass.getText().toString();

    // Simulating successful verification without making an API call
    mobile = phone; // Use entered mobile number as a placeholder

    Intent intent = new Intent(LoginActivity.this, CheckOtpActivity.class);
    intent.putExtra("user_mobile", mobile);
    startActivity(intent);
    finish();

    final Map<String, String> params = new HashMap<>();
    if (!LoginActivity.this.isFinishing()) {
        progressDialog.show();
    }
    Volley.newRequestQueue(this).add(new StringRequest(0, ProductConfig.userlogin + ("?user_mobile=" + phone), response -> {
        Log.e("Response", response.toString());
        try {
            progressDialog.dismiss();
            JSONObject jsonResponse = new JSONObject(response);
            if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                Toast.makeText(getApplicationContext(), "Mobile number not found", Toast.LENGTH_SHORT).show();
                return;
            }
            ProductConfig.userModel = new UserModel(jsonResponse);
            BSession.getInstance().initialize(LoginActivity.this, "", "", jsonResponse.getString("user_mobile"), "", "", "", "");
            mobile = jsonResponse.getString("user_mobile");

            Intent intent1 = new Intent(LoginActivity.this, CheckOtpActivity.class);
            intent1.putExtra("user_mobile", mobile);
            startActivity(intent1);
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
            progressDialog.dismiss();
        }
    }, new Response.ErrorListener() {
        public void onErrorResponse(VolleyError error) {
            if (progressDialog != null && progressDialog.isShowing()) {
                if (!LoginActivity.this.isFinishing()) {
                    progressDialog.dismiss();
                }
            }
            Log.e("Volley Error", error.toString());
        }
    }) {
        @Override
        protected Map<String, String> getParams() {
            return params;
        }
    });

}

    private void checkNetworkConnection() {
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
