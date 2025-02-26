package com.shop.veggies.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.shop.veggies.R;
import com.shop.veggies.model.UserModel;
import com.shop.veggies.utils.BSession;
import com.shop.veggies.utils.ProductConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import android.content.Context;
public class ProfileActivity extends AppCompatActivity implements LocationListener {
    public static final int RequestPermissionCode = 1;
    private int PICK_IMAGE_REQUEST = 1;
    private int REQUEST_CAMERA = 0;
    private int SELECT_FILE = 1;
    String address;
    Bitmap bitmap;
    String customer_id;
    EditText edt_caddress;
    EditText edt_dob;
    EditText edt_email;
    EditText edt_mobile;
    EditText edt_name;
    String email;
    Uri filePath;
    String latitude;
    LocationManager locationManager;
    String longitude;
    String mobile;
    String name;
    String placeimg;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    TextView tv_currentlocation;
    Button update;
    UserModel userModel = new UserModel();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_profile);
        ProgressDialog progressDialog2 = new ProgressDialog(this);
        this.progressDialog = progressDialog2;
        progressDialog2.setMessage("Updating.....");
        if (!(ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(getApplicationContext(), "android.permission.ACCESS_COARSE_LOCATION") == 0)) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 101);
        }
        toolbar();
        init();
        statusCheck();
    }

    public void init() {
        EditText editText = (EditText) findViewById(R.id.edt_dob);
        this.edt_dob = editText;
        editText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar cldr = Calendar.getInstance();
                int day = cldr.get(5);
                int month = cldr.get(2);
                DatePickerDialog picker = new DatePickerDialog(ProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String str = VolleyLog.TAG;
                        Log.d(str, "dob--" + dayOfMonth);
                        EditText editText = ProfileActivity.this.edt_dob;
                        editText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, cldr.get(1), month, day);
                cldr.setTimeInMillis(System.currentTimeMillis() - 1000);
                picker.show();
            }
        });
        this.edt_caddress = (EditText) findViewById(R.id.edt_caddress);
        TextView textView = (TextView) findViewById(R.id.tv_currentlocation);
        this.tv_currentlocation = textView;
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ProfileActivity.this.progressDialog.show();
                ProfileActivity.this.getLocation();
            }
        });
        String customer_name = BSession.getInstance().getUser_name(this);
        String customer_mobile = BSession.getInstance().getUser_mobile(this);
        String customer_address = BSession.getInstance().getUser_address(this);
        String profileimage = BSession.getInstance().getProfileimage(this);
        this.edt_name = (EditText) findViewById(R.id.et_cname);
        this.edt_mobile = (EditText) findViewById(R.id.et_cphone);
        this.edt_caddress = (EditText) findViewById(R.id.edt_caddress);
        this.edt_name.setText(customer_name);
        this.edt_mobile.setText(customer_mobile);
        this.edt_caddress.setText(customer_address);
        Button button = (Button) findViewById(R.id.update_btn);
        this.update = button;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileActivity.this.update.startAnimation(AnimationUtils.loadAnimation(ProfileActivity.this.getApplicationContext(), R.anim.fade_in));
                ProfileActivity profileActivity = ProfileActivity.this;
                profileActivity.name = profileActivity.edt_name.getText().toString().trim();
                ProfileActivity profileActivity2 = ProfileActivity.this;
                profileActivity2.mobile = profileActivity2.edt_mobile.getText().toString().trim();
                ProfileActivity profileActivity3 = ProfileActivity.this;
                profileActivity3.address = profileActivity3.edt_caddress.getText().toString().trim();
                if (ProfileActivity.this.name.isEmpty()) {
                    ProfileActivity.this.edt_name.setError("*Enter your name");
                    ProfileActivity.this.edt_name.requestFocus();
                } else if (ProfileActivity.this.mobile.isEmpty()) {
                    ProfileActivity.this.edt_mobile.requestFocus();
                } else if (ProfileActivity.this.address.isEmpty()) {
                    ProfileActivity.this.edt_caddress.setError("*Enter your address");
                    ProfileActivity.this.edt_caddress.requestFocus();
                } else {
                    if (!(ProfileActivity.this.name == null || ProfileActivity.this.name == "" || ProfileActivity.this.mobile == null)) {
                        boolean z = false;
                        boolean z2 = ProfileActivity.this.mobile != "";
                        if (ProfileActivity.this.address != null) {
                            z = true;
                        }
                        if ((z2 && z) && ProfileActivity.this.address != "") {
                            ProfileActivity.this.updateProfile();
                            return;
                        }
                    }
                    Toast.makeText(ProfileActivity.this, "Please enter your details!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void updateProfile() {
        Map<String, String> params = new HashMap<>();
        this.customer_id = BSession.getInstance().getUser_id(this);
        params.put("user_mobile", this.mobile);
        params.put("user_name", this.name);
        params.put("user_address", this.address);
        params.put("profileimage", "0");
        params.put("user_id", this.customer_id);
        PrintStream printStream = System.out;
        printStream.println("id---" + this.customer_id);
        this.progressDialog.show();
        final Map<String, String> map = params;
        StringRequest jsObjRequest = new StringRequest(1, ProductConfig.userprofileupdate, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    ProfileActivity.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                        Toast.makeText(ProfileActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ProductConfig.userModel = new UserModel(jsonResponse);
                    BSession.getInstance().initialize(ProfileActivity.this, jsonResponse.getString("user_id"), jsonResponse.getString("user_name"), jsonResponse.getString("user_mobile"), jsonResponse.getString("user_address"), jsonResponse.getString("profileimage"), "", "");
                    ProfileActivity.this.startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                    ProfileActivity.this.finish();
                    Toast.makeText(ProfileActivity.this, "Your profile has been updated", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    ProfileActivity.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                ProfileActivity.this.progressDialog.dismiss();
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                return map;
            }
        };
        Volley.newRequestQueue(this).add(jsObjRequest);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
    }

    private void toolbar() {
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar);
        this.toolbar = toolbar2;
        setSupportActionBar(toolbar2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileActivity.this.startActivity(new Intent(ProfileActivity.this.getApplicationContext(), MainActivity.class));
                ProfileActivity.this.finish();
            }
        });
        getSupportActionBar().setTitle((CharSequence) "Profile");
        this.toolbar.setTitleTextColor(-1);
    }

    public void statusCheck() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }


    private void buildAlertMessageNoGps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, please enable it to continue?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ProfileActivity.this.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
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
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                this.locationManager = locationManager;
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        8000,
                        5.0f,
                        this
                );
            }
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
            this.tv_currentlocation.setVisibility(View.GONE);
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
