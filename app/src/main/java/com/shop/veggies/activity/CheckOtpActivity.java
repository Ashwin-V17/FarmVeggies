package com.shop.veggies.activity;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.shop.veggies.R;
import com.shop.veggies.model.UserModel;
import com.shop.veggies.utils.BSession;
import com.shop.veggies.utils.ProductConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CheckOtpActivity extends AppCompatActivity implements View.OnClickListener {
    private String FcmToken;
    private String deviceid;
    private String otpStr;
    private String phone;

    private ProgressDialog progressDialog;
    private Button resend, validateButton;
    private TextView texttimer, txt_mobile;
    private TextInputEditText otpInput;

    private UserModel userModel = new UserModel();
    private Boolean sendOTP = false;
    private CountDownTimer startTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_checkotp);
        getBundle();
        phone = BSession.getInstance().getUser_mobile(this);
        deviceid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        ((NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE)).cancel(0);

        // Get FCM Token


        initializeUi();
        setListeners();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Verifying.....");
    }

    private void getBundle() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            phone = extras.getString("user_mobile");
        } else {
            Toast.makeText(this, "Phone Number Not Available", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    private void initializeUi() {
        otpInput = findViewById(R.id.otp_edit_text);
        validateButton = findViewById(R.id.btn_validate);
        resend = findViewById(R.id.btn_resend);
        txt_mobile = findViewById(R.id.txt_mobile);
        texttimer = findViewById(R.id.timer);

        txt_mobile.setText("Enter SMS verification code sent to " + phone);
    }

    private void setListeners() {
        validateButton.setOnClickListener(this);
        resend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_validate) {
            otpStr = otpInput.getText().toString().trim();
            if (!otpStr.isEmpty()) {
                checkOTP();
            } else {
                Toast.makeText(this, "Please enter OTP", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.btn_resend) {
            Toast.makeText(this, "Code sent again", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkOTP() {
        if (!CheckOtpActivity.this.isFinishing()) {
            progressDialog.show();  // Show only if the activity is running
        }

        String baseUrl = ProductConfig.userotpverify +
                "?user_mobile=" + phone +
                "&otp=" + otpStr +
                "&ip_address=" + deviceid +
                "&notifyid=" + FcmToken;

        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, baseUrl,
                response -> {
                    Log.e("Response", response);
                    if (!CheckOtpActivity.this.isFinishing() && progressDialog.isShowing()) {
                        progressDialog.dismiss();  // Dismiss safely
                    }
                    try {
                        JSONObject jsonResponse = new JSONObject(response);

                        if (jsonResponse.has("status")) {
                            if (jsonResponse.getString("status").equals("0")) {
                                ProductConfig.userModel = new UserModel(jsonResponse);
                                BSession.getInstance().initialize(CheckOtpActivity.this,
                                        jsonResponse.getString("user_id"),
                                        "",
                                        jsonResponse.getString("user_mobile"),
                                        "", "", "", "");

                                Intent i = new Intent(CheckOtpActivity.this, RegisterActivity.class);
                                i.putExtra("user_mobile", phone);
                                startActivity(i);
                                finish();

                            } else if (jsonResponse.getString("status").equals("1")) {
                                BSession.getInstance().initialize(CheckOtpActivity.this,
                                        jsonResponse.getString("user_id"),
                                        jsonResponse.getString("user_name"),
                                        jsonResponse.getString("user_mobile"),
                                        "", "", "", "");

                                Intent i = new Intent(CheckOtpActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.e("Error", error.toString());
                    if (!CheckOtpActivity.this.isFinishing() && progressDialog.isShowing()) {
                        progressDialog.dismiss();  // Dismiss safely
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsObjRequest);
    }

}
