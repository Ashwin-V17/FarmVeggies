package com.shop.veggies.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.shop.veggies.R;
import com.shop.veggies.adapter.LocationAdapter;
import com.shop.veggies.model.CategoryModel;
import com.shop.veggies.model.ZipcodeModel;
import com.shop.veggies.utils.BSession;
import com.shop.veggies.utils.Pincode;
import com.shop.veggies.utils.ProductConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseYourLocationActivity extends AppCompatActivity {
    List<ZipcodeModel> apiZipcodeList = new ArrayList();
    private List<CategoryModel> categoryModelList = new ArrayList();
    GridView gv_locations;
    String ipaddress;
    String pin;
    ProgressDialog progressDialog;
    TextView txt;
    ZipcodeModel zipcodeModel = new ZipcodeModel();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_choose_your_location);
        ProgressDialog progressDialog2 = new ProgressDialog(this);
        this.progressDialog = progressDialog2;
        progressDialog2.setMessage("Loading.....");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.pin = extras.getString("pin");
        }
        this.gv_locations = (GridView) findViewById(R.id.gv_locations);
        setpincode();
        toolbar();
    }

    private void toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ChooseYourLocationActivity.this.startActivity(new Intent(ChooseYourLocationActivity.this.getApplicationContext(), MainActivity.class));
                ChooseYourLocationActivity.this.finish();
            }
        });
        getSupportActionBar().setTitle((CharSequence) "Choose Your Location");
        toolbar.setTitleTextColor(-1);
    }

    private void setpincode() {
        final Map<String, String> params = new HashMap<>();
        this.zipcodeModel.setAction("get_pincode");
        Volley.newRequestQueue(this).add(new StringRequest(1, ProductConfig.pincode, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    ChooseYourLocationActivity.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        Toast.makeText(ChooseYourLocationActivity.this, "No pincode records", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ChooseYourLocationActivity.this.apiZipcodeList = new ArrayList();
                    JSONArray jsonlist = jsonResponse.getJSONArray("storeList");
                    for (int j = 0; j < jsonlist.length(); j++) {
                        JSONObject jsonlistObject = jsonlist.getJSONObject(j);
                        ZipcodeModel zipcodeModel = new ZipcodeModel();
                        zipcodeModel.setId(jsonlistObject.getString("pincode_id").toString());
                        zipcodeModel.setZipcode(jsonlistObject.getString("web_pincode").toString());
                        zipcodeModel.setPrice(jsonlistObject.getString("web_price").toString());
                        zipcodeModel.setPincodeprice(jsonlistObject.getString("web_pincodeprice").toString());
                        ChooseYourLocationActivity.this.apiZipcodeList.add(zipcodeModel);
                        ChooseYourLocationActivity.this.gv_locations.setAdapter(new LocationAdapter(ChooseYourLocationActivity.this, R.layout.location_list_items, ChooseYourLocationActivity.this.apiZipcodeList, ChooseYourLocationActivity.this.pin));
                        ChooseYourLocationActivity.this.gv_locations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                String zip = ChooseYourLocationActivity.this.apiZipcodeList.get(position).getZipcode();
                                if (ChooseYourLocationActivity.this.pin.equalsIgnoreCase("1")) {
                                    ChooseYourLocationActivity.this.progressDialog.show();
                                    String customer_id = BSession.getInstance().getUser_id(ChooseYourLocationActivity.this);
                                    final Map<String, String> params = new HashMap<>();
                                    Volley.newRequestQueue(ChooseYourLocationActivity.this).add(new StringRequest(0, ProductConfig.userpincode + ("?user_id=" + customer_id) + ("&user_pincode=" + zip), new Response.Listener<String>() {
                                        public void onResponse(String response) {
                                            Log.e("Response", response.toString());
                                            try {
                                                JSONObject jsonResponse = new JSONObject(response);
                                                if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                                    ChooseYourLocationActivity.this.progressDialog.dismiss();
                                                    return;
                                                }
                                                Pincode.getInstance().initialize(ChooseYourLocationActivity.this, jsonResponse.getString("user_pincode"), "");
                                                ChooseYourLocationActivity.this.progressDialog.dismiss();
                                                ChooseYourLocationActivity.this.startActivity(new Intent(ChooseYourLocationActivity.this, CheckoutActivity.class));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        public void onErrorResponse(VolleyError error) {
                                            Log.e("Error", error.toString());
                                        }
                                    }) {
                                        /* access modifiers changed from: protected */
                                        public Map<String, String> getParams() {
                                            return params;
                                        }
                                    });
                                    return;
                                }
                                ChooseYourLocationActivity.this.progressDialog.show();
                                String customer_id2 = BSession.getInstance().getUser_id(ChooseYourLocationActivity.this);
                                final Map<String, String> params2 = new HashMap<>();
                                Volley.newRequestQueue(ChooseYourLocationActivity.this).add(new StringRequest(0, ProductConfig.userpincode + ("?user_id=" + customer_id2) + ("&user_pincode=" + zip), new Response.Listener<String>() {
                                    public void onResponse(String response) {
                                        Log.e("Response", response.toString());
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                                ChooseYourLocationActivity.this.progressDialog.dismiss();
                                                return;
                                            }
                                            Pincode.getInstance().initialize(ChooseYourLocationActivity.this, jsonResponse.getString("user_pincode"), "");
                                            ChooseYourLocationActivity.this.progressDialog.dismiss();
                                            ChooseYourLocationActivity.this.startActivity(new Intent(ChooseYourLocationActivity.this, MainActivity.class));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    public void onErrorResponse(VolleyError error) {
                                        Log.e("Error", error.toString());
                                    }
                                }) {
                                    /* access modifiers changed from: protected */
                                    public Map<String, String> getParams() {
                                        return params2;
                                    }
                                });
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ChooseYourLocationActivity.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                ChooseYourLocationActivity.this.progressDialog.dismiss();
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                return params;
            }
        });
    }
}
