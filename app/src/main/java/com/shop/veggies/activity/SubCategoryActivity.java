package com.shop.veggies.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.shop.veggies.R;
import com.shop.veggies.adapter.TuitionViewPagerAdapter;
import com.shop.veggies.fragment.TutionMyclassDynamicFragment;
import com.shop.veggies.model.CategoryModel;
import com.shop.veggies.model.ProductsModel;
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
public class SubCategoryActivity extends AppCompatActivity {
    public static CategoryModel subcourcemodel = new CategoryModel();
    TuitionViewPagerAdapter adapter;
    TextView badge_notification;
    String badge_notification1 = "";
    String baseUrl;
    String customer_id = null;
    String deviceid;
    String ipaddress;
    /* access modifiers changed from: private */
    public final List<Fragment> mFragmentList = new ArrayList();
    /* access modifiers changed from: private */
    public final List<String> mFragmentTitleList = new ArrayList();
    /* access modifiers changed from: private */
    public ArrayList<String> mFragmentscidList = new ArrayList<>();
    String para_str;
    ProgressDialog progressDialog;
    /* access modifiers changed from: private */
    public TabLayout tabLayout;
    /* access modifiers changed from: private */
    public ViewPager viewPager;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_sub_category);
        ProgressDialog progressDialog2 = new ProgressDialog(this);
        this.progressDialog = progressDialog2;
        progressDialog2.setMessage("Loading.....");
        this.deviceid = Settings.Secure.getString(getContentResolver(), "android_id");
        toolbar();
        this.tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager2 = (ViewPager) findViewById(R.id.viewpager);
        this.viewPager = viewPager2;
        this.tabLayout.setupWithViewPager(viewPager2);
        for (int i = 0; i < this.tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) this.tabLayout.getChildAt(0)).getChildAt(i);
            ((ViewGroup.MarginLayoutParams) tab.getLayoutParams()).setMargins(0, 0, 50, 0);
            tab.requestLayout();
        }
        getSubcategoryList();
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
        getCartCount();
        actionView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SubCategoryActivity.this.onOptionsItemSelected(menuItem);
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

    private void toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SubCategoryActivity.this.startActivity(new Intent(SubCategoryActivity.this, MainActivity.class));
            }
        });
        getSupportActionBar().setTitle((CharSequence) subcourcemodel.getCategory_name());
        toolbar.setTitleTextColor(-1);
    }

    private void getSubcategoryList() {
        final Map<String, String> params = new HashMap<>();
        this.progressDialog.show();
        Volley.newRequestQueue(this).add(new StringRequest(0, ProductConfig.subcategorylist + ("?cid=" + subcourcemodel.getCategory_id()), new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    SubCategoryActivity.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        Toast.makeText(SubCategoryActivity.this.getApplicationContext(), "No SubCategory Result Found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JSONArray array = jsonResponse.getJSONArray("storeList");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jSONObject = array.getJSONObject(i);
                        ProductsModel model = new ProductsModel();
                        model.setCid(array.getJSONObject(i).getString("cid"));
                        model.setScid(array.getJSONObject(i).getString("scid"));
                        model.setSubcategoryname(array.getJSONObject(i).getString("subcategoryname"));
                        SubCategoryActivity.this.mFragmentTitleList.add(array.getJSONObject(i).getString("subcategoryname"));
                        SubCategoryActivity.this.mFragmentscidList.add(array.getJSONObject(i).getString("scid"));
                        Log.d("FragmentTitle", String.valueOf(SubCategoryActivity.this.mFragmentTitleList));
                    }
                    for (int i2 = 0; i2 < SubCategoryActivity.this.mFragmentTitleList.size(); i2++) {
                        SubCategoryActivity.this.mFragmentList.add(new TutionMyclassDynamicFragment());
                    }
                    SubCategoryActivity.this.setupViewPager(SubCategoryActivity.this.viewPager);
                    SubCategoryActivity.this.tabLayout.setupWithViewPager(SubCategoryActivity.this.viewPager);
                    SubCategoryActivity.this.viewPager.setOffscreenPageLimit(SubCategoryActivity.this.mFragmentList.size());
                    SubCategoryActivity.this.tabLayout.setupWithViewPager(SubCategoryActivity.this.viewPager);
                } catch (JSONException e) {
                    e.printStackTrace();
                    SubCategoryActivity.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                SubCategoryActivity.this.progressDialog.dismiss();
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                return params;
            }
        });
    }

    /* access modifiers changed from: private */
    public void setupViewPager(ViewPager viewPager2) {
        TuitionViewPagerAdapter tuitionViewPagerAdapter = new TuitionViewPagerAdapter(getSupportFragmentManager(), this.mFragmentList, this.mFragmentTitleList, this.mFragmentscidList);
        this.adapter = tuitionViewPagerAdapter;
        viewPager2.setAdapter(tuitionViewPagerAdapter);
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
                        SubCategoryActivity.this.badge_notification.setText("0");
                    } else {
                        SubCategoryActivity.this.badge_notification.setText(jsonResponse.getString("count"));
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
}
