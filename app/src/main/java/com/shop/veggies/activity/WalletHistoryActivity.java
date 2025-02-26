package com.shop.veggies.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.shop.veggies.R;
import com.shop.veggies.adapter.HistoryAdapter;
import com.shop.veggies.adapter.ViewCartAdapter;
import com.shop.veggies.model.CartModel;
import com.shop.veggies.model.HistoryModel;
import com.shop.veggies.utils.BSession;
import com.shop.veggies.utils.ProductConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WalletHistoryActivity extends AppCompatActivity {
    CartModel cartModel = new CartModel();
    List<HistoryModel> historyModelArrayList = new ArrayList();
    LinearLayoutManager linearLayoutManager;
    RecyclerView mRecyclerView;
    RecyclerView rv_walletlist;
    Toolbar toolbar;
    ViewCartAdapter viewCartAdapter;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_wallet_history);
        this.rv_walletlist = (RecyclerView) findViewById(R.id.rv_walletlist);
        toolbar();
        setRecyclerview();
    }

    public void setRecyclerview() {
        this.historyModelArrayList = new ArrayList();
        String customer_id = BSession.getInstance().getUser_id(this);
        final Map<String, String> params = new HashMap<>();
        Volley.newRequestQueue(this).add(new StringRequest(0, ProductConfig.wallet_history + ("?user_id=" + customer_id), new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        Toast.makeText(WalletHistoryActivity.this.getApplicationContext(), "History empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    WalletHistoryActivity.this.historyModelArrayList = new ArrayList();
                    JSONArray array = jsonResponse.getJSONArray("storeList");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jSONObject = array.getJSONObject(i);
                        HistoryModel cartModel = new HistoryModel();
                        cartModel.setTransaction_id(array.getJSONObject(i).getString(FirebaseAnalytics.Param.TRANSACTION_ID));
                        cartModel.setComments(array.getJSONObject(i).getString("comments"));
                        cartModel.setAmount(array.getJSONObject(i).getString("amount"));
                        cartModel.setCreated_at(array.getJSONObject(i).getString("created_at"));
                        WalletHistoryActivity.this.historyModelArrayList.add(cartModel);
                    }
                    HistoryAdapter viewCartAdapter = new HistoryAdapter(WalletHistoryActivity.this, WalletHistoryActivity.this.historyModelArrayList);
                    WalletHistoryActivity.this.rv_walletlist.setHasFixedSize(true);
                    WalletHistoryActivity.this.linearLayoutManager = new LinearLayoutManager(WalletHistoryActivity.this, RecyclerView.VERTICAL, false);
                    WalletHistoryActivity.this.rv_walletlist.setLayoutManager(WalletHistoryActivity.this.linearLayoutManager);
                    WalletHistoryActivity.this.rv_walletlist.setAdapter(viewCartAdapter);
                    viewCartAdapter.notifyDataSetChanged();
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
                WalletHistoryActivity.this.onBackPressed();
            }
        });
        getSupportActionBar().setTitle((CharSequence) "Wallet History");
        this.toolbar.setTitleTextColor(-1);
    }
}
