package com.shop.veggies.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.shop.veggies.activity.CartActivity;
import com.shop.veggies.model.CartModel;
import com.shop.veggies.utils.BSession;
import com.shop.veggies.utils.ProductConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewCartAdapter extends RecyclerView.Adapter<ViewCartAdapter.MailViewHolder> {
    String baseUrl;
    List<CartModel> cartModelList;
    String customer_id = "";
    String deiverycharge = "";
    String deviceid;
    String editqut;

    /* renamed from: i */
    Integer f173i;
    String ipaddress;
    private BottomSheetDialog mBottomSheetDialog;
    /* access modifiers changed from: private */
    public Context mContext;
    String para_str;
    String para_str1;
    String para_str3;
    String pid;
    int position = 0;
    String product_id = "";
    CartModel productsModel = new CartModel();
    String qty;
    String scid;
    String type;

    public ViewCartAdapter(Context mContext2, List<CartModel> cartModelList2) {
        this.mContext = mContext2;
        this.cartModelList = cartModelList2;
    }

    public MailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cartlist, parent, false));
    }

    public void onBindViewHolder(final MailViewHolder holder, @SuppressLint("RecyclerView") final int position2) {
        this.deviceid = Settings.Secure.getString(this.mContext.getContentResolver(), "android_id");
        CartModel model = this.cartModelList.get(position2);
        holder.tv_pTitle.setText(model.getProduct_name());
        holder.tv_pPrice.setText(model.getProduct_price());
        holder.tv_pqyt.setText(model.getProduct_quantity());
        this.product_id = this.cartModelList.get(position2).getProduct_id();
        holder.tv_pId.setText(this.cartModelList.get(position2).getCart_id());
        holder.tv_wgt.setText(model.getProduct_weight());
        this.qty = this.cartModelList.get(position2).getProduct_quantity();
        holder.ms_weight.setText(this.cartModelList.get(position2).getProduct_weight());
        holder.tv_wid.setText(this.cartModelList.get(position2).getVid());
        holder.pId.setText(this.cartModelList.get(position2).getProduct_id());
        this.scid = this.cartModelList.get(position2).getScid();
        ((RequestBuilder) Glide.with(this.mContext).load(this.cartModelList.get(position2).getProduct_image()).placeholder((int) R.drawable.dummy)).into(holder.ivMenu);
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                holder.iv_delete.startAnimation(AnimationUtils.loadAnimation(ViewCartAdapter.this.mContext, R.anim.fade_in));
                ViewCartAdapter.this.cartModelList = new ArrayList();
                ViewCartAdapter.this.customer_id = BSession.getInstance().getUser_id(ViewCartAdapter.this.mContext);
                final Map<String, String> params = new HashMap<>();
                if (ViewCartAdapter.this.customer_id.equalsIgnoreCase("")) {
                    ViewCartAdapter viewCartAdapter = ViewCartAdapter.this;
                    viewCartAdapter.para_str = "?ip_address=" + ViewCartAdapter.this.deviceid;
                    ViewCartAdapter viewCartAdapter2 = ViewCartAdapter.this;
                    viewCartAdapter2.para_str1 = "&cart_id=" + holder.tv_pId.getText().toString();
                    ViewCartAdapter viewCartAdapter3 = ViewCartAdapter.this;
                    viewCartAdapter3.baseUrl = ProductConfig.cartdelete + ViewCartAdapter.this.para_str + ViewCartAdapter.this.para_str1;
                } else {
                    ViewCartAdapter viewCartAdapter4 = ViewCartAdapter.this;
                    viewCartAdapter4.para_str = "?user_id=" + ViewCartAdapter.this.customer_id;
                    ViewCartAdapter viewCartAdapter5 = ViewCartAdapter.this;
                    viewCartAdapter5.para_str1 = "&cart_id=" + holder.tv_pId.getText().toString();
                    ViewCartAdapter viewCartAdapter6 = ViewCartAdapter.this;
                    viewCartAdapter6.baseUrl = ProductConfig.usercartdelete + ViewCartAdapter.this.para_str + ViewCartAdapter.this.para_str1;
                }
                Volley.newRequestQueue(ViewCartAdapter.this.mContext).add(new StringRequest(0, ViewCartAdapter.this.baseUrl, new Response.Listener<String>() {
                    public void onResponse(String response) {
                        Log.e("Response", response.toString());
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                Toast.makeText(ViewCartAdapter.this.mContext, "unable to remove the item", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ViewCartAdapter.this.cartModelList = new ArrayList();
                            ((CartActivity) ViewCartAdapter.this.mContext).getCartCount();
                            ((CartActivity) ViewCartAdapter.this.mContext).setRecyclerview();
                            ViewCartAdapter.this.cartModelList.clear();
                            Toast.makeText(ViewCartAdapter.this.mContext, "Product removed from your cart", Toast.LENGTH_SHORT).show();
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
        if (this.qty.equals("0")) {
            holder.tv_plus.setVisibility(View.GONE);
            holder.tv_qty.setVisibility(View.GONE);
            holder.rel_add.setVisibility(View.VISIBLE);
            holder.tv_minus.setVisibility(View.GONE);
        } else {
            holder.tv_plus.setVisibility(View.VISIBLE);
            holder.tv_qty.setVisibility(View.VISIBLE);
            holder.rel_add.setVisibility(View.GONE);
            holder.tv_minus.setVisibility(View.VISIBLE);
            holder.tv_qty.setText(this.qty);
        }
        holder.tv_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (holder.ms_weight.getText().toString().isEmpty()) {
                    holder.ms_weight.setError("Choose any weight");
                } else if (ViewCartAdapter.this.cartModelList.get(position2).getScid().equalsIgnoreCase("44")) {
                    Log.d("src", "Increasing value...");
                    ViewCartAdapter viewCartAdapter = ViewCartAdapter.this;
                    viewCartAdapter.f173i = Integer.valueOf(viewCartAdapter.cartModelList.get(position2).getProduct_quantity());
                    ViewCartAdapter viewCartAdapter2 = ViewCartAdapter.this;
                    viewCartAdapter2.f173i = Integer.valueOf(viewCartAdapter2.f173i.intValue() + 1);
                    String _stringVal = String.valueOf(ViewCartAdapter.this.f173i);
                    holder.tv_qty.setText(_stringVal);
                    ViewCartAdapter.this.cartModelList.get(position2).setProduct_quantity(_stringVal);
                    ViewCartAdapter.this.customer_id = BSession.getInstance().getUser_id(ViewCartAdapter.this.mContext);
                    final Map<String, String> params = new HashMap<>();
                    String para_str1 = "?pid=" + holder.pId.getText().toString().trim();
                    if (ViewCartAdapter.this.customer_id.equalsIgnoreCase("")) {
                        ViewCartAdapter.this.para_str3 = "&ip_address=" + ViewCartAdapter.this.deviceid;
                    } else {
                        ViewCartAdapter.this.para_str3 = "&user_id=" + ViewCartAdapter.this.customer_id;
                    }
                    String para_str5 = "&vid=" + holder.tv_wid.getText().toString().trim();
                    String para_str6 = "&scid=" + ViewCartAdapter.this.cartModelList.get(position2).getScid();
                    if (ViewCartAdapter.this.customer_id.equalsIgnoreCase("")) {
                        ViewCartAdapter.this.baseUrl = ProductConfig.addcart + para_str1 + "&qty=1" + ViewCartAdapter.this.para_str3 + "&cart_type=add" + para_str5 + para_str6;
                    } else {
                        ViewCartAdapter.this.baseUrl = ProductConfig.useraddcart + para_str1 + "&qty=1" + ViewCartAdapter.this.para_str3 + "&cart_type=add" + para_str5 + para_str6;
                    }
                    Volley.newRequestQueue(ViewCartAdapter.this.mContext).add(new StringRequest(0, ViewCartAdapter.this.baseUrl, new Response.Listener<String>() {
                        public void onResponse(String response) {
                            Log.e("Response", response.toString());
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                if (jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) && jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                    holder.tv_qty.setText(jsonResponse.getString("qty"));
                                    ViewCartAdapter.this.cartModelList.get(position2).setProduct_quantity(jsonResponse.getString("qty"));
                                    ((CartActivity) ViewCartAdapter.this.mContext).getCartCount();
                                    ((CartActivity) ViewCartAdapter.this.mContext).setRecyclerview();
                                } else if (jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) && jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("0")) {
                                    Toast.makeText(ViewCartAdapter.this.mContext, "One Product one time only added", Toast.LENGTH_SHORT).show();
                                } else if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("5")) {
                                    Toast.makeText(ViewCartAdapter.this.mContext, "Cart Add Failed", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ViewCartAdapter.this.mContext, "You can purchase only Five products", Toast.LENGTH_SHORT).show();
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
                } else {
                    Log.d("src", "Increasing value...");
                    ViewCartAdapter viewCartAdapter3 = ViewCartAdapter.this;
                    viewCartAdapter3.f173i = Integer.valueOf(viewCartAdapter3.cartModelList.get(position2).getProduct_quantity());
                    ViewCartAdapter viewCartAdapter4 = ViewCartAdapter.this;
                    viewCartAdapter4.f173i = Integer.valueOf(viewCartAdapter4.f173i.intValue() + 1);
                    String _stringVal2 = String.valueOf(ViewCartAdapter.this.f173i);
                    holder.tv_qty.setText(_stringVal2);
                    ViewCartAdapter.this.cartModelList.get(position2).setProduct_quantity(_stringVal2);
                    ViewCartAdapter.this.customer_id = BSession.getInstance().getUser_id(ViewCartAdapter.this.mContext);
                    final Map<String, String> params2 = new HashMap<>();
                    String para_str12 = "?pid=" + holder.pId.getText().toString().trim();
                    if (ViewCartAdapter.this.customer_id.equalsIgnoreCase("")) {
                        ViewCartAdapter.this.para_str3 = "&ip_address=" + ViewCartAdapter.this.deviceid;
                    } else {
                        ViewCartAdapter.this.para_str3 = "&user_id=" + ViewCartAdapter.this.customer_id;
                    }
                    String para_str52 = "&vid=" + holder.tv_wid.getText().toString().trim();
                    if (ViewCartAdapter.this.customer_id.equalsIgnoreCase("")) {
                        ViewCartAdapter.this.baseUrl = ProductConfig.addcart + para_str12 + "&qty=1" + ViewCartAdapter.this.para_str3 + "&cart_type=add" + para_str52;
                    } else {
                        ViewCartAdapter.this.baseUrl = ProductConfig.useraddcart + para_str12 + "&qty=1" + ViewCartAdapter.this.para_str3 + "&cart_type=add" + para_str52;
                    }
                    Volley.newRequestQueue(ViewCartAdapter.this.mContext).add(new StringRequest(0, ViewCartAdapter.this.baseUrl, new Response.Listener<String>() {
                        public void onResponse(String response) {
                            Log.e("Response", response.toString());
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                    Toast.makeText(ViewCartAdapter.this.mContext, "Cart Add Failed", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                holder.tv_qty.setText(jsonResponse.getString("qty"));
                                ViewCartAdapter.this.cartModelList.get(position2).setProduct_quantity(jsonResponse.getString("qty"));
                                ((CartActivity) ViewCartAdapter.this.mContext).getCartCount();
                                ((CartActivity) ViewCartAdapter.this.mContext).setRecyclerview();
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
            }
        });
        holder.tv_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ViewCartAdapter.this.cartModelList.get(position2).getScid().equalsIgnoreCase("44")) {
                    Log.d("src", "Increasing value...");
                    ViewCartAdapter viewCartAdapter = ViewCartAdapter.this;
                    viewCartAdapter.f173i = Integer.valueOf(viewCartAdapter.cartModelList.get(position2).getProduct_quantity());
                    ViewCartAdapter viewCartAdapter2 = ViewCartAdapter.this;
                    viewCartAdapter2.f173i = Integer.valueOf(viewCartAdapter2.f173i.intValue() + 1);
                    String valueOf = String.valueOf(ViewCartAdapter.this.f173i);
                    ViewCartAdapter.this.customer_id = BSession.getInstance().getUser_id(ViewCartAdapter.this.mContext);
                    final Map<String, String> params = new HashMap<>();
                    String para_str1 = "?pid=" + holder.pId.getText().toString().trim();
                    if (ViewCartAdapter.this.customer_id.equalsIgnoreCase("")) {
                        ViewCartAdapter.this.para_str3 = "&ip_address=" + ViewCartAdapter.this.deviceid;
                    } else {
                        ViewCartAdapter.this.para_str3 = "&user_id=" + ViewCartAdapter.this.customer_id;
                    }
                    String para_str5 = "&vid=" + holder.tv_wid.getText().toString().trim();
                    String para_str6 = "&scid=" + ViewCartAdapter.this.cartModelList.get(position2).getScid();
                    if (ViewCartAdapter.this.customer_id.equalsIgnoreCase("")) {
                        ViewCartAdapter.this.baseUrl = ProductConfig.addcart + para_str1 + "&qty=1" + ViewCartAdapter.this.para_str3 + "&cart_type=add" + para_str5 + para_str6;
                    } else {
                        ViewCartAdapter.this.baseUrl = ProductConfig.useraddcart + para_str1 + "&qty=1" + ViewCartAdapter.this.para_str3 + "&cart_type=add" + para_str5 + para_str6;
                    }
                    Volley.newRequestQueue(ViewCartAdapter.this.mContext).add(new StringRequest(0, ViewCartAdapter.this.baseUrl, new Response.Listener<String>() {
                        public void onResponse(String response) {
                            Log.e("Response", response.toString());
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                if (jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) && jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                    holder.tv_qty.setText(jsonResponse.getString("qty"));
                                    ViewCartAdapter.this.cartModelList.get(position2).setProduct_quantity(jsonResponse.getString("qty"));
                                    ((CartActivity) ViewCartAdapter.this.mContext).getCartCount();
                                    ((CartActivity) ViewCartAdapter.this.mContext).setRecyclerview();
                                } else if (jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) && jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("0")) {
                                    Toast.makeText(ViewCartAdapter.this.mContext, "One Product one time only added", Toast.LENGTH_SHORT).show();
                                } else if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("5")) {
                                    Toast.makeText(ViewCartAdapter.this.mContext, "Cart Add Failed", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ViewCartAdapter.this.mContext, "You can purchase only Five products", Toast.LENGTH_SHORT).show();
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
                Log.d("src", "Increasing value...");
                ViewCartAdapter viewCartAdapter3 = ViewCartAdapter.this;
                viewCartAdapter3.f173i = Integer.valueOf(viewCartAdapter3.cartModelList.get(position2).getProduct_quantity());
                ViewCartAdapter viewCartAdapter4 = ViewCartAdapter.this;
                viewCartAdapter4.f173i = Integer.valueOf(viewCartAdapter4.f173i.intValue() + 1);
                String valueOf2 = String.valueOf(ViewCartAdapter.this.f173i);
                ViewCartAdapter.this.customer_id = BSession.getInstance().getUser_id(ViewCartAdapter.this.mContext);
                final Map<String, String> params2 = new HashMap<>();
                String para_str12 = "?pid=" + holder.pId.getText().toString().trim();
                if (ViewCartAdapter.this.customer_id.equalsIgnoreCase("")) {
                    ViewCartAdapter.this.para_str3 = "&ip_address=" + ViewCartAdapter.this.deviceid;
                } else {
                    ViewCartAdapter.this.para_str3 = "&user_id=" + ViewCartAdapter.this.customer_id;
                }
                String para_str52 = "&vid=" + holder.tv_wid.getText().toString().trim();
                if (ViewCartAdapter.this.customer_id.equalsIgnoreCase("")) {
                    ViewCartAdapter.this.baseUrl = ProductConfig.addcart + para_str12 + "&qty=1" + ViewCartAdapter.this.para_str3 + "&cart_type=add" + para_str52;
                } else {
                    ViewCartAdapter.this.baseUrl = ProductConfig.useraddcart + para_str12 + "&qty=1" + ViewCartAdapter.this.para_str3 + "&cart_type=add" + para_str52;
                }
                Volley.newRequestQueue(ViewCartAdapter.this.mContext).add(new StringRequest(0, ViewCartAdapter.this.baseUrl, new Response.Listener<String>() {
                    public void onResponse(String response) {
                        Log.e("Response", response.toString());
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                Toast.makeText(ViewCartAdapter.this.mContext, "Cart Add Failed", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            holder.tv_qty.setText(jsonResponse.getString("qty"));
                            ViewCartAdapter.this.cartModelList.get(position2).setProduct_quantity(jsonResponse.getString("qty"));
                            ((CartActivity) ViewCartAdapter.this.mContext).getCartCount();
                            ((CartActivity) ViewCartAdapter.this.mContext).setRecyclerview();
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
        holder.tv_minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ViewCartAdapter.this.cartModelList.get(position2).getScid().equalsIgnoreCase("44")) {
                    Log.d("src", "Decreasing value...");
                    ViewCartAdapter viewCartAdapter = ViewCartAdapter.this;
                    viewCartAdapter.f173i = Integer.valueOf(viewCartAdapter.cartModelList.get(position2).getProduct_quantity());
                    if (ViewCartAdapter.this.f173i.intValue() > 0) {
                        ViewCartAdapter viewCartAdapter2 = ViewCartAdapter.this;
                        viewCartAdapter2.f173i = Integer.valueOf(viewCartAdapter2.f173i.intValue() - 1);
                        String valueOf = String.valueOf(ViewCartAdapter.this.f173i);
                        ViewCartAdapter.this.customer_id = BSession.getInstance().getUser_id(ViewCartAdapter.this.mContext);
                        final Map<String, String> params = new HashMap<>();
                        String para_str1 = "?pid=" + holder.pId.getText().toString().trim();
                        if (ViewCartAdapter.this.customer_id.equalsIgnoreCase("")) {
                            ViewCartAdapter.this.para_str3 = "&ip_address=" + ViewCartAdapter.this.deviceid;
                        } else {
                            ViewCartAdapter.this.para_str3 = "&user_id=" + ViewCartAdapter.this.customer_id;
                        }
                        String para_str5 = "&vid=" + holder.tv_wid.getText().toString().trim();
                        String para_str6 = "&scid=" + ViewCartAdapter.this.cartModelList.get(position2).getScid();
                        if (ViewCartAdapter.this.customer_id.equalsIgnoreCase("")) {
                            ViewCartAdapter.this.baseUrl = ProductConfig.addcart + para_str1 + "&qty=1" + ViewCartAdapter.this.para_str3 + "&cart_type=sub" + para_str5 + para_str6;
                        } else {
                            ViewCartAdapter.this.baseUrl = ProductConfig.useraddcart + para_str1 + "&qty=1" + ViewCartAdapter.this.para_str3 + "&cart_type=sub" + para_str5 + para_str6;
                        }
                        Volley.newRequestQueue(ViewCartAdapter.this.mContext).add(new StringRequest(0, ViewCartAdapter.this.baseUrl, new Response.Listener<String>() {
                            public void onResponse(String response) {
                                Log.e("Response", response.toString());
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    if (jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) && jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                        if (jsonResponse.getString("message").equalsIgnoreCase("Successfully Updated")) {
                                            holder.tv_qty.setText(jsonResponse.getString("qty"));
                                            ViewCartAdapter.this.cartModelList.get(position2).setProduct_quantity(jsonResponse.getString("qty"));
                                            ((CartActivity) ViewCartAdapter.this.mContext).getCartCount();
                                            ((CartActivity) ViewCartAdapter.this.mContext).setRecyclerview();
                                        } else {
                                            ((CartActivity) ViewCartAdapter.this.mContext).getCartCount();
                                            ((CartActivity) ViewCartAdapter.this.mContext).setRecyclerview();
                                        }
                                    } else if (jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) && jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("0")) {
                                        Toast.makeText(ViewCartAdapter.this.mContext, "One Product one time only added", Toast.LENGTH_SHORT).show();
                                    } else if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("5")) {
                                        Toast.makeText(ViewCartAdapter.this.mContext, "Cart Add Failed", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ViewCartAdapter.this.mContext, "You can purchase only Five products", Toast.LENGTH_SHORT).show();
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
                    return;
                }
                Log.d("src", "Decreasing value...");
                ViewCartAdapter viewCartAdapter3 = ViewCartAdapter.this;
                viewCartAdapter3.f173i = Integer.valueOf(viewCartAdapter3.cartModelList.get(position2).getProduct_quantity());
                if (ViewCartAdapter.this.f173i.intValue() > 0) {
                    ViewCartAdapter viewCartAdapter4 = ViewCartAdapter.this;
                    viewCartAdapter4.f173i = Integer.valueOf(viewCartAdapter4.f173i.intValue() - 1);
                    String valueOf2 = String.valueOf(ViewCartAdapter.this.f173i);
                    ViewCartAdapter.this.customer_id = BSession.getInstance().getUser_id(ViewCartAdapter.this.mContext);
                    final Map<String, String> params2 = new HashMap<>();
                    String para_str12 = "?pid=" + holder.pId.getText().toString().trim();
                    if (ViewCartAdapter.this.customer_id.equalsIgnoreCase("")) {
                        ViewCartAdapter.this.para_str3 = "&ip_address=" + ViewCartAdapter.this.deviceid;
                    } else {
                        ViewCartAdapter.this.para_str3 = "&user_id=" + ViewCartAdapter.this.customer_id;
                    }
                    String para_str52 = "&vid=" + holder.tv_wid.getText().toString().trim();
                    if (ViewCartAdapter.this.customer_id.equalsIgnoreCase("")) {
                        ViewCartAdapter.this.baseUrl = ProductConfig.addcart + para_str12 + "&qty=1" + ViewCartAdapter.this.para_str3 + "&cart_type=sub" + para_str52;
                    } else {
                        ViewCartAdapter.this.baseUrl = ProductConfig.useraddcart + para_str12 + "&qty=1" + ViewCartAdapter.this.para_str3 + "&cart_type=sub" + para_str52;
                    }
                    Volley.newRequestQueue(ViewCartAdapter.this.mContext).add(new StringRequest(0, ViewCartAdapter.this.baseUrl, new Response.Listener<String>() {
                        public void onResponse(String response) {
                            Log.e("Response", response.toString());
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                if (jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) && jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                    if (jsonResponse.getString("message").equalsIgnoreCase("Successfully Updated")) {
                                        holder.tv_qty.setText(jsonResponse.getString("qty"));
                                        ViewCartAdapter.this.cartModelList.get(position2).setProduct_quantity(jsonResponse.getString("qty"));
                                        ((CartActivity) ViewCartAdapter.this.mContext).getCartCount();
                                        ((CartActivity) ViewCartAdapter.this.mContext).setRecyclerview();
                                        return;
                                    }
                                    ((CartActivity) ViewCartAdapter.this.mContext).getCartCount();
                                    ((CartActivity) ViewCartAdapter.this.mContext).setRecyclerview();
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
                            return params2;
                        }
                    });
                }
            }
        });
    }



    public int getItemCount() {
        return this.cartModelList.size();
    }

    public void removeItem(int position2) {
        this.cartModelList.remove(position2);
        notifyItemRemoved(position2);
    }

    public class MailViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMenu;
        ImageView iv_delete;
        TextView ms_weight;
        TextView pId;
        RelativeLayout rel_add;
        TextView stack;
        TextView tv_add;
        TextView tv_delivery;
        TextView tv_minus;
        TextView tv_mrp;
        TextView tv_offer;
        TextView tv_pId;
        TextView tv_pPrice;
        TextView tv_pTitle;
        TextView tv_plus;
        TextView tv_pqyt;
        TextView tv_price;
        TextView tv_qty;
        TextView tv_title;
        TextView tv_wgt;
        TextView tv_wid;

        public MailViewHolder(View itemView) {
            super(itemView);
            this.ivMenu = (ImageView) itemView.findViewById(R.id.ivMenu);
            this.iv_delete = (ImageView) itemView.findViewById(R.id.iv_delete);
            this.tv_pTitle = (TextView) itemView.findViewById(R.id.tv_pTitle);
            this.tv_pPrice = (TextView) itemView.findViewById(R.id.tv_pPrice);
            this.tv_pqyt = (TextView) itemView.findViewById(R.id.tv_pqyt);
            this.tv_wgt = (TextView) itemView.findViewById(R.id.tv_wgt);
            this.tv_delivery = (TextView) itemView.findViewById(R.id.tv_delivery);
            this.tv_pId = (TextView) itemView.findViewById(R.id.tv_pId);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_pTitle);
            this.tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            this.ms_weight = (TextView) itemView.findViewById(R.id.ms_weight);
            this.tv_qty = (TextView) itemView.findViewById(R.id.tv_qty);
            this.tv_plus = (TextView) itemView.findViewById(R.id.tv_plus);
            this.tv_minus = (TextView) itemView.findViewById(R.id.tv_minus);
            this.tv_add = (TextView) itemView.findViewById(R.id.tv_add1);
            this.stack = (TextView) itemView.findViewById(R.id.stack);
            this.tv_wid = (TextView) itemView.findViewById(R.id.tv_wid);
            this.rel_add = (RelativeLayout) itemView.findViewById(R.id.rel_add);
            this.pId = (TextView) itemView.findViewById(R.id.pId);
        }
    }
}
