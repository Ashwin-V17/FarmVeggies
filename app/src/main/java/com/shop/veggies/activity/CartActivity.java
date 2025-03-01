package com.shop.veggies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
import com.shop.veggies.R;
import com.shop.veggies.adapter.ViewCartAdapter;
import com.shop.veggies.model.CartModel;
import com.shop.veggies.utils.BSession;
import com.shop.veggies.utils.ProductConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    TextView badge_notification;
    String baseUrl;
    Button btn_continue;
    CartModel cartModel = new CartModel();
    List<CartModel> cartModelList = new ArrayList();
    Button checkout;
    String customer_id = null;
    String deviceid;
    String idd;
    String ipaddress;
    TextView items;
    FrameLayout layout_items;
    LinearLayoutManager linearLayoutManager;
    LinearLayout llay_cart_empty;
    RecyclerView mRecyclerView;
    String para_str;
    Toolbar toolbar;
    TextView total;
    ViewCartAdapter viewCartAdapter;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_cart);
        this.deviceid = Settings.Secure.getString(getContentResolver(), "android_id");
        toolbar();
        getCartCount();
        this.llay_cart_empty = (LinearLayout) findViewById(R.id.llay_cart_empty);
        this.layout_items = (FrameLayout) findViewById(R.id.layout_items);
        this.items = (TextView) findViewById(R.id.items);
        this.total = (TextView) findViewById(R.id.total);
        String user_id = BSession.getInstance().getUser_id(this);
        this.idd = user_id;
        Log.d("idd", user_id);
        this.llay_cart_empty.setVisibility(View.GONE);
        this.layout_items.setVisibility(View.VISIBLE);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.rv_cartList);
        Button button = (Button) findViewById(R.id.btn_checkout);
        this.checkout = button;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (BSession.getInstance().getUser_name(CartActivity.this).equalsIgnoreCase("")) {
                    CartActivity.this.startActivity(new Intent(CartActivity.this, LoginActivity.class));
                    return;
                }
                CartActivity.this.startActivity(new Intent(CartActivity.this, CheckoutActivity.class));
            }
        });
        setRecyclerview();
        Button button2 = (Button) findViewById(R.id.btn_continue);
        this.btn_continue = button2;
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CartActivity.this.checkout.startAnimation(AnimationUtils.loadAnimation(CartActivity.this, R.anim.fade_in));
                CartActivity.this.startActivity(new Intent(CartActivity.this, MainActivity.class));
            }
        });
    }

    public void setRecyclerview() {
        this.cartModelList = new ArrayList();
        this.customer_id = BSession.getInstance().getUser_id(this);
        final Map<String, String> params = new HashMap<>();
        if (this.customer_id.equalsIgnoreCase("")) {
            this.para_str = "?ip_address=" + this.deviceid;
            this.baseUrl = ProductConfig.cartlist + this.para_str;
        } else {
            this.para_str = "?user_id=" + this.customer_id;
            this.baseUrl = ProductConfig.usercartlist + this.para_str;
        }
        Volley.newRequestQueue(this).add(new StringRequest(0, this.baseUrl, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        if (CartActivity.this.cartModelList.size() > 0) {
                            CartActivity.this.llay_cart_empty.setVisibility(View.GONE);
                            CartActivity.this.layout_items.setVisibility(View.VISIBLE);
                        } else {
                            CartActivity.this.llay_cart_empty.setVisibility(View.VISIBLE);
                            CartActivity.this.layout_items.setVisibility(View.GONE);
                        }
                        Toast.makeText(CartActivity.this.getApplicationContext(), "Cart empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    CartActivity.this.cartModelList = new ArrayList();
                    JSONArray array = jsonResponse.getJSONArray("storeList");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jSONObject = array.getJSONObject(i);
                        CartModel cartModel = new CartModel();
                        cartModel.setCart_id(array.getJSONObject(i).getString("cart_id"));
                        cartModel.setProduct_id(array.getJSONObject(i).getString("product_id"));
                        cartModel.setProduct_name(array.getJSONObject(i).getString("product_name"));
                        cartModel.setProduct_quantity(array.getJSONObject(i).getString("qty"));
                        cartModel.setProduct_price(array.getJSONObject(i).getString("total"));
                        cartModel.setVid(array.getJSONObject(i).getString("vid"));
                        cartModel.setProduct_weight(array.getJSONObject(i).getString("weight"));
                        cartModel.setProduct_image(array.getJSONObject(i).getString("web_image"));
                        cartModel.setScid(array.getJSONObject(i).getString("pro_scid"));
                        CartActivity.this.cartModelList.add(cartModel);
                    }
                    if (CartActivity.this.cartModelList.size() > 0) {
                        CartActivity.this.llay_cart_empty.setVisibility(View.GONE);
                        CartActivity.this.layout_items.setVisibility(View.VISIBLE);
                    } else {
                        CartActivity.this.llay_cart_empty.setVisibility(View.VISIBLE);
                        CartActivity.this.layout_items.setVisibility(View.GONE);
                    }
                    CartActivity.this.viewCartAdapter = new ViewCartAdapter(CartActivity.this, CartActivity.this.cartModelList);
                    CartActivity.this.mRecyclerView.setHasFixedSize(true);
                    CartActivity.this.linearLayoutManager = new LinearLayoutManager(CartActivity.this, RecyclerView.VERTICAL, false);
                    CartActivity.this.mRecyclerView.setLayoutManager(CartActivity.this.linearLayoutManager);
                    CartActivity.this.mRecyclerView.setAdapter(CartActivity.this.viewCartAdapter);
                    CartActivity.this.viewCartAdapter.notifyDataSetChanged();
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

    private void toolbar() {
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar);
        this.toolbar = toolbar2;
        setSupportActionBar(toolbar2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CartActivity.this.onBackPressed();
            }
        });
        getSupportActionBar().setTitle((CharSequence) "Cart Items");
        this.toolbar.setTitleTextColor(-1);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        this.badge_notification = (TextView) MenuItemCompat.getActionView(menu.findItem(R.id.action_cart1)).findViewById(R.id.badge_notification);
        return true;
    }

    public void getCartCount() {
        final Map<String, String> params = new HashMap<>();
        String user_id = BSession.getInstance().getUser_id(this);
        this.customer_id = user_id;
        if (user_id.equalsIgnoreCase("")) {
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
                        CartActivity.this.badge_notification.setText("0");
                        return;
                    }
                    CartActivity.this.badge_notification.setText(jsonResponse.getString("count"));
                    TextView textView = CartActivity.this.items;
                    textView.setText(jsonResponse.getString("count") + "  Item(s) ");
                    CartActivity.this.total.setText(jsonResponse.getString("total"));
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
