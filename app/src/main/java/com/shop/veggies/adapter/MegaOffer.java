package com.shop.veggies.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.exifinterface.media.ExifInterface;
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
import com.shop.veggies.activity.MegaOfferActivity;
import com.shop.veggies.activity.OfferActivity;
import com.shop.veggies.model.ProductsModel;
import com.shop.veggies.model.WeightModel;
import com.shop.veggies.utils.BSession;
import com.shop.veggies.utils.ProductConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MegaOffer extends RecyclerView.Adapter<MegaOffer.MailViewHolder> {
    public static Dialog dialog;
    String[] SPINNERLIST = {"250 g", "500 g", "1 kg", "5 kg"};
    AdapterRe adapterRe;
    ArrayAdapter<String> arrayAdapter;
    String baseUrl;
    String customer_id = "";
    String deviceid;
    String editqut;

    /* renamed from: i */
    Integer f168i;
    String ipaddress;
    LinearLayoutManager linearLayoutManager;
    private BottomSheetDialog mBottomSheetDialog;
    /* access modifiers changed from: private */
    public Context mContext;
    String mrp;
    String name;
    String para_str;
    String para_str3;
    String pid = "";
    String price;
    ProductsModel productsModel = new ProductsModel();
    List<ProductsModel> productsModelList;
    List<ProductsModel> productsModelListftu = new ArrayList();
    String qty = "";
    String scid;
    String stackcoit;
    String type;
    String wid;

    public MegaOffer(Context mContext2, List<ProductsModel> productsModelList2) {
        this.mContext = mContext2;
        this.productsModelList = productsModelList2;
    }

    public MailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_offer_list, parent, false));
    }

    public void onBindViewHolder(final MailViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final ProductsModel model = this.productsModelList.get(position);
        this.deviceid = Settings.Secure.getString(this.mContext.getContentResolver(), "android_id");
        this.name = this.productsModelList.get(position).getProduct_name();
        this.price = this.productsModelList.get(position).getProduct_price();
        this.mrp = this.productsModelList.get(position).getProduct_mrp();
        this.qty = this.productsModelList.get(position).getProduct_quantity();
        this.pid = this.productsModelList.get(position).getProduct_id();
        this.stackcoit = this.productsModelList.get(position).getProduct_status();
        this.scid = this.productsModelList.get(position).getScid();
        ((RequestBuilder) Glide.with(this.mContext).load(this.productsModelList.get(position).getProduct_image()).placeholder((int) R.drawable.dummy)).into(holder.ivMenu);
        holder.tv_mrp.setPaintFlags(holder.tv_mrp.getPaintFlags() | 16);
        holder.tv_title.setText(this.productsModelList.get(position).getProduct_name());
        holder.tv_qty.setText(this.productsModelList.get(position).getProduct_quantity());
        holder.tv_pId.setText(this.productsModelList.get(position).getProduct_id());
        if (this.productsModelList.get(position).getOffer_status().contentEquals("1")) {
            holder.fl_offer.setVisibility(View.VISIBLE);
        } else if (this.productsModelList.get(position).getOffer_status().contentEquals("0")) {
            holder.fl_offer.setVisibility(View.GONE);
        }
        TextView textView = holder.tv_offer;
        textView.setText(this.productsModelList.get(position).getProduct_offer() + " % \n off");
        TextView textView2 = holder.tv_price;
        textView2.setText("₹ " + this.price);
        TextView textView3 = holder.tv_mrp;
        textView3.setText(" ₹ " + this.mrp);
        new ArrayList();
        List<WeightModel> weightModelList = model.getWeight();
        TextView textView4 = holder.tv_mrp;
        textView4.setText(" ₹ " + weightModelList.get(0).getMrp());
        TextView textView5 = holder.tv_price;
        textView5.setText(" ₹ " + weightModelList.get(0).getWeb_price());
        holder.ms_weight.setText(weightModelList.get(0).getWeb_title());
        holder.tv_wid.setText(weightModelList.get(0).getWid());
        if (weightModelList.size() > 1) {
            holder.ms_weight_img.setVisibility(View.VISIBLE);
            holder.weight_rel.setBackground(this.mContext.getDrawable(R.drawable.text_border_color));
            holder.weight_rel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    MegaOffer.dialog = new Dialog(MegaOffer.this.mContext);
                    MegaOffer.dialog.setCancelable(true);
                    MegaOffer.dialog.setContentView(R.layout.weight_listview_popup);
                    RecyclerView recyclerView = (RecyclerView) MegaOffer.dialog.findViewById(R.id.rv_weight_list);
                    MegaOffer.this.productsModelListftu = new ArrayList();
                    List<WeightModel> weightModelsTemp = model.getWeight();
                    new ProductsModel();
                    ((TextView) MegaOffer.dialog.findViewById(R.id.name_product)).setText(model.getProduct_name());
                    for (WeightModel weightModel : weightModelsTemp) {
                        ProductsModel productsModelTemp = new ProductsModel();
                        productsModelTemp.setWeight_wid(weightModel.getWid());
                        productsModelTemp.setWeight_webtitle(weightModel.getWeb_title());
                        productsModelTemp.setWeight_webprice(weightModel.getWeb_price());
                        productsModelTemp.setWeight_webmrp(weightModel.getMrp());
                        MegaOffer.this.productsModelListftu.add(productsModelTemp);
                    }
                    MegaOffer megaOffer = MegaOffer.this;
                    megaOffer.adapterRe = new AdapterRe(megaOffer.mContext, MegaOffer.this.productsModelListftu, holder.ms_weight, MegaOffer.dialog, holder.tv_mrp, holder.tv_price, holder.tv_wid);
                    recyclerView.setHasFixedSize(true);
                    MegaOffer megaOffer2 = MegaOffer.this;
                    megaOffer2.linearLayoutManager = new LinearLayoutManager(megaOffer2.mContext, RecyclerView.VERTICAL, false);
                    recyclerView.setLayoutManager(MegaOffer.this.linearLayoutManager);
                    recyclerView.setAdapter(MegaOffer.this.adapterRe);
                    MegaOffer.dialog.show();
                }
            });
        }
        if (this.qty.equals("0")) {
            holder.tv_plus.setVisibility(View.GONE);
            holder.tv_qty.setVisibility(View.GONE);
            holder.tv_add.setVisibility(View.VISIBLE);
            holder.tv_minus.setVisibility(View.GONE);
        } else {
            holder.tv_plus.setVisibility(View.VISIBLE);
            holder.tv_qty.setVisibility(View.VISIBLE);
            holder.tv_add.setVisibility(View.GONE);
            holder.tv_minus.setVisibility(View.VISIBLE);
            holder.tv_qty.setText(this.qty);
        }
        if (!this.qty.equalsIgnoreCase("1")) {
            holder.tv_add.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (holder.ms_weight.getText().toString().isEmpty()) {
                        holder.ms_weight.setError("Choose any weight");
                    } else if (MegaOffer.this.scid.equalsIgnoreCase("44")) {
                        holder.tv_plus.setVisibility(View.VISIBLE);
                        holder.tv_qty.setVisibility(View.VISIBLE);
                        holder.tv_add.setVisibility(View.GONE);
                        holder.tv_minus.setVisibility(View.VISIBLE);
                        Log.d("src", "Increasing value...");
                        MegaOffer megaOffer = MegaOffer.this;
                        megaOffer.f168i = Integer.valueOf(megaOffer.productsModelList.get(position).getProduct_quantity());
                        MegaOffer megaOffer2 = MegaOffer.this;
                        megaOffer2.f168i = Integer.valueOf(megaOffer2.f168i.intValue() + 1);
                        String _stringVal = String.valueOf(MegaOffer.this.f168i);
                        holder.tv_qty.setText(_stringVal);
                        MegaOffer.this.productsModelList.get(position).setProduct_quantity(_stringVal);
                        MegaOffer.this.customer_id = BSession.getInstance().getUser_id(MegaOffer.this.mContext);
                        final Map<String, String> params = new HashMap<>();
                        String para_str1 = "?pid=" + holder.tv_pId.getText().toString().trim();
                        if (MegaOffer.this.customer_id.equalsIgnoreCase("")) {
                            MegaOffer.this.para_str3 = "&ip_address=" + MegaOffer.this.deviceid;
                        } else {
                            MegaOffer.this.para_str3 = "&user_id=" + MegaOffer.this.customer_id;
                        }
                        String para_str5 = "&vid=" + holder.tv_wid.getText().toString().trim();
                        String para_str6 = "&scid=" + MegaOffer.this.productsModelList.get(position).getScid();
                        if (MegaOffer.this.customer_id.equalsIgnoreCase("")) {
                            MegaOffer.this.baseUrl = ProductConfig.addcart + para_str1 + "&qty=1" + MegaOffer.this.para_str3 + "&cart_type=add" + para_str5 + para_str6;
                        } else {
                            MegaOffer.this.baseUrl = ProductConfig.useraddcart + para_str1 + "&qty=1" + MegaOffer.this.para_str3 + "&cart_type=add" + para_str5 + para_str6;
                        }
                        Volley.newRequestQueue(MegaOffer.this.mContext).add(new StringRequest(0, MegaOffer.this.baseUrl, new Response.Listener<String>() {
                            public void onResponse(String response) {
                                Log.e("Response", response.toString());
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    if (jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) && jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                        holder.tv_qty.setText(jsonResponse.getString("qty"));
                                        MegaOffer.this.productsModelList.get(position).setProduct_quantity(jsonResponse.getString("qty"));
                                        ((MegaOfferActivity) MegaOffer.this.mContext).getCartCount();
                                        ((MegaOfferActivity) MegaOffer.this.mContext).setRecyclerview();
                                    } else if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("0")) {
                                        if (jsonResponse.has(FirebaseAnalytics.Param.SUCCESS)) {
                                            if (jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("5")) {
                                                Toast.makeText(MegaOffer.this.mContext, "You can purchase only Five products", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        }
                                        if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals(ExifInterface.GPS_MEASUREMENT_2D)) {
                                            Toast.makeText(MegaOffer.this.mContext, "Cart Add Failed", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        holder.tv_plus.setVisibility(View.GONE);
                                        holder.tv_qty.setVisibility(View.GONE);
                                        holder.tv_add.setVisibility(View.VISIBLE);
                                        holder.tv_minus.setVisibility(View.GONE);
                                        Toast.makeText(MegaOffer.this.mContext, "You can purchase only Five products", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MegaOffer.this.mContext, "One Product one time only added", Toast.LENGTH_SHORT).show();
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
                        holder.tv_plus.setVisibility(View.VISIBLE);
                        holder.tv_qty.setVisibility(View.VISIBLE);
                        holder.tv_add.setVisibility(View.GONE);
                        holder.tv_minus.setVisibility(View.VISIBLE);
                        Log.d("src", "Increasing value...");
                        MegaOffer megaOffer3 = MegaOffer.this;
                        megaOffer3.f168i = Integer.valueOf(megaOffer3.productsModelList.get(position).getProduct_quantity());
                        MegaOffer megaOffer4 = MegaOffer.this;
                        megaOffer4.f168i = Integer.valueOf(megaOffer4.f168i.intValue() + 1);
                        String _stringVal2 = String.valueOf(MegaOffer.this.f168i);
                        holder.tv_qty.setText(_stringVal2);
                        MegaOffer.this.productsModelList.get(position).setProduct_quantity(_stringVal2);
                        MegaOffer.this.customer_id = BSession.getInstance().getUser_id(MegaOffer.this.mContext);
                        final Map<String, String> params2 = new HashMap<>();
                        String para_str12 = "?pid=" + holder.tv_pId.getText().toString().trim();
                        if (MegaOffer.this.customer_id.equalsIgnoreCase("")) {
                            MegaOffer.this.para_str3 = "&ip_address=" + MegaOffer.this.deviceid;
                        } else {
                            MegaOffer.this.para_str3 = "&user_id=" + MegaOffer.this.customer_id;
                        }
                        String para_str52 = "&vid=" + holder.tv_wid.getText().toString().trim();
                        if (MegaOffer.this.customer_id.equalsIgnoreCase("")) {
                            MegaOffer.this.baseUrl = ProductConfig.addcart + para_str12 + "&qty=1" + MegaOffer.this.para_str3 + "&cart_type=add" + para_str52;
                        } else {
                            MegaOffer.this.baseUrl = ProductConfig.useraddcart + para_str12 + "&qty=1" + MegaOffer.this.para_str3 + "&cart_type=add" + para_str52;
                        }
                        Volley.newRequestQueue(MegaOffer.this.mContext).add(new StringRequest(0, MegaOffer.this.baseUrl, new Response.Listener<String>() {
                            public void onResponse(String response) {
                                Log.e("Response", response.toString());
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                        Toast.makeText(MegaOffer.this.mContext, "Cart Add Failed", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    holder.tv_qty.setText(jsonResponse.getString("qty"));
                                    MegaOffer.this.productsModelList.get(position).setProduct_quantity(jsonResponse.getString("qty"));
                                    ((OfferActivity) MegaOffer.this.mContext).getCartCount();
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
        holder.tv_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MegaOffer.this.scid.equalsIgnoreCase("44")) {
                    Log.d("src", "Increasing value...");
                    MegaOffer megaOffer = MegaOffer.this;
                    megaOffer.f168i = Integer.valueOf(megaOffer.productsModelList.get(position).getProduct_quantity());
                    MegaOffer megaOffer2 = MegaOffer.this;
                    megaOffer2.f168i = Integer.valueOf(megaOffer2.f168i.intValue() + 1);
                    String valueOf = String.valueOf(MegaOffer.this.f168i);
                    MegaOffer.this.customer_id = BSession.getInstance().getUser_id(MegaOffer.this.mContext);
                    final Map<String, String> params = new HashMap<>();
                    String para_str1 = "?pid=" + holder.tv_pId.getText().toString().trim();
                    if (MegaOffer.this.customer_id.equalsIgnoreCase("")) {
                        MegaOffer.this.para_str3 = "&ip_address=" + MegaOffer.this.deviceid;
                    } else {
                        MegaOffer.this.para_str3 = "&user_id=" + MegaOffer.this.customer_id;
                    }
                    String para_str5 = "&vid=" + holder.tv_wid.getText().toString().trim();
                    String para_str6 = "&scid=" + MegaOffer.this.productsModelList.get(position).getScid();
                    if (MegaOffer.this.customer_id.equalsIgnoreCase("")) {
                        MegaOffer.this.baseUrl = ProductConfig.addcart + para_str1 + "&qty=1" + MegaOffer.this.para_str3 + "&cart_type=add" + para_str5 + para_str6;
                    } else {
                        MegaOffer.this.baseUrl = ProductConfig.useraddcart + para_str1 + "&qty=1" + MegaOffer.this.para_str3 + "&cart_type=add" + para_str5 + para_str6;
                    }
                    Volley.newRequestQueue(MegaOffer.this.mContext).add(new StringRequest(0, MegaOffer.this.baseUrl, new Response.Listener<String>() {
                        public void onResponse(String response) {
                            Log.e("Response", response.toString());
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                if (jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) && jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                    holder.tv_qty.setText(jsonResponse.getString("qty"));
                                    MegaOffer.this.productsModelList.get(position).setProduct_quantity(jsonResponse.getString("qty"));
                                    ((MegaOfferActivity) MegaOffer.this.mContext).getCartCount();
                                    ((MegaOfferActivity) MegaOffer.this.mContext).setRecyclerview();
                                } else if (jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) && jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("0")) {
                                    Toast.makeText(MegaOffer.this.mContext, "One Product one time only added", Toast.LENGTH_SHORT).show();
                                } else if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("5")) {
                                    Toast.makeText(MegaOffer.this.mContext, "Cart Add Failed", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MegaOffer.this.mContext, "You can purchase only Five products", Toast.LENGTH_SHORT).show();
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
                MegaOffer megaOffer3 = MegaOffer.this;
                megaOffer3.f168i = Integer.valueOf(megaOffer3.productsModelList.get(position).getProduct_quantity());
                MegaOffer megaOffer4 = MegaOffer.this;
                megaOffer4.f168i = Integer.valueOf(megaOffer4.f168i.intValue() + 1);
                String valueOf2 = String.valueOf(MegaOffer.this.f168i);
                MegaOffer.this.customer_id = BSession.getInstance().getUser_id(MegaOffer.this.mContext);
                final Map<String, String> params2 = new HashMap<>();
                String para_str12 = "?pid=" + holder.tv_pId.getText().toString().trim();
                if (MegaOffer.this.customer_id.equalsIgnoreCase("")) {
                    MegaOffer.this.para_str3 = "&ip_address=" + MegaOffer.this.deviceid;
                } else {
                    MegaOffer.this.para_str3 = "&user_id=" + MegaOffer.this.customer_id;
                }
                String para_str52 = "&vid=" + holder.tv_wid.getText().toString().trim();
                if (MegaOffer.this.customer_id.equalsIgnoreCase("")) {
                    MegaOffer.this.baseUrl = ProductConfig.addcart + para_str12 + "&qty=1" + MegaOffer.this.para_str3 + "&cart_type=add" + para_str52;
                } else {
                    MegaOffer.this.baseUrl = ProductConfig.useraddcart + para_str12 + "&qty=1" + MegaOffer.this.para_str3 + "&cart_type=add" + para_str52;
                }
                Volley.newRequestQueue(MegaOffer.this.mContext).add(new StringRequest(0, MegaOffer.this.baseUrl, new Response.Listener<String>() {
                    public void onResponse(String response) {
                        Log.e("Response", response.toString());
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                Toast.makeText(MegaOffer.this.mContext, "Cart Add Failed", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            holder.tv_qty.setText(jsonResponse.getString("qty"));
                            MegaOffer.this.productsModelList.get(position).setProduct_quantity(jsonResponse.getString("qty"));
                            ((OfferActivity) MegaOffer.this.mContext).getCartCount();
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
                if (MegaOffer.this.scid.equalsIgnoreCase("44")) {
                    Log.d("src", "Decreasing value...");
                    MegaOffer megaOffer = MegaOffer.this;
                    megaOffer.f168i = Integer.valueOf(megaOffer.productsModelList.get(position).getProduct_quantity());
                    if (MegaOffer.this.f168i.intValue() > 0) {
                        MegaOffer megaOffer2 = MegaOffer.this;
                        megaOffer2.f168i = Integer.valueOf(megaOffer2.f168i.intValue() - 1);
                        String valueOf = String.valueOf(MegaOffer.this.f168i);
                        MegaOffer.this.customer_id = BSession.getInstance().getUser_id(MegaOffer.this.mContext);
                        final Map<String, String> params = new HashMap<>();
                        String para_str1 = "?pid=" + holder.tv_pId.getText().toString().trim();
                        if (MegaOffer.this.customer_id.equalsIgnoreCase("")) {
                            MegaOffer.this.para_str3 = "&ip_address=" + MegaOffer.this.deviceid;
                        } else {
                            MegaOffer.this.para_str3 = "&user_id=" + MegaOffer.this.customer_id;
                        }
                        String para_str5 = "&vid=" + holder.tv_wid.getText().toString().trim();
                        String para_str6 = "&scid=" + MegaOffer.this.productsModelList.get(position).getScid();
                        if (MegaOffer.this.customer_id.equalsIgnoreCase("")) {
                            MegaOffer.this.baseUrl = ProductConfig.addcart + para_str1 + "&qty=1" + MegaOffer.this.para_str3 + "&cart_type=sub" + para_str5 + para_str6;
                        } else {
                            MegaOffer.this.baseUrl = ProductConfig.useraddcart + para_str1 + "&qty=1" + MegaOffer.this.para_str3 + "&cart_type=sub" + para_str5 + para_str6;
                        }
                        Volley.newRequestQueue(MegaOffer.this.mContext).add(new StringRequest(0, MegaOffer.this.baseUrl, new Response.Listener<String>() {
                            public void onResponse(String response) {
                                Log.e("Response", response.toString());
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    if (jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) && jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals(ExifInterface.GPS_MEASUREMENT_3D)) {
                                        ((MegaOfferActivity) MegaOffer.this.mContext).getCartCount();
                                        ((MegaOfferActivity) MegaOffer.this.mContext).setRecyclerview();
                                    } else if (jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) && jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals(ExifInterface.GPS_MEASUREMENT_2D)) {
                                        holder.tv_plus.setVisibility(View.GONE);
                                        holder.tv_qty.setVisibility(View.GONE);
                                        holder.tv_add.setVisibility(View.VISIBLE);
                                        holder.tv_minus.setVisibility(View.GONE);
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
                MegaOffer megaOffer3 = MegaOffer.this;
                megaOffer3.f168i = Integer.valueOf(megaOffer3.productsModelList.get(position).getProduct_quantity());
                if (MegaOffer.this.f168i.intValue() > 0) {
                    MegaOffer megaOffer4 = MegaOffer.this;
                    megaOffer4.f168i = Integer.valueOf(megaOffer4.f168i.intValue() - 1);
                    String valueOf2 = String.valueOf(MegaOffer.this.f168i);
                    MegaOffer.this.customer_id = BSession.getInstance().getUser_id(MegaOffer.this.mContext);
                    final Map<String, String> params2 = new HashMap<>();
                    String para_str12 = "?pid=" + holder.tv_pId.getText().toString().trim();
                    if (MegaOffer.this.customer_id.equalsIgnoreCase("")) {
                        MegaOffer.this.para_str3 = "&ip_address=" + MegaOffer.this.deviceid;
                    } else {
                        MegaOffer.this.para_str3 = "&user_id=" + MegaOffer.this.customer_id;
                    }
                    String para_str52 = "&vid=" + holder.tv_wid.getText().toString().trim();
                    if (MegaOffer.this.customer_id.equalsIgnoreCase("")) {
                        MegaOffer.this.baseUrl = ProductConfig.addcart + para_str12 + "&qty=1" + MegaOffer.this.para_str3 + "&cart_type=sub" + para_str52;
                    } else {
                        MegaOffer.this.baseUrl = ProductConfig.useraddcart + para_str12 + "&qty=1" + MegaOffer.this.para_str3 + "&cart_type=sub" + para_str52;
                    }
                    Volley.newRequestQueue(MegaOffer.this.mContext).add(new StringRequest(0, MegaOffer.this.baseUrl, new Response.Listener<String>() {
                        public void onResponse(String response) {
                            Log.e("Response", response.toString());
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                if (jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) && jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                    holder.tv_qty.setText(jsonResponse.getString("qty"));
                                    MegaOffer.this.productsModelList.get(position).setProduct_quantity(jsonResponse.getString("qty"));
                                    ((OfferActivity) MegaOffer.this.mContext).getCartCount();
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
                    return;
                }
                Log.d("src", "Value can't be less than 0");
            }
        });
    }

//    private void NetwordDetect() {
//        boolean WIFI = false;
//        boolean MOBILE = false;
//        for (NetworkInfo netInfo : ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getAllNetworkInfo()) {
//            if (netInfo.getTypeName().equalsIgnoreCase("WIFI") && netInfo.isConnected()) {
//                WIFI = true;
//            }
//            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE") && netInfo.isConnected()) {
//                MOBILE = true;
//            }
//        }
//        if (WIFI) {
//            this.ipaddress = GetDeviceipWiFiData();
//        }
//        if (MOBILE) {
//            this.ipaddress = GetDeviceipMobileData();
//        }
//    }

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

//    public String GetDeviceipWiFiData() {
//        return Formatter.formatIpAddress(((WifiManager) this.mContext.getSystemService("wifi")).getConnectionInfo().getIpAddress());
//    }

    public int getItemCount() {
        return this.productsModelList.size();
    }

    public class MailViewHolder extends RecyclerView.ViewHolder {
        FrameLayout fl_offer;
        ImageView ivMenu;
        LinearLayout ll_parent;
        LinearLayout llay_cart;
        TextView ms_weight;
        ImageView ms_weight_img;
        TextView stack;
        TextView tv_add;
        TextView tv_minus;
        TextView tv_mrp;
        TextView tv_offer;
        TextView tv_pId;
        TextView tv_plus;
        TextView tv_price;
        TextView tv_qty;
        TextView tv_title;
        TextView tv_wid;
        RelativeLayout weight_rel;

        public MailViewHolder(View itemView) {
            super(itemView);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_pTitle);
            this.tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            this.ms_weight = (TextView) itemView.findViewById(R.id.ms_weight);
            this.tv_qty = (TextView) itemView.findViewById(R.id.tv_qty);
            this.tv_plus = (TextView) itemView.findViewById(R.id.tv_plus);
            this.tv_minus = (TextView) itemView.findViewById(R.id.tv_minus);
            this.ivMenu = (ImageView) itemView.findViewById(R.id.ivMenu);
            this.tv_pId = (TextView) itemView.findViewById(R.id.tv_pId);
            this.tv_wid = (TextView) itemView.findViewById(R.id.tv_wid);
            this.tv_mrp = (TextView) itemView.findViewById(R.id.tv_mrp);
            this.fl_offer = (FrameLayout) itemView.findViewById(R.id.fl_offer);
            this.tv_offer = (TextView) itemView.findViewById(R.id.tv_offer);
            this.ll_parent = (LinearLayout) itemView.findViewById(R.id.ll_parent);
            this.tv_add = (TextView) itemView.findViewById(R.id.tv_add1);
            this.stack = (TextView) itemView.findViewById(R.id.stack);
            this.llay_cart = (LinearLayout) itemView.findViewById(R.id.llay_cart);
            this.ms_weight_img = (ImageView) itemView.findViewById(R.id.ms_weight_img);
            this.weight_rel = (RelativeLayout) itemView.findViewById(R.id.weight_rel);
        }
    }

    class AdapterRe extends RecyclerView.Adapter<AdapterRe.MyViewHolder> {
        Dialog dialog_popup;
        private LayoutInflater inflater;
        Context mContext;
        TextView ms_weight_popup;
        private String[] myImageNameList;
        String namee;
        List<ProductsModel> productsModelListftu = new ArrayList();
        TextView tv_mrp_popup;
        TextView tv_price_popup;
        TextView tv_wid_popup;
        String weight_count;
        String weight_id;
        String weight_mrp;
        String weight_price;
        String weight_title;

        public AdapterRe(Context ctx, List<ProductsModel> productsModelList, TextView ms_weight_popup2, Dialog dialog_popup2, TextView tv_mrp_popup2, TextView tv_price_popup2, TextView tv_wid_popup2) {
            this.mContext = ctx;
            this.productsModelListftu = productsModelList;
            this.ms_weight_popup = ms_weight_popup2;
            this.dialog_popup = dialog_popup2;
            this.tv_mrp_popup = tv_mrp_popup2;
            this.tv_price_popup = tv_price_popup2;
            this.tv_wid_popup = tv_wid_popup2;
        }

        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.weight_list_items, parent, false));
        }

        public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            ProductsModel models = this.productsModelListftu.get(position);
            this.weight_id = this.productsModelListftu.get(position).getWeight_wid();
            this.weight_title = this.productsModelListftu.get(position).getWeight_webtitle();
            this.weight_mrp = this.productsModelListftu.get(position).getWeight_webmrp();
            this.weight_price = this.productsModelListftu.get(position).getWeight_webprice();
            this.weight_count = this.productsModelListftu.get(position).getWeight_count();
            if (this.weight_mrp.equalsIgnoreCase("0")) {
                holder.wid.setText(this.productsModelListftu.get(position).getWeight_webtitle());
                TextView textView = holder.price;
                textView.setText(" ₹ " + this.productsModelListftu.get(position).getWeight_webprice());
                TextView textView2 = holder.mrp;
                textView2.setText("MRP ₹ " + this.productsModelListftu.get(position).getWeight_webmrp());
                holder.tv_wid.setText(this.productsModelListftu.get(position).getWeight_wid());
                holder.mrp.setPaintFlags(holder.mrp.getPaintFlags() | 16);
                MegaOffer.this.wid = models.getWeight_wid();
            } else {
                holder.wid.setText(this.productsModelListftu.get(position).getWeight_webtitle());
                TextView textView3 = holder.mrp;
                textView3.setText("MRP ₹ " + this.productsModelListftu.get(position).getWeight_webmrp());
                TextView textView4 = holder.price;
                textView4.setText(" ₹ " + this.productsModelListftu.get(position).getWeight_webprice());
                holder.tv_wid.setText(this.productsModelListftu.get(position).getWeight_wid());
                MegaOffer.this.wid = this.productsModelListftu.get(position).getWeight_wid();
                holder.mrp.setPaintFlags(holder.mrp.getPaintFlags() | 16);
            }
            holder.lin_recy.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    AdapterRe.this.ms_weight_popup.setText(AdapterRe.this.productsModelListftu.get(position).getWeight_webtitle());
                    AdapterRe.this.tv_wid_popup.setText(AdapterRe.this.productsModelListftu.get(position).getWeight_wid());
                    TextView textView = AdapterRe.this.tv_mrp_popup;
                    textView.setText("MRP ₹ " + AdapterRe.this.productsModelListftu.get(position).getWeight_webmrp());
                    TextView textView2 = AdapterRe.this.tv_price_popup;
                    textView2.setText(" ₹ " + AdapterRe.this.productsModelListftu.get(position).getWeight_webprice());
                    AdapterRe.this.dialog_popup.dismiss();
                }
            });
        }

        public int getItemCount() {
            return this.productsModelListftu.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView count;
            LinearLayout lin_recy;
            TextView mrp;
            TextView price;
            TextView title;
            TextView tv_wid;
            TextView wid;

            public MyViewHolder(View itemView) {
                super(itemView);
                this.wid = (TextView) itemView.findViewById(R.id.graa);
                this.mrp = (TextView) itemView.findViewById(R.id.tv_mrp);
                this.price = (TextView) itemView.findViewById(R.id.tv_price);
                this.tv_wid = (TextView) itemView.findViewById(R.id.tv_wid);
                this.lin_recy = (LinearLayout) itemView.findViewById(R.id.lin_recy);
            }
        }
    }
}
