package com.shop.veggies.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.shop.veggies.R;
import com.shop.veggies.adapter.OfferAdapter;
import com.shop.veggies.model.ProductsModel;
import com.shop.veggies.model.WeightModel;
import com.shop.veggies.utils.BSession;
import com.shop.veggies.utils.ProductConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfferActivity extends AppCompatActivity {
    TextView badge_notification;
    String baseUrl;
    String customer_id = null;
    String deviceid;
    String ipaddress;
    LinearLayoutManager linearLayoutManager;
    RecyclerView mRecyclerView;
    OfferAdapter offerAdapter;
    String para_str;
    ProductsModel productsModel = new ProductsModel();
    List<ProductsModel> productsModelList = new ArrayList();
    ProgressDialog progressDialog;
    String role;
    String user_id = "";

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_offer);
        ProgressDialog progressDialog2 = new ProgressDialog(this);
        this.progressDialog = progressDialog2;
        progressDialog2.setMessage("Loading.....");
        this.deviceid = Settings.Secure.getString(getContentResolver(), "android_id");
        this.user_id = BSession.getInstance().getUser_id(this);
        toolbar();
        getCartCount();
        this.mRecyclerView = (RecyclerView) findViewById(R.id.rvItemList);
        setRecyclerview();
    }


    /* access modifiers changed from: package-private */
    public String GetDeviceipMobileData() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                Enumeration<InetAddress> enumIpAddr = en.nextElement().getInetAddresses();
                while (true) {
                    if (enumIpAddr.hasMoreElements()) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            return inetAddress.getHostAddress().toString();
                        }
                    }
                }
            }
            return null;
        } catch (Exception ex) {
            Log.e("Current IP", ex.toString());
            return null;
        }
    }



    private void toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                OfferActivity.this.startActivity(new Intent(OfferActivity.this.getApplicationContext(), MainActivity.class));
                OfferActivity.this.finish();
            }
        });
        getSupportActionBar().setTitle((CharSequence) "Offers");
        toolbar.setTitleTextColor(-1);
    }

    public void getCartCount() {
        final Map<String, String> params = new HashMap<>();
        this.customer_id = BSession.getInstance().getUser_id(this);
        String user_id2 = BSession.getInstance().getUser_id(this);
        this.customer_id = user_id2;
        if (user_id2.equalsIgnoreCase("")) {
            this.para_str = "?ip_address=" + this.deviceid;
            this.baseUrl = ProductConfig.cartcount + this.para_str;
        } else {
            this.para_str = "?user_id=" + this.customer_id;
            this.baseUrl = ProductConfig.usercartcount + this.para_str;
        }
        Volley.newRequestQueue(this).add(new StringRequest(0, this.baseUrl, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        OfferActivity.this.badge_notification.setText("0");
                    } else {
                        OfferActivity.this.badge_notification.setText(jsonResponse.getString("count"));
                    }
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
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_cart1);
        View actionView = MenuItemCompat.getActionView(menuItem);
        this.badge_notification = (TextView) actionView.findViewById(R.id.badge_notification);
        actionView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OfferActivity.this.onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.action_cart1) {
            return super.onOptionsItemSelected(item);
        }
        startActivity(new Intent(this, CartActivity.class));
        return true;
    }

    private void setRecyclerview() {
        this.productsModelList = new ArrayList();
        this.progressDialog.show();
        final Map<String, String> params = new HashMap<>();
        String user_id2 = BSession.getInstance().getUser_id(this);
        this.customer_id = user_id2;
        if (user_id2.equalsIgnoreCase("")) {
            this.para_str = "?ip_address=" + this.deviceid;
            this.baseUrl = ProductConfig.offerlist + this.para_str;
        } else {
            this.para_str = "?user_id=" + this.customer_id;
            this.baseUrl = ProductConfig.userofferlist + this.para_str;
        }
        Volley.newRequestQueue(this).add(new StringRequest(0, this.baseUrl, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    OfferActivity.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        Toast.makeText(OfferActivity.this.getApplicationContext(), "No offer Result Found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    OfferActivity.this.productsModelList = new ArrayList();
                    JSONArray array = jsonResponse.getJSONArray("storeList");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jSONObject = array.getJSONObject(i);
                        ProductsModel model = new ProductsModel();
                        model.setProduct_id(array.getJSONObject(i).getString("pid"));
                        model.setCid(array.getJSONObject(i).getString("cid"));
                        model.setScid(array.getJSONObject(i).getString("scid"));
                        model.setProduct_name(array.getJSONObject(i).getString("productname"));
                        model.setOffer_status(array.getJSONObject(i).getString("offerstatus"));
                        model.setProduct_offer(array.getJSONObject(i).getString("offerpercentage"));
                        model.setProduct_image(array.getJSONObject(i).getString("productimage"));
                        model.setProduct_quantity(array.getJSONObject(i).getString("qty"));
                        model.setProduct_desc(array.getJSONObject(i).getString("description"));
                        model.setProduct_status(array.getJSONObject(i).getString("product_status"));
                        JSONArray weightArr = array.getJSONObject(i).getJSONArray("list");
                        if (weightArr == null || weightArr.length() <= 0) {
                            List<WeightModel> weightModelList = new ArrayList<>();
                            WeightModel weightModel = new WeightModel();
                            weightModel.setWid("0");
                            weightModel.setWeb_price("0");
                            weightModel.setWeb_title("0");
                            weightModel.setMrp("0");
                            weightModelList.add(weightModel);
                            model.setWeight(weightModelList);
                            OfferActivity.this.productsModelList.add(model);
                        } else {
                            List<WeightModel> weightModelList2 = new ArrayList<>();
                            for (int j = 0; j < weightArr.length(); j++) {
                                JSONObject jSONObject2 = weightArr.getJSONObject(j);
                                WeightModel weightModel2 = new WeightModel();
                                weightModel2.setWid(weightArr.getJSONObject(j).getString("vid"));
                                weightModel2.setWeb_price(weightArr.getJSONObject(j).getString(FirebaseAnalytics.Param.PRICE));
                                weightModel2.setWeb_title(weightArr.getJSONObject(j).getString("weight"));
                                weightModel2.setMrp(weightArr.getJSONObject(j).getString("mrp"));
                                weightModelList2.add(weightModel2);
                            }
                            model.setWeight(weightModelList2);
                            OfferActivity.this.productsModelList.add(model);
                        }
                    }
                    OfferActivity.this.offerAdapter = new OfferAdapter(OfferActivity.this, OfferActivity.this.productsModelList);
                    OfferActivity.this.mRecyclerView.setHasFixedSize(true);
                    OfferActivity.this.linearLayoutManager = new LinearLayoutManager(OfferActivity.this, RecyclerView.VERTICAL, false);
                    OfferActivity.this.mRecyclerView.setLayoutManager(OfferActivity.this.linearLayoutManager);
                    OfferActivity.this.mRecyclerView.setAdapter(OfferActivity.this.offerAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    OfferActivity.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                OfferActivity.this.progressDialog.dismiss();
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                return params;
            }
        });
    }
}
