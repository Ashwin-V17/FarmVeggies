package com.shop.veggies.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.shop.veggies.R;
import com.shop.veggies.activity.CartActivity;
import com.shop.veggies.adapter.ProductListAdapter;
import com.shop.veggies.model.ProductsModel;
import com.shop.veggies.model.WeightModel;
import com.shop.veggies.utils.BSession;
import com.shop.veggies.utils.ProductConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TutionMyclassDynamicFragment extends Fragment {
    AutoCompleteTextView auto_search;
    String baseUrl;
    Button btn_checkout;
    String deviceid;
    LinearLayoutManager linearLayoutManager;
    LinearLayout notes_ll;
    String para_str;
    String para_str1;
    int position;
    ProductListAdapter productListAdapter;
    ProductsModel productsModel = new ProductsModel();
    List<ProductsModel> productsModelList = new ArrayList();
    ProgressDialog progressDialog;
    RecyclerView rv_menuList;
    String scid = "";
    private TextView textView;
    String user_id = "";

    public static Fragment getInstance(int position2, String scid2) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position2);
        bundle.putString("scid", scid2);
        TutionMyclassDynamicFragment tabFragment = new TutionMyclassDynamicFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.position = getArguments().getInt("pos");
        this.scid = getArguments().getString("scid");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tution_myclass_dynamic, container, false);
        ProgressDialog progressDialog2 = new ProgressDialog(getActivity());
        this.progressDialog = progressDialog2;
        progressDialog2.setMessage("Loading.....");
        this.deviceid = Settings.Secure.getString(getContext().getContentResolver(), "android_id");
        this.user_id = BSession.getInstance().getUser_id(getActivity());
        this.rv_menuList = (RecyclerView) view.findViewById(R.id.rv_menuList);
        this.btn_checkout = (Button) view.findViewById(R.id.btn_checkout);
        this.auto_search = (AutoCompleteTextView) view.findViewById(R.id.auto_search);
        this.btn_checkout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TutionMyclassDynamicFragment.this.startActivity(new Intent(TutionMyclassDynamicFragment.this.getContext(), CartActivity.class));
            }
        });
        loadProductList();
        this.auto_search.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                TutionMyclassDynamicFragment.this.filter(String.valueOf(cs));
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void afterTextChanged(Editable arg0) {
            }
        });
        return view;
    }

    /* access modifiers changed from: private */
    public void filter(String text) {
        ArrayList<ProductsModel> filteredlist = new ArrayList<>();
        for (ProductsModel item : this.productsModelList) {
            if (item.getProduct_name().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            this.productListAdapter.filterList(filteredlist);
        }
    }

    private void loadProductList() {
        this.productsModelList = new ArrayList();
        final Map<String, String> params = new HashMap<>();
        if (BSession.getInstance().getUser_id(getContext()).equalsIgnoreCase("")) {
            this.para_str = "?ip_address=" + this.deviceid;
            this.para_str1 = "&scid=" + this.scid;
            this.baseUrl = ProductConfig.productlist + this.para_str + this.para_str1;
        } else {
            this.para_str = "?user_id=" + this.user_id;
            this.para_str1 = "&scid=" + this.scid;
            this.baseUrl = ProductConfig.productlist1 + this.para_str + this.para_str1;
        }
        Volley.newRequestQueue(getActivity()).add(new StringRequest(0, this.baseUrl, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        Toast.makeText(TutionMyclassDynamicFragment.this.getActivity(), "No Product Result Found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    TutionMyclassDynamicFragment.this.productsModelList = new ArrayList();
                    JSONArray array = jsonResponse.getJSONArray("storeList");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jSONObject = array.getJSONObject(i);
                        ProductsModel model = new ProductsModel();
                        model.setProduct_id(array.getJSONObject(i).getString("pid"));
                        model.setCid(array.getJSONObject(i).getString("cid"));
                        model.setScid(array.getJSONObject(i).getString("scid"));
                        model.setProduct_name(array.getJSONObject(i).getString("productname"));
                        model.setOffer_status(array.getJSONObject(i).getString("offerstatus"));
                        model.setProduct_image(array.getJSONObject(i).getString("productimage"));
                        model.setProduct_quantity(array.getJSONObject(i).getString("qty"));
                        model.setProduct_desc(array.getJSONObject(i).getString("description"));
                        model.setProduct_status(array.getJSONObject(i).getString("product_status"));
                        model.setProduct_offer(array.getJSONObject(i).getString("offerpercentage"));
                        JSONArray weightArr = array.getJSONObject(i).getJSONArray("list");
                        if (weightArr == null || weightArr.length() <= 0) {
                            List<WeightModel> weightModelList = new ArrayList<>();
                            WeightModel weightModel = new WeightModel();
                            weightModel.setWid("0");
                            weightModel.setWeb_price("0");
                            weightModel.setWeb_title("0");
                            weightModel.setMrp("0");
                            weightModelList.add(weightModel);
                            model.setWeight(weightModelList);
                            TutionMyclassDynamicFragment.this.productsModelList.add(model);
                        } else {
                            List<WeightModel> weightModelList2 = new ArrayList<>();
                            for (int j = 0; j < weightArr.length(); j++) {
                                JSONObject jSONObject2 = weightArr.getJSONObject(j);
                                WeightModel weightModel2 = new WeightModel();
                                weightModel2.setWid(weightArr.getJSONObject(j).getString("vid"));
                                weightModel2.setWeb_price(weightArr.getJSONObject(j).getString(FirebaseAnalytics.Param.PRICE));
                                weightModel2.setWeb_title(weightArr.getJSONObject(j).getString("weight"));
                                weightModel2.setMrp(weightArr.getJSONObject(j).getString("mrp"));
                                weightModelList2.add(weightModel2);
                            }
                            model.setWeight(weightModelList2);
                            TutionMyclassDynamicFragment.this.productsModelList.add(model);
                        }
                    }
                    TutionMyclassDynamicFragment.this.rv_menuList.setLayoutManager(new LinearLayoutManager(TutionMyclassDynamicFragment.this.getActivity(), RecyclerView.VERTICAL, false));
                    TutionMyclassDynamicFragment.this.productListAdapter = new ProductListAdapter(TutionMyclassDynamicFragment.this.getActivity(), TutionMyclassDynamicFragment.this.productsModelList);
                    TutionMyclassDynamicFragment.this.rv_menuList.setAdapter(TutionMyclassDynamicFragment.this.productListAdapter);
                    TutionMyclassDynamicFragment.this.rv_menuList.setHasFixedSize(true);
                    TutionMyclassDynamicFragment.this.rv_menuList.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(TutionMyclassDynamicFragment.this.getContext(), R.anim.layout_animation));
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

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
