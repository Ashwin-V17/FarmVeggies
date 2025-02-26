package com.shop.veggies.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.internal.view.SupportMenu;
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
import com.shop.veggies.activity.MainActivity;
import com.shop.veggies.activity.ProductViewActivity;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MailViewHolder> {
    String[] SPINNERLIST = {"250g", "500g", "1kg", "5kg"};
    ArrayAdapter<String> arrayAdapter;
    String baseUrl;
    String customer_id = "";
    String deviceid;
    String editqut;

    /* renamed from: i */
    Integer f172i;
    String ipaddress;
    private BottomSheetDialog mBottomSheetDialog;
    /* access modifiers changed from: private */
    public Context mContext;
    String mrp;
    String name;
    String offer_status;
    String para_str;
    String para_str3;
    String pid = "";
    String price;
    ProductsModel productsModel = new ProductsModel();
    List<ProductsModel> productsModelList;
    String qty = "";
    String stackcoit;
    String type;
    String wid;

    public ProductsAdapter(Context mContext2, List<ProductsModel> productsModelList2) {
        this.mContext = mContext2;
        this.productsModelList = productsModelList2;
    }

    public MailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_seller_item_list, parent, false));
    }

    public void onBindViewHolder(final MailViewHolder holder, final int position) {
        ProductsModel productsModel2 = this.productsModelList.get(position);
        this.deviceid = Settings.Secure.getString(this.mContext.getContentResolver(), "android_id");
        this.name = this.productsModelList.get(position).getProduct_name();
        this.price = this.productsModelList.get(position).getProduct_price();
        this.mrp = this.productsModelList.get(position).getProduct_mrp();
        this.qty = this.productsModelList.get(position).getProduct_quantity();
        this.pid = this.productsModelList.get(position).getProduct_id();
        this.stackcoit = this.productsModelList.get(position).getProduct_status();
        ((RequestBuilder) Glide.with(this.mContext).load(this.productsModelList.get(position).getProduct_image()).placeholder((int) R.drawable.dummy)).into((ImageView) holder.iv_Image);
        holder.tv_mrp.setPaintFlags(holder.tv_mrp.getPaintFlags() | 16);
        holder.tv_title.setText(this.productsModelList.get(position).getProduct_name());
        holder.tv_qty.setText(this.productsModelList.get(position).getProduct_quantity());
        holder.tv_pId.setText(this.productsModelList.get(position).getProduct_id());
        String offer_status2 = this.productsModelList.get(position).getOffer_status();
        this.offer_status = offer_status2;
        Log.e("offerstatus", offer_status2);
        if (this.productsModelList.get(position).getProduct_offer().contentEquals("0")) {
            holder.fl_offer.setVisibility(8);
        } else {
            holder.fl_offer.setVisibility(0);
            TextView textView = holder.tv_offer;
            textView.setText(this.productsModelList.get(position).getProduct_offer() + " \n off");
        }
        TextView textView2 = holder.tv_price;
        textView2.setText("₹ " + this.price);
        TextView textView3 = holder.tv_mrp;
        textView3.setText("MRP ₹ " + this.mrp);
        holder.ms_weight.setSelection(0);
        holder.ms_weight.setAdapter(this.arrayAdapter);
        ArrayList<String> weightarray = new ArrayList<>();
        final List<WeightModel> weightModelList = this.productsModelList.get(position).getWeight();
        for (WeightModel weightModel : weightModelList) {
            weightarray.add(weightModel.getWeb_title());
        }
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this.mContext, R.layout.spinner_item, weightarray);
        this.arrayAdapter = arrayAdapter2;
        arrayAdapter2.setDropDownViewResource(17367049);
        holder.ms_weight.setSelection(0);
        holder.ms_weight.setAdapter(this.arrayAdapter);
        holder.ms_weight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d("spi", holder.ms_weight.getSelectedItem().toString());
                for (WeightModel weightModel : weightModelList) {
                    if (holder.ms_weight.getSelectedItem().toString().equals(weightModel.getWeb_title())) {
                        TextView textView = holder.tv_price;
                        textView.setText("₹ " + weightModel.getWeb_price());
                        holder.tv_wid.setText(weightModel.getWid());
                        ProductsAdapter.this.wid = weightModel.getWid();
                        TextView textView2 = holder.tv_mrp;
                        textView2.setText("MRP ₹ " + weightModel.getMrp());
                    }
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.wid = weightModelList.get(0).getWid();
        TextView textView4 = holder.tv_price;
        textView4.setText("Price ₹ " + weightModelList.get(0).getWeb_price());
        holder.tv_wid.setText(weightModelList.get(0).getWid());
        TextView textView5 = holder.tv_mrp;
        textView5.setText("MRP ₹ " + weightModelList.get(0).getMrp());
        if (this.qty.equals("0")) {
            holder.tv_plus.setVisibility(8);
            holder.tv_qty.setVisibility(8);
            holder.rel_add.setVisibility(0);
            holder.tv_minus.setVisibility(8);
        } else {
            holder.tv_plus.setVisibility(0);
            holder.tv_qty.setVisibility(0);
            holder.rel_add.setVisibility(8);
            holder.tv_minus.setVisibility(0);
            holder.tv_qty.setText(this.qty);
        }
        if (this.stackcoit.equalsIgnoreCase("Enable")) {
            holder.stack.setVisibility(8);
        } else {
            holder.stack.setVisibility(0);
            holder.stack.setText("Out of Stock ");
            holder.stack.setTextColor(SupportMenu.CATEGORY_MASK);
            TextView textView6 = holder.tv_price;
            textView6.setText("Price ₹ " + weightModelList.get(0).getWeb_price());
            TextView textView7 = holder.tv_mrp;
            textView7.setText("MRP ₹ " + weightModelList.get(0).getMrp());
            holder.rel_add.setVisibility(8);
        }
        holder.rel_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ProductsAdapter productsAdapter = ProductsAdapter.this;
                productsAdapter.deviceid = Settings.Secure.getString(productsAdapter.mContext.getContentResolver(), "android_id");
                if (!holder.ms_weight.getSelectedItem().toString().isEmpty()) {
                    holder.tv_plus.setVisibility(0);
                    holder.tv_qty.setVisibility(0);
                    holder.rel_add.setVisibility(8);
                    holder.tv_minus.setVisibility(0);
                    Log.d("src", "Increasing value...");
                    ProductsAdapter.this.f172i = Integer.valueOf(holder.tv_qty.getText().toString().trim());
                    ProductsAdapter productsAdapter2 = ProductsAdapter.this;
                    productsAdapter2.f172i = Integer.valueOf(productsAdapter2.f172i.intValue() + 1);
                    String _stringVal = String.valueOf(ProductsAdapter.this.f172i);
                    holder.tv_qty.setText(_stringVal);
                    ProductsAdapter.this.productsModelList.get(position).setProduct_quantity(_stringVal);
                    ProductsAdapter.this.customer_id = BSession.getInstance().getUser_id(ProductsAdapter.this.mContext);
                    final Map<String, String> params = new HashMap<>();
                    String para_str1 = "?pid=" + holder.tv_pId.getText().toString().trim();
                    if (ProductsAdapter.this.customer_id.equalsIgnoreCase("")) {
                        ProductsAdapter.this.para_str3 = "&ip_address=" + ProductsAdapter.this.deviceid;
                    } else {
                        ProductsAdapter.this.para_str3 = "&user_id=" + ProductsAdapter.this.customer_id;
                    }
                    String para_str5 = "&vid=" + holder.tv_wid.getText().toString().trim();
                    if (ProductsAdapter.this.customer_id.equalsIgnoreCase("")) {
                        ProductsAdapter.this.baseUrl = ProductConfig.addcart + para_str1 + "&qty=1" + ProductsAdapter.this.para_str3 + "&cart_type=add" + para_str5;
                    } else {
                        ProductsAdapter.this.baseUrl = ProductConfig.useraddcart + para_str1 + "&qty=1" + ProductsAdapter.this.para_str3 + "&cart_type=add" + para_str5;
                    }
                    Volley.newRequestQueue(ProductsAdapter.this.mContext).add(new StringRequest(0, ProductsAdapter.this.baseUrl, new Response.Listener<String>() {
                        public void onResponse(String response) {
                            Log.e("Response", response.toString());
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                    Toast.makeText(ProductsAdapter.this.mContext, "Cart Add Failed", 1).show();
                                    return;
                                }
                                holder.tv_qty.setText(jsonResponse.getString("qty"));
                                ProductsAdapter.this.productsModelList.get(position).setProduct_quantity(jsonResponse.getString("qty"));
                                ((MainActivity) ProductsAdapter.this.mContext).getCartCount();
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
        holder.tv_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ProductsAdapter productsAdapter = ProductsAdapter.this;
                productsAdapter.deviceid = Settings.Secure.getString(productsAdapter.mContext.getContentResolver(), "android_id");
                Log.d("src", "Increasing value...");
                ProductsAdapter productsAdapter2 = ProductsAdapter.this;
                productsAdapter2.f172i = Integer.valueOf(productsAdapter2.productsModelList.get(position).getProduct_quantity());
                ProductsAdapter productsAdapter3 = ProductsAdapter.this;
                productsAdapter3.f172i = Integer.valueOf(productsAdapter3.f172i.intValue() + 1);
                String valueOf = String.valueOf(ProductsAdapter.this.f172i);
                ProductsAdapter.this.customer_id = BSession.getInstance().getUser_id(ProductsAdapter.this.mContext);
                final Map<String, String> params = new HashMap<>();
                String para_str1 = "?pid=" + holder.tv_pId.getText().toString().trim();
                if (ProductsAdapter.this.customer_id.equalsIgnoreCase("")) {
                    ProductsAdapter.this.para_str3 = "&ip_address=" + ProductsAdapter.this.deviceid;
                } else {
                    ProductsAdapter.this.para_str3 = "&user_id=" + ProductsAdapter.this.customer_id;
                }
                String para_str5 = "&vid=" + holder.tv_wid.getText().toString().trim();
                if (ProductsAdapter.this.customer_id.equalsIgnoreCase("")) {
                    ProductsAdapter.this.baseUrl = ProductConfig.addcart + para_str1 + "&qty=1" + ProductsAdapter.this.para_str3 + "&cart_type=add" + para_str5;
                } else {
                    ProductsAdapter.this.baseUrl = ProductConfig.useraddcart + para_str1 + "&qty=1" + ProductsAdapter.this.para_str3 + "&cart_type=add" + para_str5;
                }
                Volley.newRequestQueue(ProductsAdapter.this.mContext).add(new StringRequest(0, ProductsAdapter.this.baseUrl, new Response.Listener<String>() {
                    public void onResponse(String response) {
                        Log.e("Response", response.toString());
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                Toast.makeText(ProductsAdapter.this.mContext, "Cart Add Failed", 1).show();
                                return;
                            }
                            holder.tv_qty.setText(jsonResponse.getString("qty"));
                            ProductsAdapter.this.productsModelList.get(position).setProduct_quantity(jsonResponse.getString("qty"));
                            ((MainActivity) ProductsAdapter.this.mContext).getCartCount();
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
        holder.tv_minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ProductsAdapter productsAdapter = ProductsAdapter.this;
                productsAdapter.deviceid = Settings.Secure.getString(productsAdapter.mContext.getContentResolver(), "android_id");
                Log.d("src", "Decreasing value...");
                ProductsAdapter productsAdapter2 = ProductsAdapter.this;
                productsAdapter2.f172i = Integer.valueOf(productsAdapter2.productsModelList.get(position).getProduct_quantity());
                if (ProductsAdapter.this.f172i.intValue() > 0) {
                    ProductsAdapter productsAdapter3 = ProductsAdapter.this;
                    productsAdapter3.f172i = Integer.valueOf(productsAdapter3.f172i.intValue() - 1);
                    String valueOf = String.valueOf(ProductsAdapter.this.f172i);
                    ProductsAdapter.this.customer_id = BSession.getInstance().getUser_id(ProductsAdapter.this.mContext);
                    final Map<String, String> params = new HashMap<>();
                    String para_str1 = "?pid=" + holder.tv_pId.getText().toString().trim();
                    if (ProductsAdapter.this.customer_id.equalsIgnoreCase("")) {
                        ProductsAdapter.this.para_str3 = "&ip_address=" + ProductsAdapter.this.deviceid;
                    } else {
                        ProductsAdapter.this.para_str3 = "&user_id=" + ProductsAdapter.this.customer_id;
                    }
                    String para_str5 = "&vid=" + holder.tv_wid.getText().toString().trim();
                    if (ProductsAdapter.this.customer_id.equalsIgnoreCase("")) {
                        ProductsAdapter.this.baseUrl = ProductConfig.addcart + para_str1 + "&qty=1" + ProductsAdapter.this.para_str3 + "&cart_type=sub" + para_str5;
                    } else {
                        ProductsAdapter.this.baseUrl = ProductConfig.useraddcart + para_str1 + "&qty=1" + ProductsAdapter.this.para_str3 + "&cart_type=sub" + para_str5;
                    }
                    Volley.newRequestQueue(ProductsAdapter.this.mContext).add(new StringRequest(0, ProductsAdapter.this.baseUrl, new Response.Listener<String>() {
                        public void onResponse(String response) {
                            Log.e("Response", response.toString());
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                if (jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) && jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                                    if (jsonResponse.getString("message").equalsIgnoreCase("Successfully Updated")) {
                                        holder.tv_qty.setText(jsonResponse.getString("qty"));
                                        ProductsAdapter.this.productsModelList.get(position).setProduct_quantity(jsonResponse.getString("qty"));
                                        ((MainActivity) ProductsAdapter.this.mContext).getCartCount();
                                        return;
                                    }
                                    ((MainActivity) ProductsAdapter.this.mContext).getCartCount();
                                    holder.tv_plus.setVisibility(8);
                                    holder.tv_qty.setVisibility(8);
                                    holder.rel_add.setVisibility(0);
                                    holder.tv_minus.setVisibility(8);
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
                Intent intent = new Intent(ProductsAdapter.this.mContext, ProductViewActivity.class);
                ProductViewActivity.subcourcemodel = ProductsAdapter.this.productsModelList.get(position);
                ProductsAdapter.this.mContext.startActivity(intent);
            }
        });
    }

    private void NetwordDetect() {
        boolean WIFI = false;
        boolean MOBILE = false;
        for (NetworkInfo netInfo : ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getAllNetworkInfo()) {
            if (netInfo.getTypeName().equalsIgnoreCase("WIFI") && netInfo.isConnected()) {
                WIFI = true;
            }
            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE") && netInfo.isConnected()) {
                MOBILE = true;
            }
        }
        if (WIFI) {
            this.ipaddress = GetDeviceipWiFiData();
        }
        if (MOBILE) {
            this.ipaddress = GetDeviceipMobileData();
        }
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

    public String GetDeviceipWiFiData() {
        return Formatter.formatIpAddress(((WifiManager) this.mContext.getSystemService("wifi")).getConnectionInfo().getIpAddress());
    }

    public int getItemCount() {
        return this.productsModelList.size();
    }

    public class MailViewHolder extends RecyclerView.ViewHolder {
        FrameLayout fl_offer;
        CircleImageView iv_Image;
        LinearLayout ll_parent;
        Spinner ms_weight;
        RelativeLayout rel_add;
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

        public MailViewHolder(View itemView) {
            super(itemView);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            this.ms_weight = (Spinner) itemView.findViewById(R.id.sp_weight);
            this.tv_qty = (TextView) itemView.findViewById(R.id.tv_qty);
            this.tv_plus = (TextView) itemView.findViewById(R.id.tv_plus);
            this.tv_minus = (TextView) itemView.findViewById(R.id.tv_minus);
            this.iv_Image = (CircleImageView) itemView.findViewById(R.id.iv_Image);
            this.tv_pId = (TextView) itemView.findViewById(R.id.tv_pId);
            this.tv_wid = (TextView) itemView.findViewById(R.id.tv_wid);
            this.fl_offer = (FrameLayout) itemView.findViewById(R.id.fl_offer);
            this.tv_mrp = (TextView) itemView.findViewById(R.id.tv_mrp);
            this.tv_offer = (TextView) itemView.findViewById(R.id.tv_offer);
            this.ll_parent = (LinearLayout) itemView.findViewById(R.id.ll_parent);
            this.tv_add = (TextView) itemView.findViewById(R.id.tv_add1);
            this.stack = (TextView) itemView.findViewById(R.id.stack);
            this.rel_add = (RelativeLayout) itemView.findViewById(R.id.rel_add);
        }
    }
}
