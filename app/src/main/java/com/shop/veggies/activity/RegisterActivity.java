package com.shop.veggies.activity;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.shop.veggies.R;
import com.shop.veggies.model.UserModel;
import com.shop.veggies.utils.BSession;
import com.shop.veggies.utils.ProductConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity implements LocationListener {
    public static final int RequestPermissionCode = 1;
    String FcmToken;
    private int PICK_IMAGE_REQUEST = 1;
    private int REQUEST_CAMERA = 0;
    private int SELECT_FILE = 1;
    String address;
    Bitmap bitmap;
    CircleImageView capture_img;
    String customer_id = null;
    EditText edtMobile;
    EditText edtName;
    EditText edt_caddress;
    EditText edt_pass;
    EditText edt_referral;
    String email;
    Uri filePath;
    String latitude;
    LocationManager locationManager;
    String longitude;
    String message;
    String mobile;
    String name;
    String pass;
    String phone;
    String placeimg;
    ProgressDialog progressDialog;
    String referral = "0";
    Button register;
    TextView show;
    TextView tv_currentlocation;
    UserModel userModel = new UserModel();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_register);
        ((NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE)).cancel(0);
        try {
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Log.w("FCM Token", "Fetching FCM registration token failed", task.getException());
                            this.FcmToken = "";
                            return;
                        }

                        this.FcmToken = task.getResult();
                        Log.d("FCM Token", "Token: " + this.FcmToken);
                    });

        } catch (Exception e) {
            this.FcmToken = "";
        }
        ProgressDialog progressDialog2 = new ProgressDialog(this);
        this.progressDialog = progressDialog2;
        progressDialog2.setMessage("Registering.....");
        if (!(ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(getApplicationContext(), "android.permission.ACCESS_COARSE_LOCATION") == 0)) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 101);
        }
        getBundle();
        init();
        statusCheck();
    }

    private void getBundle() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.phone = extras.getString("user_mobile");
        }
    }

    public void init() {
        this.edtName = (EditText) findViewById(R.id.edt_name);
        this.edtMobile = (EditText) findViewById(R.id.edt_phno);
        this.edt_caddress = (EditText) findViewById(R.id.edt_caddress);
        this.edt_pass = (EditText) findViewById(R.id.edt_pass);
        this.edt_referral = (EditText) findViewById(R.id.edt_referral);
        this.edtMobile.setText(this.phone);
        this.register = (Button) findViewById(R.id.update_btn);
        TextView textView = (TextView) findViewById(R.id.tv_currentlocation);
        this.tv_currentlocation = textView;
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RegisterActivity.this.tv_currentlocation.startAnimation(AnimationUtils.loadAnimation(RegisterActivity.this.getApplicationContext(), R.anim.fade_in));
                RegisterActivity.this.progressDialog.setMessage("Searching.....");
                RegisterActivity.this.progressDialog.show();
                RegisterActivity.this.getLocation();
            }
        });
        EnableRuntimePermission();
        this.register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RegisterActivity.this.register.startAnimation(AnimationUtils.loadAnimation(RegisterActivity.this.getApplicationContext(), R.anim.fade_in));
                RegisterActivity registerActivity = RegisterActivity.this;
                registerActivity.name = registerActivity.edtName.getText().toString().trim();
                RegisterActivity registerActivity2 = RegisterActivity.this;
                registerActivity2.mobile = registerActivity2.edtMobile.getText().toString().trim();
                RegisterActivity registerActivity3 = RegisterActivity.this;
                registerActivity3.address = registerActivity3.edt_caddress.getText().toString();
                RegisterActivity registerActivity4 = RegisterActivity.this;
                registerActivity4.pass = registerActivity4.edt_pass.getText().toString();
                RegisterActivity registerActivity5 = RegisterActivity.this;
                registerActivity5.referral = registerActivity5.edt_referral.getText().toString().trim();
                if (RegisterActivity.this.name.isEmpty()) {
                    RegisterActivity.this.edtName.setError("*Enter your name");
                    RegisterActivity.this.edtName.requestFocus();
                } else if (RegisterActivity.this.mobile.isEmpty()) {
                    RegisterActivity.this.edtMobile.setError("*Enter your mobile number");
                    RegisterActivity.this.edtMobile.requestFocus();
                } else if (RegisterActivity.this.mobile == null || RegisterActivity.this.mobile.length() < 6 || RegisterActivity.this.mobile.length() > 13) {
                    RegisterActivity.this.edtMobile.setError("*Enter Valid Mobile Number");
                    RegisterActivity.this.edtMobile.requestFocus();
                } else if (RegisterActivity.this.address.isEmpty()) {
                    RegisterActivity.this.edt_caddress.setError("*Enter your address");
                    RegisterActivity.this.edt_caddress.requestFocus();
                } else if (RegisterActivity.this.name == null || RegisterActivity.this.name == "" || RegisterActivity.this.mobile == null || RegisterActivity.this.mobile == "" || RegisterActivity.this.address == null || RegisterActivity.this.address == "") {
                    Toast.makeText(RegisterActivity.this, "Please enter required information", Toast.LENGTH_SHORT).show();
                } else {
                    RegisterActivity.this.setRegister();
                }
            }
        });
    }

    public void EnableRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CAMERA")) {
            Toast.makeText(this, "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA"}, 1);
        }
    }

    public static Bitmap getScaledBitmap(Bitmap b, int reqWidth, int reqHeight) {
        Matrix m = new Matrix();
        m.setRectToRect(new RectF(0.0f, 0.0f, (float) b.getWidth(), (float) b.getHeight()), new RectF(0.0f, 0.0f, (float) reqWidth, (float) reqHeight), Matrix.ScaleToFit.CENTER);
        return Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ((BitmapDrawable) this.capture_img.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return Base64.encodeToString(baos.toByteArray(), 0);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), this.PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult parseActivityResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != this.SELECT_FILE || resultCode != -1 || data == null || data.getData() == null) {
            Toast.makeText(this, "Select Your Profile", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            this.bitmap = bitmap2;
            Log.e("img", String.valueOf(bitmap2));
            this.capture_img.setImageBitmap(getScaledBitmap(this.bitmap, 500, 500));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void setRegister() {
        final Map<String, String> params = new HashMap<>();
        Bitmap bitmap2 = this.bitmap;
        if (bitmap2 != null) {
            this.placeimg = getStringImage(getScaledBitmap(bitmap2, 500, 500));
        } else {
            this.placeimg = "0";
        }
        this.name = this.edtName.getText().toString().trim();
        this.mobile = this.edtMobile.getText().toString().trim();
        this.address = this.edt_caddress.getText().toString();
        this.progressDialog.show();
        StringRequest jsObjRequest = new StringRequest(1, ProductConfig.userregister + ("?user_mobile=" + this.mobile) + ("&user_name=" + this.name) + ("&user_address=" + this.address) + ("&profileimage=" + this.placeimg) + ("&notifyid=" + this.FcmToken) + ("&referral_id=" + this.edt_referral.getText().toString().trim()), new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    RegisterActivity.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                        RegisterActivity.this.message = jsonResponse.getString("message");
                        if (RegisterActivity.this.message.equalsIgnoreCase("Already Register customer_mobile!")) {
                            Toast.makeText(RegisterActivity.this.getApplicationContext(), "Mobile number already register", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this.getApplicationContext(), "Register Failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        RegisterActivity.this.userModel = new UserModel(jsonResponse);
                        BSession.getInstance().initialize(RegisterActivity.this, jsonResponse.getString("user_id"), jsonResponse.getString("user_name"), jsonResponse.getString("user_mobile"), jsonResponse.getString("user_address"), jsonResponse.getString("profileimage"), jsonResponse.getString("refer_id"), "");
                        RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, CheckoutActivity.class));
                        RegisterActivity.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    RegisterActivity.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                RegisterActivity.this.progressDialog.dismiss();
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                return params;
            }
        };
        Volley.newRequestQueue(this).add(jsObjRequest);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
    }

    public void statusCheck() {
        if (!((LocationManager) getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled("gps")) {
            buildAlertMessageNoGps();
        }
    }

    private void buildAlertMessageNoGps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, please enable it to continue?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                RegisterActivity.this.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    /* access modifiers changed from: package-private */
    public void getLocation() {
        this.progressDialog.show();
        try {
            LocationManager locationManager2 = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            this.locationManager = locationManager2;
            locationManager2.requestLocationUpdates("network", 8000, 5.0f, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void onLocationChanged(Location location) {
        try {
            List<Address> addresses = new Geocoder(this, Locale.getDefault()).getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            EditText editText = this.edt_caddress;
            editText.setText("" + addresses.get(0).getAddressLine(0));
            this.progressDialog.dismiss();
            this.latitude = String.valueOf(location.getLatitude());
            this.longitude = String.valueOf(location.getLongitude());
            Log.e("Latitude: ", this.latitude + " & Longitude: " + this.longitude);
        } catch (Exception e) {
        }
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    public void onProviderDisabled(String provider) {
    }
}
