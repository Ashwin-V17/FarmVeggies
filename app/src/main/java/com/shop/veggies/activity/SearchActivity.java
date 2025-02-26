package com.shop.veggies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shop.veggies.R;
import com.shop.veggies.adapter.FruitAdapter;
import com.shop.veggies.model.ProductsModel;
import com.shop.veggies.utils.ProductConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    AutoCompleteTextView auto_search;
    String cid;
    /* access modifiers changed from: private */
    public FruitAdapter fruitAdapter;
    public ArrayList<ProductsModel> spinnerList = new ArrayList<>();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_search);
        this.auto_search = (AutoCompleteTextView) findViewById(R.id.auto_search);
        toolbar();
        loadspinner();
    }

    private void toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SearchActivity.this.startActivity(new Intent(SearchActivity.this.getApplicationContext(), MainActivity.class));
                SearchActivity.this.finish();
            }
        });
        getSupportActionBar().setTitle((CharSequence) "Search");
        toolbar.setTitleTextColor(-1);
    }

    private void loadspinner() {
        this.spinnerList = new ArrayList<>();
        final Map<String, String> params = new HashMap<>();
        Volley.newRequestQueue(getApplicationContext()).add(new StringRequest(0, ProductConfig.searchproduct, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONArray jsonArray = new JSONObject(response).getJSONArray("storeList");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ProductsModel model = new ProductsModel();
                        model.setProduct_id(jsonObject.getString("pid"));
                        model.setCid(jsonObject.getString("cid"));
                        model.setScid(jsonObject.getString("scid"));
                        model.setProduct_name(jsonObject.getString("productname"));
                        model.setOffer_status(jsonObject.getString("offerstatus"));
                        model.setProduct_offer(jsonObject.getString("offerpercentage"));
                        model.setProduct_image(jsonObject.getString("productimage"));
                        model.setProduct_status(jsonObject.getString(NotificationCompat.CATEGORY_STATUS));
                        model.setProduct_quantity(jsonObject.getString("qty"));
                        model.setProduct_desc(jsonObject.getString("description"));
                        SearchActivity.this.spinnerList.add(model);
                        FruitAdapter unused = SearchActivity.this.fruitAdapter = new FruitAdapter(SearchActivity.this, R.layout.custom_row, SearchActivity.this.spinnerList);
                        SearchActivity.this.auto_search.setAdapter(SearchActivity.this.fruitAdapter);
                        SearchActivity.this.auto_search.setThreshold(1);
                        SearchActivity.this.auto_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                ProductsModel productsModel = SearchActivity.this.spinnerList.get(position);
                                SearchActivity.this.auto_search.setText(SearchActivity.this.spinnerList.get(position).getProduct_name());
                                String product_id = SearchActivity.this.spinnerList.get(position).getProduct_id();
                                Intent intent = new Intent(SearchActivity.this.getApplicationContext(), ProductViewActivity.class);
                                ProductViewActivity.subcourcemodel = SearchActivity.this.spinnerList.get(position);
                                SearchActivity.this.startActivity(intent);
                            }
                        });
                        SearchActivity.this.auto_search.addTextChangedListener(new TextWatcher() {
                            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                                SearchActivity.this.fruitAdapter.getFilter().filter(cs);
                            }

                            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                            }

                            public void afterTextChanged(Editable arg0) {
                            }
                        });
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
