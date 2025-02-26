package com.shop.veggies.activity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.shop.veggies.R;
import com.shop.veggies.adapter.MyordersListAdapter;
import com.shop.veggies.model.MyordersModel;
import com.shop.veggies.utils.BSession;
import com.shop.veggies.utils.ProductConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyordersActivity extends AppCompatActivity {
    LinearLayoutManager linearLayoutManager;
    MyordersListAdapter myordersListAdapter;
    List<MyordersModel> myordersModelList = new ArrayList();
    ProgressDialog progressDialog;
    RecyclerView rv_ordersList;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_myorders);
        ProgressDialog progressDialog2 = new ProgressDialog(this);
        this.progressDialog = progressDialog2;
        progressDialog2.setMessage("Loading.....");
        init();
    }

    private void init() {
        toolbar();
        this.rv_ordersList = (RecyclerView) findViewById(R.id.rv_ordersList);
        getOrdersList();
    }

    private void toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MyordersActivity.this.startActivity(new Intent(MyordersActivity.this.getApplicationContext(), MainActivity.class));
                MyordersActivity.this.finish();
            }
        });
        getSupportActionBar().setTitle((CharSequence) "My Orders");
        toolbar.setTitleTextColor(-1);
    }

    private void getOrdersList() {
        String customer_id = BSession.getInstance().getUser_id(this);
        final Map<String, String> params = new HashMap<>();
        this.progressDialog.show();
        Volley.newRequestQueue(this).add(new StringRequest(0, ProductConfig.myorder + ("?user_id=" + customer_id), new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    MyordersActivity.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        Toast.makeText(MyordersActivity.this.getApplicationContext(), "No orders Result Found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    MyordersActivity.this.myordersModelList = new ArrayList();
                    JSONArray array = jsonResponse.getJSONArray("storeList");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jSONObject = array.getJSONObject(i);
                        MyordersModel myordersModel = new MyordersModel();
                        myordersModel.setOrder_id(array.getJSONObject(i).getString("order_id"));
                        myordersModel.setOrder_date(array.getJSONObject(i).getString("order_date"));
                        myordersModel.setOrder_total(array.getJSONObject(i).getString("grandtotal"));
                        myordersModel.setOrder_status(array.getJSONObject(i).getString("order_status"));
                        myordersModel.setDelivery_date(array.getJSONObject(i).getString("delivery_date"));
                        MyordersActivity.this.myordersModelList.add(myordersModel);
                    }
                    MyordersActivity.this.linearLayoutManager = new LinearLayoutManager(MyordersActivity.this, RecyclerView.VERTICAL, false);
                    MyordersActivity.this.rv_ordersList.setLayoutManager(MyordersActivity.this.linearLayoutManager);
                    MyordersActivity.this.myordersListAdapter = new MyordersListAdapter(MyordersActivity.this, MyordersActivity.this.myordersModelList);
                    MyordersActivity.this.rv_ordersList.setHasFixedSize(true);
                    MyordersActivity.this.rv_ordersList.setAdapter(MyordersActivity.this.myordersListAdapter);
                    MyordersActivity.this.myordersListAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    MyordersActivity.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                MyordersActivity.this.progressDialog.dismiss();
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                return params;
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }
}
