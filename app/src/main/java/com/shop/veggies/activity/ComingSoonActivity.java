package com.shop.veggies.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shop.veggies.R;
import com.shop.veggies.adapter.CategoryAdapter;
import com.shop.veggies.adapter.ComingSoonAdapter;
import com.shop.veggies.model.CategoryModel;
import com.shop.veggies.utils.ProductConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComingSoonActivity extends AppCompatActivity {
    CategoryAdapter categoryAdapter;
    /* access modifiers changed from: private */
    public List<CategoryModel> categoryModelList = new ArrayList();
    GridView gv_category;
    ProgressDialog progressDialog;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_coming_soon);
        toolbar();
        ProgressDialog progressDialog2 = new ProgressDialog(this);
        this.progressDialog = progressDialog2;
        progressDialog2.setMessage("Loading.....");
        this.gv_category = (GridView) findViewById(R.id.gv_category);
        setCategoryList();
    }

    private void setCategoryList() {
        this.categoryModelList = new ArrayList();
        final Map<String, String> params = new HashMap<>();
        this.progressDialog.show();
        Volley.newRequestQueue(this).add(new StringRequest(0, ProductConfig.comingsoon, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    ComingSoonActivity.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        Toast.makeText(ComingSoonActivity.this, "No category list", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JSONArray jsonlist = jsonResponse.getJSONArray("storeList");
                    for (int j = 0; j < jsonlist.length(); j++) {
                        JSONObject jsonlistObject = jsonlist.getJSONObject(j);
                        CategoryModel model = new CategoryModel();
                        model.setCategory_id(jsonlistObject.getString("comingsoon_id").toString());
                        model.setCategory_name(jsonlistObject.getString("web_title").toString());
                        model.setCategory_image(jsonlistObject.getString("comingsoon_image").toString());
                        ComingSoonActivity.this.categoryModelList.add(model);
                        ComingSoonActivity.this.gv_category.setAdapter(new ComingSoonAdapter(ComingSoonActivity.this, R.layout.layout_category_list, ComingSoonActivity.this.categoryModelList));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ComingSoonActivity.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                ComingSoonActivity.this.progressDialog.dismiss();
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
                ComingSoonActivity.this.startActivity(new Intent(ComingSoonActivity.this.getApplicationContext(), MainActivity.class));
                ComingSoonActivity.this.finish();
            }
        });
        getSupportActionBar().setTitle((CharSequence) "Comming Soon");
        toolbar.setTitleTextColor(-1);
    }
}
