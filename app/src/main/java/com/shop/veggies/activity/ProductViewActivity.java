package com.shop.veggies.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.shop.veggies.R;
import com.shop.veggies.RecyclerItemClickListener;
import com.shop.veggies.adapter.WeightAdapter;
import com.shop.veggies.model.ProductsModel;
import com.shop.veggies.model.WeightModel;
import com.shop.veggies.utils.BSession;
import com.shop.veggies.utils.ProductConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
public class ProductViewActivity extends AppCompatActivity {
    public static WeightModel subWightModel = new WeightModel();
    public static ProductsModel subcourcemodel = new ProductsModel();
    TextView badge_notification;
    String baseUrl;
    String customer_id = "";
    String deviceid;
    String editqut;
    FrameLayout fl_offer;

    /* renamed from: i */
    Integer f166i;
    String ipaddress;
    ImageView iv_itemImage;
    LinearLayout llay_cart;
    private BottomSheetDialog mBottomSheetDialog;
    int offer_status;
    String para_str;
    String para_str3;
    ProgressDialog progressDialog;
    String qty;
    /* access modifiers changed from: private */
    public RecyclerView rv_wgtList;
    TextView stack;
    String stackcoit;
    TextView tv_add1;
    TextView tv_minus;
    TextView tv_mrp;
    TextView tv_offer;
    TextView tv_pContent;
    TextView tv_pId;
    TextView tv_pPrice;
    TextView tv_pTitle;
    TextView tv_plus;
    TextView tv_qty;
    TextView tv_wid;
    String type;
    WeightAdapter weightAdapter;
    /* access modifiers changed from: private */
    public List<WeightModel> weightModelList;
    ArrayList<String> weightarray = new ArrayList<>();

    /* access modifiers changed from: protected */
    @SuppressLint("RestrictedApi")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_product_view);
        this.deviceid = Settings.Secure.getString(getContentResolver(), "android_id");
        ProgressDialog progressDialog2 = new ProgressDialog(this);
        this.progressDialog = progressDialog2;
        progressDialog2.setMessage("Loading.....");
        this.customer_id = BSession.getInstance().getUser_id(this);
        this.llay_cart = (LinearLayout) findViewById(R.id.llay_cart);
        this.stackcoit = subcourcemodel.getProduct_status();
        init();
        toolbar();
        getCartCount();
        getWeightList();
        String str = this.qty;
        if (str != null) {
            if (str.equals("0")) {
                this.tv_plus.setVisibility(View.GONE);
                this.tv_qty.setVisibility(View.GONE);
                this.tv_add1.setVisibility(View.VISIBLE);
                this.tv_minus.setVisibility(View.GONE);
            } else {
                this.tv_plus.setVisibility(View.VISIBLE);
                this.tv_qty.setVisibility(View.VISIBLE);
                this.tv_add1.setVisibility(View.GONE);
                this.tv_minus.setVisibility(View.VISIBLE);
                this.tv_qty.setText(this.qty);
            }
        }
        if (subcourcemodel.getProduct_quantity().isEmpty()) {
            this.tv_plus.setVisibility(View.GONE);
            this.tv_qty.setVisibility(View.GONE);
            this.tv_add1.setVisibility(View.VISIBLE);
            this.tv_minus.setVisibility(View.GONE);
        } else {
            this.tv_plus.setVisibility(View.VISIBLE);
            this.tv_qty.setVisibility(View.VISIBLE);
            this.tv_add1.setVisibility(View.GONE);
            this.tv_minus.setVisibility(View.VISIBLE);
        }
        if (this.stackcoit.equalsIgnoreCase("Enable")) {
            this.stack.setVisibility(View.GONE);
            this.stack.setTextColor(Color.parseColor("#075730"));
        } else {
            this.stack.setVisibility(View.VISIBLE);
            this.stack.setText("Out of Stock ");
            this.stack.setTextColor(SupportMenu.CATEGORY_MASK);
            this.llay_cart.setVisibility(View.GONE);
        }
        if (subcourcemodel.getProduct_quantity().equalsIgnoreCase("0")) {
            this.tv_plus.setVisibility(View.GONE);
            this.tv_qty.setVisibility(View.GONE);
            this.tv_add1.setVisibility(View.VISIBLE);
            this.tv_minus.setVisibility(View.GONE);
        } else {
            this.tv_plus.setVisibility(View.VISIBLE);
            this.tv_qty.setVisibility(View.VISIBLE);
            this.tv_add1.setVisibility(View.GONE);
            this.tv_minus.setVisibility(View.VISIBLE);
        }
        this.tv_add1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ProductViewActivity.this.tv_plus.setTextColor(ProductViewActivity.this.getColor(R.color.white));
                if (!ProductViewActivity.this.tv_wid.getText().toString().isEmpty()) {
                    ProductViewActivity.this.tv_plus.setVisibility(View.VISIBLE);
                    ProductViewActivity.this.tv_qty.setVisibility(View.VISIBLE);
                    ProductViewActivity.this.tv_add1.setVisibility(View.GONE);
                    ProductViewActivity.this.tv_minus.setVisibility(View.VISIBLE);
                    ProductViewActivity.this.customer_id = BSession.getInstance().getUser_id(ProductViewActivity.this);
                    final Map<String, String> params = new HashMap<>();
                    String para_str1 = "?pid=" + ProductViewActivity.this.tv_pId.getText().toString().trim();
                    if (ProductViewActivity.this.customer_id.equalsIgnoreCase("")) {
                        ProductViewActivity.this.para_str3 = "&ip_address=" + ProductViewActivity.this.deviceid;
                    } else {
                        ProductViewActivity.this.para_str3 = "&user_id=" + ProductViewActivity.this.customer_id;
                    }
                    String para_str5 = "&vid=" + ProductViewActivity.this.tv_wid.getText().toString().trim();
                    if (ProductViewActivity.this.customer_id.equalsIgnoreCase("")) {
                        ProductViewActivity.this.baseUrl = ProductConfig.addcart + para_str1 + "&qty=1" + ProductViewActivity.this.para_str3 + "&cart_type=add" + para_str5;
                    } else {
                        ProductViewActivity.this.baseUrl = ProductConfig.useraddcart + para_str1 + "&qty=1" + ProductViewActivity.this.para_str3 + "&cart_type=add" + para_str5;
                    }
                    Volley.newRequestQueue(ProductViewActivity.this.getApplicationContext()).add(new StringRequest(0, ProductViewActivity.this.baseUrl, new Response.Listener<String>() {
                        public void onResponse(String response) {
                            Log.e("Response", response.toString());
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                    Toast.makeText(ProductViewActivity.this.getApplicationContext(), "Cart Add Failed", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                ProductViewActivity.this.tv_qty.setText(jsonResponse.getString("qty"));
                                ProductViewActivity.this.getCartCount();
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
            }
        });
        this.tv_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ProductViewActivity.this.tv_plus.setTextColor(ProductViewActivity.this.getColor(R.color.white));
                ProductViewActivity.this.tv_plus.startAnimation(AnimationUtils.loadAnimation(ProductViewActivity.this, R.anim.fade_in));
                Log.d("src", "Increasing value...");
                ProductViewActivity productViewActivity = ProductViewActivity.this;
                productViewActivity.f166i = Integer.valueOf(productViewActivity.tv_qty.getText().toString());
                ProductViewActivity productViewActivity2 = ProductViewActivity.this;
                productViewActivity2.f166i = Integer.valueOf(productViewActivity2.f166i.intValue() + 1);
                String valueOf = String.valueOf(ProductViewActivity.this.f166i);
                ProductViewActivity.this.customer_id = BSession.getInstance().getUser_id(ProductViewActivity.this);
                final Map<String, String> params = new HashMap<>();
                String para_str1 = "?pid=" + ProductViewActivity.this.tv_pId.getText().toString().trim();
                if (ProductViewActivity.this.customer_id.equalsIgnoreCase("")) {
                    ProductViewActivity.this.para_str3 = "&ip_address=" + ProductViewActivity.this.deviceid;
                } else {
                    ProductViewActivity.this.para_str3 = "&user_id=" + ProductViewActivity.this.customer_id;
                }
                String para_str5 = "&vid=" + ProductViewActivity.this.tv_wid.getText().toString().trim();
                if (ProductViewActivity.this.customer_id.equalsIgnoreCase("")) {
                    ProductViewActivity.this.baseUrl = ProductConfig.addcart + para_str1 + "&qty=1" + ProductViewActivity.this.para_str3 + "&cart_type=add" + para_str5;
                } else {
                    ProductViewActivity.this.baseUrl = ProductConfig.useraddcart + para_str1 + "&qty=1" + ProductViewActivity.this.para_str3 + "&cart_type=add" + para_str5;
                }
                Volley.newRequestQueue(ProductViewActivity.this).add(new StringRequest(0, ProductViewActivity.this.baseUrl, new Response.Listener<String>() {
                    public void onResponse(String response) {
                        Log.e("Response", response.toString());
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                Toast.makeText(ProductViewActivity.this, "Cart Add Failed", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ProductViewActivity.this.tv_qty.setText(jsonResponse.getString("qty"));
                            ProductViewActivity.this.getCartCount();
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
        });
        this.tv_minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ProductViewActivity.this.tv_minus.startAnimation(AnimationUtils.loadAnimation(ProductViewActivity.this, R.anim.fade_in));
                Log.d("src", "Decreasing value...");
                ProductViewActivity productViewActivity = ProductViewActivity.this;
                productViewActivity.f166i = Integer.valueOf(productViewActivity.tv_qty.getText().toString());
                if (ProductViewActivity.this.f166i.intValue() > 0) {
                    ProductViewActivity productViewActivity2 = ProductViewActivity.this;
                    productViewActivity2.f166i = Integer.valueOf(productViewActivity2.f166i.intValue() - 1);
                    String valueOf = String.valueOf(ProductViewActivity.this.f166i);
                    ProductViewActivity.this.customer_id = BSession.getInstance().getUser_id(ProductViewActivity.this);
                    final Map<String, String> params = new HashMap<>();
                    String para_str1 = "?pid=" + ProductViewActivity.this.tv_pId.getText().toString().trim();
                    if (ProductViewActivity.this.customer_id.equalsIgnoreCase("")) {
                        ProductViewActivity.this.para_str3 = "&ip_address=" + ProductViewActivity.this.deviceid;
                    } else {
                        ProductViewActivity.this.para_str3 = "&user_id=" + ProductViewActivity.this.customer_id;
                    }
                    String para_str5 = "&vid=" + ProductViewActivity.this.tv_wid.getText().toString().trim();
                    if (ProductViewActivity.this.customer_id.equalsIgnoreCase("")) {
                        ProductViewActivity.this.baseUrl = ProductConfig.addcart + para_str1 + "&qty=1" + ProductViewActivity.this.para_str3 + "&cart_type=sub" + para_str5;
                    } else {
                        ProductViewActivity.this.baseUrl = ProductConfig.addcart + para_str1 + "&qty=1" + ProductViewActivity.this.para_str3 + "&cart_type=sub" + para_str5;
                    }
                    Volley.newRequestQueue(ProductViewActivity.this).add(new StringRequest(0, ProductViewActivity.this.baseUrl, new Response.Listener<String>() {
                        public void onResponse(String response) {
                            Log.e("Response", response.toString());
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                    ProductViewActivity productViewActivity = ProductViewActivity.this;
                                    Toast.makeText(productViewActivity, "Cart sub Failed" + ProductViewActivity.this.tv_qty.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (jsonResponse.getString("message").equalsIgnoreCase("Successfully Updated")) {
                                    ProductViewActivity.this.tv_qty.setText(jsonResponse.getString("qty"));
                                    ProductViewActivity.this.getCartCount();
                                } else {
                                    ProductViewActivity.this.tv_plus.setTextColor(ProductViewActivity.this.getColor(R.color.colorAccent));
                                    ProductViewActivity.this.tv_plus.setVisibility(View.GONE);
                                    ProductViewActivity.this.tv_qty.setVisibility(View.GONE);
                                    ProductViewActivity.this.tv_add1.setVisibility(View.VISIBLE);
                                    ProductViewActivity.this.tv_minus.setVisibility(View.GONE);
                                    ProductViewActivity.this.getCartCount();
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
                    return;
                }
                Log.d("src", "Value can't be less than 0");
            }
        });
        this.tv_pTitle.setText(subcourcemodel.getProduct_name());
        this.tv_qty.setText(subcourcemodel.getProduct_quantity());
        this.tv_pId.setText(subcourcemodel.getProduct_id());
        this.offer_status = Integer.parseInt(subcourcemodel.getOffer_status());
        this.tv_qty.setText(subcourcemodel.getProduct_quantity());
        int i = this.offer_status;
        if (i == 1) {
            this.fl_offer.setVisibility(View.VISIBLE);
        } else if (i == 0) {
            this.fl_offer.setVisibility(View.GONE);
        }
        this.tv_pContent.setText(Html.fromHtml(String.valueOf(Html.fromHtml(subcourcemodel.getProduct_desc()))));
        TextView textView = this.tv_offer;
        if (textView == null) {
            textView.setText(subcourcemodel.getProduct_offer() + "\nOff");
            this.tv_pContent.setText(Html.fromHtml(String.valueOf(Html.fromHtml(subcourcemodel.getProduct_desc()))));
        } else {
            textView.setText(subcourcemodel.getProduct_offer() + "\nOff");
            this.tv_pContent.setText(Html.fromHtml(String.valueOf(Html.fromHtml(subcourcemodel.getProduct_desc()))));
        }
        ((RequestBuilder) Glide.with((FragmentActivity) this).load(subcourcemodel.getProduct_image()).placeholder((int) R.drawable.dummy)).into(this.iv_itemImage);
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

    public String GetDeviceIpWiFiData() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();

            // Convert int IP to human-readable format
            try {
                byte[] ipByteArray = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(ipAddress).array();
                InetAddress inetAddress = InetAddress.getByAddress(ipByteArray);
                return inetAddress.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return "Unable to get IP Address";
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_cart1);
        View actionView = MenuItemCompat.getActionView(menuItem);
        this.badge_notification = (TextView) actionView.findViewById(R.id.badge_notification);
        actionView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ProductViewActivity.this.onOptionsItemSelected(menuItem);
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

    private void init() {
        this.tv_pTitle = (TextView) findViewById(R.id.tv_pTitle);
        this.tv_pPrice = (TextView) findViewById(R.id.tv_pPrice);
        this.tv_wid = (TextView) findViewById(R.id.tv_wid);
        this.iv_itemImage = (ImageView) findViewById(R.id.iv_itemImage);
        this.tv_pContent = (TextView) findViewById(R.id.tv_pContent);
        this.tv_pId = (TextView) findViewById(R.id.tv_pId);
        TextView textView = (TextView) findViewById(R.id.tv_mrp);
        this.tv_mrp = textView;
        textView.setPaintFlags(textView.getPaintFlags() | 16);
        this.tv_qty = (TextView) findViewById(R.id.tv_qty);
        this.tv_plus = (TextView) findViewById(R.id.tv_plus);
        this.tv_minus = (TextView) findViewById(R.id.tv_minus);
        this.tv_offer = (TextView) findViewById(R.id.tv_offer);
        this.fl_offer = (FrameLayout) findViewById(R.id.fl_offer);
        this.rv_wgtList = (RecyclerView) findViewById(R.id.rv_wgtList);
        this.tv_add1 = (TextView) findViewById(R.id.tv_add1);
        this.stack = (TextView) findViewById(R.id.stack);
    }

    public void getCartCount() {
        final Map<String, String> params = new HashMap<>();
        String customerid = BSession.getInstance().getUser_id(this);
        if (customerid.equalsIgnoreCase("")) {
            this.para_str = "?ip_address=" + this.deviceid;
            this.baseUrl = ProductConfig.cartcount + this.para_str;
        } else {
            this.para_str = "?user_id=" + customerid;
            this.baseUrl = ProductConfig.usercartcount + this.para_str;
        }
        Volley.newRequestQueue(this).add(new StringRequest(0, this.baseUrl, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        ProductViewActivity.this.badge_notification.setText("0");
                    } else {
                        ProductViewActivity.this.badge_notification.setText(jsonResponse.getString("count"));
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

    private void getWeightList() {
        final Map<String, String> params = new HashMap<>();
        this.progressDialog.show();
        Volley.newRequestQueue(this).add(new StringRequest(0, ProductConfig.weightlist + ("?pid=" + subcourcemodel.getProduct_id()), new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    ProductViewActivity.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        Toast.makeText(ProductViewActivity.this.getApplicationContext(), "No Weight Result Found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List unused = ProductViewActivity.this.weightModelList = new ArrayList();
                    JSONArray array = jsonResponse.getJSONArray("storeList");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jSONObject = array.getJSONObject(i);
                        WeightModel weightModel = new WeightModel();
                        weightModel.setWid(array.getJSONObject(i).getString("vid"));
                        weightModel.setWeb_title(array.getJSONObject(i).getString("weight"));
                        weightModel.setWeb_price(array.getJSONObject(i).getString(FirebaseAnalytics.Param.PRICE));
                        weightModel.setMrp(array.getJSONObject(i).getString("mrp"));
                        ProductViewActivity.this.weightModelList.add(weightModel);
                    }
                    for (WeightModel weightModel2 : ProductViewActivity.this.weightModelList) {
                        ProductViewActivity.this.weightarray.add(weightModel2.getWeb_title());
                    }
                    ProductViewActivity.this.rv_wgtList.setLayoutManager(new LinearLayoutManager(ProductViewActivity.this, RecyclerView.VERTICAL, false));
                    ProductViewActivity.this.rv_wgtList.setHasFixedSize(true);
                    ProductViewActivity.this.weightAdapter = new WeightAdapter(ProductViewActivity.this.getApplicationContext(), ProductViewActivity.this.weightModelList);
                    ProductViewActivity.this.rv_wgtList.setAdapter(ProductViewActivity.this.weightAdapter);
                    TextView textView = ProductViewActivity.this.tv_pPrice;
                    textView.setText("₹ " + ((WeightModel) ProductViewActivity.this.weightModelList.get(0)).getWeb_price());
                    TextView textView2 = ProductViewActivity.this.tv_mrp;
                    textView2.setText(" ₹ " + ((WeightModel) ProductViewActivity.this.weightModelList.get(0)).getMrp());
                    ProductViewActivity.this.tv_wid.setText(((WeightModel) ProductViewActivity.this.weightModelList.get(0)).getWid());
                    ProductViewActivity.this.rv_wgtList.addOnItemTouchListener(new RecyclerItemClickListener(ProductViewActivity.this, ProductViewActivity.this.rv_wgtList, new RecyclerItemClickListener.ClickListener() {
                        public void onItemClick(View view, int position) {
                            Log.e("weight position", ((WeightModel) ProductViewActivity.this.weightModelList.get(position)).getWeb_title());
                            TextView textView = ProductViewActivity.this.tv_pPrice;
                            textView.setText("₹ " + ((WeightModel) ProductViewActivity.this.weightModelList.get(position)).getWeb_price());
                            ProductViewActivity.this.tv_wid.setText(((WeightModel) ProductViewActivity.this.weightModelList.get(position)).getWid());
                            TextView textView2 = ProductViewActivity.this.tv_mrp;
                            textView2.setText(" ₹ " + ((WeightModel) ProductViewActivity.this.weightModelList.get(position)).getMrp());
                            ProductViewActivity.this.qty = ((WeightModel) ProductViewActivity.this.weightModelList.get(position)).getWid();
                        }
                    }));
                } catch (JSONException e) {
                    e.printStackTrace();
                    ProductViewActivity.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                ProductViewActivity.this.progressDialog.dismiss();
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
                ProductViewActivity.this.startActivity(new Intent(ProductViewActivity.this.getApplicationContext(), MainActivity.class));
                ProductViewActivity.this.finish();
            }
        });
        getSupportActionBar().setTitle((CharSequence) subcourcemodel.getProduct_name());
        toolbar.setTitleTextColor(-1);
    }
}
