package com.shop.veggies.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shop.veggies.R;
import com.shop.veggies.adapter.AllCategoryAdapter;
import com.shop.veggies.adapter.CategoryListAdapter;
import com.shop.veggies.model.CategoryModel;
import com.shop.veggies.model.PlanChildModel;
import com.shop.veggies.model.PlanHeaderModel;
import com.shop.veggies.utils.ProductConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryListActivity extends AppCompatActivity implements ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupExpandListener, ExpandableListView.OnGroupCollapseListener {
    CategoryListAdapter adapter;
    ArrayList<PlanHeaderModel> categoryChildModelArrayList = null;
    /* access modifiers changed from: private */
    public List<CategoryModel> categoryModelList = new ArrayList();
    ArrayList<PlanChildModel> child = null;
    ExpandableListView elvMyListView;
    GridView gv_category;
    String ipaddress;
    private int lastExpandedPosition = -1;
    ProgressDialog progressDialog;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_category_list);
        ProgressDialog progressDialog2 = new ProgressDialog(this);
        this.progressDialog = progressDialog2;
        progressDialog2.setMessage("Loading.....");
        this.gv_category = (GridView) findViewById(R.id.gv_category);
        toolbar();
        setCategoryList();
    }

    private void toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CategoryListActivity.this.onBackPressed();
            }
        });
        getSupportActionBar().setTitle((CharSequence) "Categories");
        toolbar.setTitleTextColor(-1);
    }

    private void setCategoryList() {
        this.categoryModelList = new ArrayList();
        final Map<String, String> params = new HashMap<>();
        this.progressDialog.show();
        Volley.newRequestQueue(this).add(new StringRequest(0, ProductConfig.categorylist, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    CategoryListActivity.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        Toast.makeText(CategoryListActivity.this, "No category list", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JSONArray jsonlist = jsonResponse.getJSONArray("storeList");
                    for (int j = 0; j < jsonlist.length(); j++) {
                        JSONObject jsonlistObject = jsonlist.getJSONObject(j);
                        CategoryModel model = new CategoryModel();
                        model.setCategory_id(jsonlistObject.getString("cat_id").toString());
                        model.setCategory_name(jsonlistObject.getString("web_title").toString());
                        model.setCategory_image(jsonlistObject.getString("web_image").toString());
                        CategoryListActivity.this.categoryModelList.add(model);
                        CategoryListActivity.this.gv_category.setAdapter(new AllCategoryAdapter(CategoryListActivity.this,R.layout.all_category_list, CategoryListActivity.this.categoryModelList));
                        CategoryListActivity.this.gv_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(CategoryListActivity.this, SubCategoryActivity.class);
                                SubCategoryActivity.subcourcemodel = (CategoryModel) CategoryListActivity.this.categoryModelList.get(i);
                                CategoryListActivity.this.startActivity(intent);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    CategoryListActivity.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                CategoryListActivity.this.progressDialog.dismiss();
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                return params;
            }
        });
    }

    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        PlanChildModel child2 = this.adapter.getChild(groupPosition, childPosition);
        Intent intent = new Intent(getApplicationContext(), ComboOfferActivity.class);
        intent.putExtra("page", "slider");
        intent.putExtra("id", this.adapter.getChild(groupPosition, childPosition).getChild_id());
        startActivity(intent);
        return false;
    }

    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return false;
    }

    public void onGroupCollapse(int groupPosition) {
    }

    public void onGroupExpand(int groupPosition) {
        int i = this.lastExpandedPosition;
        if (!(i == -1 || groupPosition == i)) {
            this.elvMyListView.collapseGroup(i);
        }
        this.lastExpandedPosition = groupPosition;
    }
}
