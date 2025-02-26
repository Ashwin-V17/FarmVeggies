package com.shop.veggies.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
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

import androidx.core.internal.view.SupportMenu;
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
import com.shop.veggies.activity.OfferActivity;
import com.shop.veggies.activity.ProductViewActivity;
import com.shop.veggies.model.ProductsModel;
import com.shop.veggies.model.WeightModel;
import com.shop.veggies.utils.BSession;
import com.shop.veggies.utils.ProductConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MailViewHolder> {
    public static Dialog dialog;
    String[] SPINNERLIST = {"250 g", "500 g", "1 kg", "5 kg"};
    AdapterRe adapterRe;
    ArrayAdapter<String> arrayAdapter;
    String baseUrl;
    String customer_id = "";
    String deviceid;
    String editqut;

    /* renamed from: i */
    Integer f169i;
    String ipaddress;
    LinearLayoutManager linearLayoutManager;
    private BottomSheetDialog mBottomSheetDialog;
    /* access modifiers changed from: private */
    public Context mContext;
    String mrp;
    String name;
    String para_str;
    String para_str1;
    String para_str2;
    String para_str3;
    String para_str4;
    String para_str5;
    String para_str6;
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

    public OfferAdapter(Context mContext2, List<ProductsModel> productsModelList2) {
        this.mContext = mContext2;
        this.productsModelList = productsModelList2;
    }

    public MailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_offer_list, parent, false));
    }

    @SuppressLint("RecyclerView")
    public void onBindViewHolder(final MailViewHolder holder, final int position) {
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
            holder.weight_rel.setBackground(this.mContext.getDrawable(R.drawable.blac_transborder));
            holder.weight_rel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    OfferAdapter.dialog = new Dialog(OfferAdapter.this.mContext);
                    OfferAdapter.dialog.setCancelable(true);
                    OfferAdapter.dialog.setContentView(R.layout.weight_listview_popup);
                    RecyclerView recyclerView = (RecyclerView) OfferAdapter.dialog.findViewById(R.id.rv_weight_list);
                    OfferAdapter.this.productsModelListftu = new ArrayList();
                    List<WeightModel> weightModelsTemp = model.getWeight();
                    new ProductsModel();
                    ((TextView) OfferAdapter.dialog.findViewById(R.id.name_product)).setText(model.getProduct_name());
                    for (WeightModel weightModel : weightModelsTemp) {
                        ProductsModel productsModelTemp = new ProductsModel();
                        productsModelTemp.setWeight_wid(weightModel.getWid());
                        productsModelTemp.setWeight_webtitle(weightModel.getWeb_title());
                        productsModelTemp.setWeight_webprice(weightModel.getWeb_price());
                        productsModelTemp.setWeight_webmrp(weightModel.getMrp());
                        OfferAdapter.this.productsModelListftu.add(productsModelTemp);
                    }
                    OfferAdapter offerAdapter = OfferAdapter.this;
                    offerAdapter.adapterRe = new AdapterRe(offerAdapter.mContext, OfferAdapter.this.productsModelListftu, holder.ms_weight, OfferAdapter.dialog, holder.tv_mrp, holder.tv_price, holder.tv_wid);
                    recyclerView.setHasFixedSize(true);
                    OfferAdapter offerAdapter2 = OfferAdapter.this;
                    offerAdapter2.linearLayoutManager = new LinearLayoutManager(offerAdapter2.mContext, RecyclerView.VERTICAL, false);
                    recyclerView.setLayoutManager(OfferAdapter.this.linearLayoutManager);
                    recyclerView.setAdapter(OfferAdapter.this.adapterRe);
                    OfferAdapter.dialog.show();
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
        if (this.stackcoit.equalsIgnoreCase("Enable")) {
            holder.stack.setVisibility(View.GONE);
            holder.stack.setTextColor(Color.parseColor("#075730"));
        } else {
            holder.stack.setVisibility(View.VISIBLE);
            holder.stack.setText("Out of Stock ");
            holder.stack.setTextColor(-65536);
            TextView textView6 = holder.tv_price;
            textView6.setText("Price ₹ " + weightModelList.get(0).getWeb_price());
            TextView textView7 = holder.tv_mrp;
            textView7.setText("MRP ₹ " + weightModelList.get(0).getMrp());
            holder.llay_cart.setVisibility(View.GONE);
        }
        holder.tv_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (holder.ms_weight.getText().toString().isEmpty()) {
                    holder.ms_weight.setError("Choose any weight");
                    return;
                }
                holder.tv_plus.setVisibility(View.VISIBLE);
                holder.tv_qty.setVisibility(View.VISIBLE);
                holder.tv_add.setVisibility(View.GONE);
                holder.tv_minus.setVisibility(View.VISIBLE);
                Log.d("src", "Increasing value...");
                OfferAdapter offerAdapter = OfferAdapter.this;
                offerAdapter.f169i = Integer.valueOf(offerAdapter.productsModelList.get(position).getProduct_quantity());
                OfferAdapter offerAdapter2 = OfferAdapter.this;
                offerAdapter2.f169i = Integer.valueOf(offerAdapter2.f169i.intValue() + 1);
                String _stringVal = String.valueOf(OfferAdapter.this.f169i);
                holder.tv_qty.setText(_stringVal);
                OfferAdapter.this.productsModelList.get(position).setProduct_quantity(_stringVal);
                OfferAdapter.this.customer_id = BSession.getInstance().getUser_id(OfferAdapter.this.mContext);
                final Map<String, String> params = new HashMap<>();
                if (OfferAdapter.this.customer_id.equalsIgnoreCase("")) {
                    OfferAdapter offerAdapter3 = OfferAdapter.this;
                    offerAdapter3.para_str1 = "?pid=" + holder.tv_pId.getText().toString().trim();
                    OfferAdapter.this.para_str2 = "&qty=1";
                    OfferAdapter offerAdapter4 = OfferAdapter.this;
                    offerAdapter4.para_str3 = "&ip_address=" + OfferAdapter.this.deviceid;
                    OfferAdapter.this.para_str4 = "&cart_type=add";
                    OfferAdapter offerAdapter5 = OfferAdapter.this;
                    offerAdapter5.para_str5 = "&vid=" + holder.tv_wid.getText().toString().trim();
                    OfferAdapter offerAdapter6 = OfferAdapter.this;
                    offerAdapter6.para_str6 = "&scid=" + OfferAdapter.this.productsModelList.get(position).getScid();
                    OfferAdapter offerAdapter7 = OfferAdapter.this;
                    offerAdapter7.baseUrl = ProductConfig.addcart + OfferAdapter.this.para_str1 + OfferAdapter.this.para_str2 + OfferAdapter.this.para_str3 + OfferAdapter.this.para_str4 + OfferAdapter.this.para_str5 + OfferAdapter.this.para_str6;
                } else {
                    OfferAdapter offerAdapter8 = OfferAdapter.this;
                    offerAdapter8.para_str1 = "?pid=" + holder.tv_pId.getText().toString().trim();
                    OfferAdapter.this.para_str2 = "&qty=1";
                    OfferAdapter offerAdapter9 = OfferAdapter.this;
                    offerAdapter9.para_str3 = "&user_id=" + OfferAdapter.this.customer_id;
                    OfferAdapter.this.para_str4 = "&cart_type=add";
                    OfferAdapter offerAdapter10 = OfferAdapter.this;
                    offerAdapter10.para_str5 = "&vid=" + holder.tv_wid.getText().toString().trim();
                    OfferAdapter offerAdapter11 = OfferAdapter.this;
                    offerAdapter11.para_str6 = "&scid=" + OfferAdapter.this.productsModelList.get(position).getScid();
                    OfferAdapter offerAdapter12 = OfferAdapter.this;
                    offerAdapter12.baseUrl = ProductConfig.useraddcart + OfferAdapter.this.para_str1 + OfferAdapter.this.para_str2 + OfferAdapter.this.para_str3 + OfferAdapter.this.para_str4 + OfferAdapter.this.para_str5 + OfferAdapter.this.para_str6;
                }
                Volley.newRequestQueue(OfferAdapter.this.mContext).add(new StringRequest(0, OfferAdapter.this.baseUrl, new Response.Listener<String>() {
                    public void onResponse(String response) {
                        Log.e("Response", response.toString());
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) && jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                holder.tv_qty.setText(jsonResponse.getString("qty"));
                                OfferAdapter.this.productsModelList.get(position).setProduct_quantity(jsonResponse.getString("qty"));
                                ((OfferActivity) OfferAdapter.this.mContext).getCartCount();
                            } else if (jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) && jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("0")) {
                                Toast.makeText(OfferAdapter.this.mContext, "One Product one time only added", Toast.LENGTH_SHORT).show();
                            } else if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("5")) {
                                Toast.makeText(OfferAdapter.this.mContext, "Cart Add Failed", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(OfferAdapter.this.mContext, "You can purchase only Five products", Toast.LENGTH_SHORT).show();
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
        });
        holder.tv_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (OfferAdapter.this.scid.equalsIgnoreCase("44")) {
                    Log.d("src", "Increasing value...");
                    OfferAdapter offerAdapter = OfferAdapter.this;
                    offerAdapter.f169i = Integer.valueOf(offerAdapter.productsModelList.get(position).getProduct_quantity());
                    OfferAdapter offerAdapter2 = OfferAdapter.this;
                    offerAdapter2.f169i = Integer.valueOf(offerAdapter2.f169i.intValue() + 1);
                    String valueOf = String.valueOf(OfferAdapter.this.f169i);
                    OfferAdapter.this.customer_id = BSession.getInstance().getUser_id(OfferAdapter.this.mContext);
                    final Map<String, String> params = new HashMap<>();
                    if (OfferAdapter.this.customer_id.equalsIgnoreCase("")) {
                        OfferAdapter offerAdapter3 = OfferAdapter.this;
                        offerAdapter3.para_str1 = "?pid=" + holder.tv_pId.getText().toString().trim();
                        OfferAdapter.this.para_str2 = "&qty=1";
                        OfferAdapter offerAdapter4 = OfferAdapter.this;
                        offerAdapter4.para_str3 = "&ip_address=" + OfferAdapter.this.deviceid;
                        OfferAdapter.this.para_str4 = "&cart_type=add";
                        OfferAdapter offerAdapter5 = OfferAdapter.this;
                        offerAdapter5.para_str5 = "&vid=" + holder.tv_wid.getText().toString().trim();
                        OfferAdapter offerAdapter6 = OfferAdapter.this;
                        offerAdapter6.baseUrl = ProductConfig.addcart + OfferAdapter.this.para_str1 + OfferAdapter.this.para_str2 + OfferAdapter.this.para_str3 + OfferAdapter.this.para_str4 + OfferAdapter.this.para_str5;
                    } else {
                        OfferAdapter offerAdapter7 = OfferAdapter.this;
                        offerAdapter7.para_str1 = "?pid=" + holder.tv_pId.getText().toString().trim();
                        OfferAdapter.this.para_str2 = "&qty=1";
                        OfferAdapter offerAdapter8 = OfferAdapter.this;
                        offerAdapter8.para_str3 = "&user_id=" + OfferAdapter.this.customer_id;
                        OfferAdapter.this.para_str4 = "&cart_type=add";
                        OfferAdapter offerAdapter9 = OfferAdapter.this;
                        offerAdapter9.para_str5 = "&vid=" + holder.tv_wid.getText().toString().trim();
                        OfferAdapter offerAdapter10 = OfferAdapter.this;
                        offerAdapter10.baseUrl = ProductConfig.useraddcart + OfferAdapter.this.para_str1 + OfferAdapter.this.para_str2 + OfferAdapter.this.para_str3 + OfferAdapter.this.para_str4 + OfferAdapter.this.para_str5;
                    }
                    Volley.newRequestQueue(OfferAdapter.this.mContext).add(new StringRequest(0, OfferAdapter.this.baseUrl, new Response.Listener<String>() {
                        public void onResponse(String response) {
                            Log.e("Response", response.toString());
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                    Toast.makeText(OfferAdapter.this.mContext, "Cart Add Failed", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                holder.tv_qty.setText(jsonResponse.getString("qty"));
                                OfferAdapter.this.productsModelList.get(position).setProduct_quantity(jsonResponse.getString("qty"));
                                ((OfferActivity) OfferAdapter.this.mContext).getCartCount();
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
                OfferAdapter offerAdapter11 = OfferAdapter.this;
                offerAdapter11.f169i = Integer.valueOf(offerAdapter11.productsModelList.get(position).getProduct_quantity());
                OfferAdapter offerAdapter12 = OfferAdapter.this;
                offerAdapter12.f169i = Integer.valueOf(offerAdapter12.f169i.intValue() + 1);
                String valueOf2 = String.valueOf(OfferAdapter.this.f169i);
                OfferAdapter.this.customer_id = BSession.getInstance().getUser_id(OfferAdapter.this.mContext);
                final Map<String, String> params2 = new HashMap<>();
                if (OfferAdapter.this.customer_id.equalsIgnoreCase("")) {
                    OfferAdapter offerAdapter13 = OfferAdapter.this;
                    offerAdapter13.para_str1 = "?pid=" + holder.tv_pId.getText().toString().trim();
                    OfferAdapter.this.para_str2 = "&qty=1";
                    OfferAdapter offerAdapter14 = OfferAdapter.this;
                    offerAdapter14.para_str3 = "&ip_address=" + OfferAdapter.this.deviceid;
                    OfferAdapter.this.para_str4 = "&cart_type=add";
                    OfferAdapter offerAdapter15 = OfferAdapter.this;
                    offerAdapter15.para_str5 = "&vid=" + holder.tv_wid.getText().toString().trim();
                    OfferAdapter offerAdapter16 = OfferAdapter.this;
                    offerAdapter16.baseUrl = ProductConfig.addcart + OfferAdapter.this.para_str1 + OfferAdapter.this.para_str2 + OfferAdapter.this.para_str3 + OfferAdapter.this.para_str4 + OfferAdapter.this.para_str5;
                } else {
                    OfferAdapter offerAdapter17 = OfferAdapter.this;
                    offerAdapter17.para_str1 = "?pid=" + holder.tv_pId.getText().toString().trim();
                    OfferAdapter.this.para_str2 = "&qty=1";
                    OfferAdapter offerAdapter18 = OfferAdapter.this;
                    offerAdapter18.para_str3 = "&user_id=" + OfferAdapter.this.customer_id;
                    OfferAdapter.this.para_str4 = "&cart_type=add";
                    OfferAdapter offerAdapter19 = OfferAdapter.this;
                    offerAdapter19.para_str5 = "&vid=" + holder.tv_wid.getText().toString().trim();
                    OfferAdapter offerAdapter20 = OfferAdapter.this;
                    offerAdapter20.baseUrl = ProductConfig.useraddcart + OfferAdapter.this.para_str1 + OfferAdapter.this.para_str2 + OfferAdapter.this.para_str3 + OfferAdapter.this.para_str4 + OfferAdapter.this.para_str5;
                }
                Volley.newRequestQueue(OfferAdapter.this.mContext).add(new StringRequest(0, OfferAdapter.this.baseUrl, new Response.Listener<String>() {
                    public void onResponse(String response) {
                        Log.e("Response", response.toString());
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                Toast.makeText(OfferAdapter.this.mContext, "Cart Add Failed", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            holder.tv_qty.setText(jsonResponse.getString("qty"));
                            OfferAdapter.this.productsModelList.get(position).setProduct_quantity(jsonResponse.getString("qty"));
                            ((OfferActivity) OfferAdapter.this.mContext).getCartCount();
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
                Log.d("src", "Decreasing value...");
                OfferAdapter offerAdapter = OfferAdapter.this;
                offerAdapter.f169i = Integer.valueOf(offerAdapter.productsModelList.get(position).getProduct_quantity());
                if (OfferAdapter.this.f169i.intValue() > 0) {
                    OfferAdapter offerAdapter2 = OfferAdapter.this;
                    offerAdapter2.f169i = Integer.valueOf(offerAdapter2.f169i.intValue() - 1);
                    String valueOf = String.valueOf(OfferAdapter.this.f169i);
                    OfferAdapter.this.customer_id = BSession.getInstance().getUser_id(OfferAdapter.this.mContext);
                    final Map<String, String> params = new HashMap<>();
                    if (OfferAdapter.this.customer_id.equalsIgnoreCase("")) {
                        OfferAdapter offerAdapter3 = OfferAdapter.this;
                        offerAdapter3.para_str1 = "?pid=" + holder.tv_pId.getText().toString().trim();
                        OfferAdapter.this.para_str2 = "&qty=1";
                        OfferAdapter offerAdapter4 = OfferAdapter.this;
                        offerAdapter4.para_str3 = "&ip_address=" + OfferAdapter.this.deviceid;
                        OfferAdapter.this.para_str4 = "&cart_type=sub";
                        OfferAdapter offerAdapter5 = OfferAdapter.this;
                        offerAdapter5.para_str5 = "&vid=" + holder.tv_wid.getText().toString().trim();
                        OfferAdapter offerAdapter6 = OfferAdapter.this;
                        offerAdapter6.baseUrl = ProductConfig.addcart + OfferAdapter.this.para_str1 + OfferAdapter.this.para_str2 + OfferAdapter.this.para_str3 + OfferAdapter.this.para_str4 + OfferAdapter.this.para_str5;
                    } else {
                        OfferAdapter offerAdapter7 = OfferAdapter.this;
                        offerAdapter7.para_str1 = "?pid=" + holder.tv_pId.getText().toString().trim();
                        OfferAdapter.this.para_str2 = "&qty=1";
                        OfferAdapter offerAdapter8 = OfferAdapter.this;
                        offerAdapter8.para_str3 = "&ip_address=" + OfferAdapter.this.deviceid;
                        OfferAdapter.this.para_str4 = "&cart_type=sub";
                        OfferAdapter offerAdapter9 = OfferAdapter.this;
                        offerAdapter9.para_str5 = "&vid=" + holder.tv_wid.getText().toString().trim();
                        OfferAdapter offerAdapter10 = OfferAdapter.this;
                        offerAdapter10.baseUrl = ProductConfig.useraddcart + OfferAdapter.this.para_str1 + OfferAdapter.this.para_str2 + OfferAdapter.this.para_str3 + OfferAdapter.this.para_str4 + OfferAdapter.this.para_str5;
                    }
                    Volley.newRequestQueue(OfferAdapter.this.mContext).add(new StringRequest(0, OfferAdapter.this.baseUrl, new Response.Listener<String>() {
                        public void onResponse(String response) {
                            Log.e("Response", response.toString());
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                if (jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) && jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                    holder.tv_qty.setText(jsonResponse.getString("qty"));
                                    OfferAdapter.this.productsModelList.get(position).setProduct_quantity(jsonResponse.getString("qty"));
                                    ((OfferActivity) OfferAdapter.this.mContext).getCartCount();
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
        holder.ll_parent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OfferAdapter.this.mContext, ProductViewActivity.class);
                ProductViewActivity.subcourcemodel = OfferAdapter.this.productsModelList.get(position);
                OfferAdapter.this.mContext.startActivity(intent);
            }
        });
    }


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
                OfferAdapter.this.wid = models.getWeight_wid();
            } else {
                holder.wid.setText(this.productsModelListftu.get(position).getWeight_webtitle());
                TextView textView3 = holder.mrp;
                textView3.setText("MRP ₹ " + this.productsModelListftu.get(position).getWeight_webmrp());
                TextView textView4 = holder.price;
                textView4.setText(" ₹ " + this.productsModelListftu.get(position).getWeight_webprice());
                holder.tv_wid.setText(this.productsModelListftu.get(position).getWeight_wid());
                OfferAdapter.this.wid = this.productsModelListftu.get(position).getWeight_wid();
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
