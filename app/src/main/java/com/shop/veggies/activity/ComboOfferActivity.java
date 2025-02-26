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
import androidx.core.app.NotificationCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.shop.veggies.R;
import com.shop.veggies.adapter.ComboOfferAdapter;
import com.shop.veggies.model.ProductsModel;
import com.shop.veggies.model.WeightModel;
import com.shop.veggies.utils.BSession;
import com.shop.veggies.utils.ProductConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboOfferActivity extends AppCompatActivity {
    TextView badge_notification;
    String baseUrl;
    ComboOfferAdapter comboOfferAdapter;
    String customer_id = null;
    String deviceid;

    /* renamed from: id */
    String f165id;
    String ipaddress;
    LinearLayoutManager linearLayoutManager;
    RecyclerView mRecyclerView;
    String page;
    String para_str;
    String para_str1;
    ProductsModel productsModel = new ProductsModel();
    List<ProductsModel> productsModelList = new ArrayList();
    ProgressDialog progressDialog;
    String role;
    String user_id = "";

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_combo_offer);
        ProgressDialog progressDialog2 = new ProgressDialog(this);
        this.progressDialog = progressDialog2;
        progressDialog2.setMessage("Loading.....");
        this.deviceid = Settings.Secure.getString(getContentResolver(), "android_id");
        this.user_id = BSession.getInstance().getUser_id(this);
        toolbar();
        getBundle();
        getCartCount();
        this.mRecyclerView = (RecyclerView) findViewById(R.id.rvItemList);
    }

    private void getBundle() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.page = extras.getString("page");
            if (this.page.contentEquals("brand")) {
                setBandList();
            } else if (this.page.contentEquals("slider")) {
                setSliderList();
            } else if (this.page.contentEquals("combo")) {
                setRecyclerview();
            }
        } else {
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void setSliderList() {
        this.productsModelList = new ArrayList();
        final Map<String, String> params = new HashMap<>();
        String customerid = BSession.getInstance().getUser_id(this);
        if (customerid.equalsIgnoreCase("")) {
            this.para_str = "?ip_address =" + this.deviceid;
            this.para_str1 = "&scid=" + this.f165id;
        } else {
            this.para_str = "?user_id =" + customerid;
            this.para_str1 = "&scid=" + this.f165id;
        }
        this.progressDialog.show();
        if (customerid.equalsIgnoreCase("")) {
            this.baseUrl = ProductConfig.sliderlistproduct + this.para_str + this.para_str1;
        } else {
            this.baseUrl = ProductConfig.sliderlistproduct1 + this.para_str + this.para_str1;
        }
        Volley.newRequestQueue(this).add(new StringRequest(0, this.baseUrl, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    ComboOfferActivity.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        Toast.makeText(ComboOfferActivity.this.getApplicationContext(), "No product Result Found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ComboOfferActivity.this.productsModelList = new ArrayList();
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
                            ComboOfferActivity.this.productsModelList.add(model);
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
                            ComboOfferActivity.this.productsModelList.add(model);
                        }
                    }
                    ComboOfferActivity.this.comboOfferAdapter = new ComboOfferAdapter(ComboOfferActivity.this, ComboOfferActivity.this.productsModelList);
                    ComboOfferActivity.this.mRecyclerView.setHasFixedSize(true);
                    ComboOfferActivity.this.linearLayoutManager = new LinearLayoutManager(ComboOfferActivity.this, RecyclerView.VERTICAL, false);
                    ComboOfferActivity.this.mRecyclerView.setLayoutManager(ComboOfferActivity.this.linearLayoutManager);
                    ComboOfferActivity.this.mRecyclerView.setAdapter(ComboOfferActivity.this.comboOfferAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    ComboOfferActivity.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                ComboOfferActivity.this.progressDialog.dismiss();
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                return params;
            }
        });
    }

    private void setBandList() {
        this.productsModelList = new ArrayList();
        this.progressDialog.show();
        final Map<String, String> params = new HashMap<>();
        String user_id2 = BSession.getInstance().getUser_id(this);
        this.customer_id = user_id2;
        if (user_id2.equalsIgnoreCase("")) {
            this.para_str = "?ip_address=" + this.deviceid;
            this.para_str1 = "&banner_id=" + this.f165id;
            this.baseUrl = ProductConfig.brandlistproduct + this.para_str + this.para_str1;
        } else {
            this.para_str = "?user_id=" + this.customer_id;
            this.para_str1 = "&banner_id=" + this.f165id;
            this.baseUrl = ProductConfig.userbrandlistproduct + this.para_str + this.para_str1;
        }
        Volley.newRequestQueue(this).add(new StringRequest(0, this.baseUrl, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    ComboOfferActivity.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        Toast.makeText(ComboOfferActivity.this.getApplicationContext(), "No product Result Found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ComboOfferActivity.this.productsModelList = new ArrayList();
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
                            ComboOfferActivity.this.productsModelList.add(model);
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
                            ComboOfferActivity.this.productsModelList.add(model);
                        }
                    }
                    ComboOfferActivity.this.comboOfferAdapter = new ComboOfferAdapter(ComboOfferActivity.this, ComboOfferActivity.this.productsModelList);
                    ComboOfferActivity.this.mRecyclerView.setHasFixedSize(true);
                    ComboOfferActivity.this.linearLayoutManager = new LinearLayoutManager(ComboOfferActivity.this, RecyclerView.VERTICAL, false);
                    ComboOfferActivity.this.mRecyclerView.setLayoutManager(ComboOfferActivity.this.linearLayoutManager);
                    ComboOfferActivity.this.mRecyclerView.setAdapter(ComboOfferActivity.this.comboOfferAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    ComboOfferActivity.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                ComboOfferActivity.this.progressDialog.dismiss();
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                return params;
            }
        });
    }

    private void toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ComboOfferActivity.this.startActivity(new Intent(ComboOfferActivity.this.getApplicationContext(), MainActivity.class));
                ComboOfferActivity.this.finish();
            }
        });
        getSupportActionBar().setTitle((CharSequence) "Products");
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
                        ComboOfferActivity.this.badge_notification.setText("0");
                    } else {
                        ComboOfferActivity.this.badge_notification.setText(jsonResponse.getString("count"));
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
        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        View actionView = MenuItemCompat.getActionView(menuItem);
        this.badge_notification = (TextView) actionView.findViewById(R.id.badge_notification);
        actionView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ComboOfferActivity.this.onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.action_cart) {
            return super.onOptionsItemSelected(item);
        }
        startActivity(new Intent(this, CartActivity.class));
        return true;
    }

    private void setRecyclerview() {
        this.productsModelList = new ArrayList();
        this.progressDialog.show();
        final Map<String, String> params = new HashMap<>();
        if (this.customer_id.equalsIgnoreCase("")) {
            this.para_str = "?ip_address=" + this.deviceid;
            this.baseUrl = ProductConfig.combooffer + this.para_str;
        } else {
            this.para_str = "?user_id=" + this.customer_id;
            this.baseUrl = ProductConfig.usercombooffer + this.para_str;
        }
        StringRequest r1 = new StringRequest(0, this.baseUrl, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    ComboOfferActivity.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        Toast.makeText(ComboOfferActivity.this.getApplicationContext(), "No offer Result Found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ComboOfferActivity.this.productsModelList = new ArrayList();
                    JSONArray array = jsonResponse.getJSONArray("storeList");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jSONObject = array.getJSONObject(i);
                        ProductsModel model = new ProductsModel();
                        model.setProduct_id(array.getJSONObject(i).getString("pid"));
                        model.setCid(array.getJSONObject(i).getString("cid"));
                        model.setScid(array.getJSONObject(i).getString("scid"));
                        model.setProduct_name(array.getJSONObject(i).getString("productname"));
                        model.setOffer_status(array.getJSONObject(i).getString(NotificationCompat.CATEGORY_STATUS));
                        model.setProduct_offer(array.getJSONObject(i).getString("percentage"));
                        model.setProduct_image(array.getJSONObject(i).getString("productimage"));
                        model.setProduct_quantity(array.getJSONObject(i).getString("qty"));
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
                            ComboOfferActivity.this.productsModelList.add(model);
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
                            ComboOfferActivity.this.productsModelList.add(model);
                        }
                    }
                    ComboOfferActivity.this.comboOfferAdapter = new ComboOfferAdapter(ComboOfferActivity.this, ComboOfferActivity.this.productsModelList);
                    ComboOfferActivity.this.mRecyclerView.setHasFixedSize(true);
                    ComboOfferActivity.this.linearLayoutManager = new LinearLayoutManager(ComboOfferActivity.this, RecyclerView.VERTICAL, false);
                    ComboOfferActivity.this.mRecyclerView.setLayoutManager(ComboOfferActivity.this.linearLayoutManager);
                    ComboOfferActivity.this.mRecyclerView.setAdapter(ComboOfferActivity.this.comboOfferAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    ComboOfferActivity.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                ComboOfferActivity.this.progressDialog.dismiss();
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                return params;
            }
        };
        Volley.newRequestQueue(this).add(r1);
        r1.setRetryPolicy(new DefaultRetryPolicy(5000, 1, 1.0f));
    }
}
