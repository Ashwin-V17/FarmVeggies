package com.shop.veggies.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.shop.veggies.R;
import com.shop.veggies.activity.ComboOfferActivity;
import com.shop.veggies.activity.MegaOfferActivity;
import com.shop.veggies.adapter.BannerAdapter;
import com.shop.veggies.adapter.CategoryAdapter;
import com.shop.veggies.adapter.ProductRecomAdapter;
import com.shop.veggies.adapter.Products1Adapter;
import com.shop.veggies.adapter.ProductsAdapter;
import com.shop.veggies.adapter.SliderListAdapter;
import com.shop.veggies.model.BannerModel;
import com.shop.veggies.model.CategoryModel;
import com.shop.veggies.model.ProductsModel;
import com.shop.veggies.model.SliderListModel;
import com.shop.veggies.model.WeightModel;
import com.shop.veggies.utils.BSession;
import com.shop.veggies.utils.ProductConfig;
//import com.synnapps.carouselview.CarouselView;
//import com.synnapps.carouselview.ImageClickListener;
//import com.synnapps.carouselview.ViewListener;
//import com.denzcoskun.imageslideshow.ImageSlideshow;
//import com.denzcoskun.imageslideshow.models.SlideModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class HomeFragment extends Fragment {
    /* access modifiers changed from: private */
    public static final Integer[] XMEN;
    /* access modifiers changed from: private */
    public static int currentPage = 0;
    /* access modifiers changed from: private */
    public static ViewPager mPager;
    String FcmToken;
    private ArrayList<Integer> XMENArray = new ArrayList<>();

    private ViewPager2 viewPager;
    private SliderAdapter sliderAdapter;
    List<BannerModel> apiSliderList = new ArrayList<BannerModel>();
    String baseUrl;
    ImageView carousal_bottom;
    ImageView carousal_static;
    String cat_id = "12";
    CategoryAdapter categoryAdapter;
    /* access modifiers changed from: private */
    public List<CategoryModel> categoryModelList = new ArrayList();
    LinearLayout closed_ll;

//    CarouselView customCarouselView;
    ImageView customCarouselVieww;
    CardView cv_cardoff;
    String deviceid;
    FrameLayout fl_combo;
    GridView gv_category;
    RecyclerView gv_category1;
    GridView gv_offer;
    CircleIndicator indicator;
    String ipaddress;
    LinearLayoutManager linearLayoutManager;
    NestedScrollView nest;
    String para_str;
    ProductRecomAdapter productRecomAdapter;
    Products1Adapter products1Adapter;
    ProductsAdapter productsAdapter;
    /* access modifiers changed from: private */
    public List<ProductsModel> productsModelList;

    private List<ProductsModel> productsModelList1 = new ArrayList();
    ProgressDialog progressDialog;
    /* access modifiers changed from: private */
    public RecyclerView rv_bestSeller;
    private RecyclerView rv_mega;
    /* access modifiers changed from: private */
    public RecyclerView rv_rec_item;
    /* access modifiers changed from: private */
    public RecyclerView rv_slider1;
    SliderListAdapter sliderListAdapter;
    /* access modifiers changed from: private */
    public List<SliderListModel> sliderListModelList;
    BannerModel sliderModel = new BannerModel();
    TextView tv_offer;
    String user_id = "";
    View view;
//    ViewListener viewListener = new ViewListener() {
//        public View setViewForPosition(int position) {
//            View customView = HomeFragment.this.getLayoutInflater().inflate(R.layout.slide, (ViewGroup) null);
//            ((RequestBuilder) Glide.with(HomeFragment.this.getActivity()).load(HomeFragment.this.apiSliderList.get(position).getBanner_image()).placeholder((int) R.drawable.dummycat)).into((ImageView) customView.findViewById(R.id.image));
//            ((RequestBuilder) Glide.with(HomeFragment.this.getActivity()).load(HomeFragment.this.apiSliderList.get(2).getBanner_image()).placeholder((int) R.drawable.dummycat)).into(HomeFragment.this.carousal_static);
//            ((RequestBuilder) Glide.with(HomeFragment.this.getActivity()).load(HomeFragment.this.apiSliderList.get(1).getBanner_image()).placeholder((int) R.drawable.dummycat)).into(HomeFragment.this.carousal_bottom);
//            HomeFragment.this.customCarouselView.setImageClickListener(new ImageClickListener() {
//                public void onClick(int position) {
//                    Intent intent = new Intent(HomeFragment.this.getActivity(), OfferActivity.class);
//                    intent.putExtra("page", "brand");
//                    intent.putExtra("id", HomeFragment.this.apiSliderList.get(position).getBanner_id());
//                    HomeFragment.this.startActivity(intent);
//                }
//            });
//            return customView;
//        }
//    };
    ImageView whats;

    static /* synthetic */ int access$608() {
        int i = currentPage;
        currentPage = i + 1;
        return i;
    }

    static {
        Integer valueOf = Integer.valueOf(R.drawable.dummy);
        XMEN = new Integer[]{valueOf, valueOf, valueOf, valueOf, valueOf};
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_home, container, false);
        ProgressDialog progressDialog2 = new ProgressDialog(getActivity());
        this.progressDialog = progressDialog2;
        progressDialog2.setMessage("Loading.....");
        this.deviceid = Settings.Secure.getString(getContext().getContentResolver(), "android_id");
        this.user_id = BSession.getInstance().getUser_id(getActivity());
        this.carousal_static = (ImageView) this.view.findViewById(R.id.carousal_static);
        this.carousal_bottom = (ImageView) this.view.findViewById(R.id.carousal_bottom);
        this.viewPager = this.view.findViewById(R.id.viewPager);
        this.gv_category = (GridView) this.view.findViewById(R.id.gv_category);
        this.gv_category1 = (RecyclerView) this.view.findViewById(R.id.gv_category1);
        this.rv_slider1 = (RecyclerView) this.view.findViewById(R.id.rv_slider1);
        this.rv_bestSeller = (RecyclerView) this.view.findViewById(R.id.rv_bestSeller);
        this.gv_offer = (GridView) this.view.findViewById(R.id.gv_offer);
        this.tv_offer = (TextView) this.view.findViewById(R.id.tv_offer);
        this.rv_rec_item = (RecyclerView) this.view.findViewById(R.id.rv_rec_item);
        this.fl_combo = (FrameLayout) this.view.findViewById(R.id.fl_combo);
        this.cv_cardoff = (CardView) this.view.findViewById(R.id.cv_cardoff);
        this.whats = (ImageView) this.view.findViewById(R.id.whats);
        this.nest = (NestedScrollView) this.view.findViewById(R.id.nest);
        this.closed_ll = (LinearLayout) this.view.findViewById(R.id.closed_ll);
        this.customCarouselVieww = (ImageView) this.view.findViewById(R.id.customCarouselVieww);
        this.whats.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent msg = new Intent("android.intent.action.VIEW");
                    msg.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + "+91 7299232425" + "&text=" + "Your Message to Contact Number"));
                    HomeFragment.this.startActivity(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        loadleave();
        loadslider();
        setCategoryList();
        setSlider();
        setBestSeller();
        setRecItem();
        setOfferGrid();
        return this.view;
    }

    private void loadleave() {
        final Map<String, String> params = new HashMap<>();
        this.progressDialog.show();
        StringRequest jsObjRequest = new StringRequest(0, ProductConfig.appstatus, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                        HomeFragment.this.progressDialog.dismiss();
                        return;
                    }
                    HomeFragment.this.progressDialog.dismiss();
                    if (jsonResponse.getString("app_status").equalsIgnoreCase("Enable")) {
                        HomeFragment.this.closed_ll.setVisibility(View.GONE);
                        HomeFragment.this.nest.setVisibility(View.VISIBLE);
                        HomeFragment.this.whats.setVisibility(View.VISIBLE);
                    } else {
                        HomeFragment.this.closed_ll.setVisibility(View.VISIBLE);
                        HomeFragment.this.nest.setVisibility(View.GONE);
                        HomeFragment.this.whats.setVisibility(View.GONE);
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
        };
        Volley.newRequestQueue(getContext()).add(jsObjRequest);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
    }

    private void loadslider() {
        final Map<String, String> params = new HashMap<>();
        this.apiSliderList = new ArrayList();
        this.sliderModel = new BannerModel();
        Volley.newRequestQueue(getActivity()).add(new StringRequest(0, ProductConfig.bannerlist, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        Toast.makeText(HomeFragment.this.getActivity(), "No slider result found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JSONArray jsonResarray = jsonResponse.getJSONArray("storeList");
                    for (int i = 0; i < jsonResarray.length(); i++) {
                        JSONObject jsonObject = jsonResarray.getJSONObject(i);
                        BannerModel model = new BannerModel();
                        model.setBanner_id(jsonObject.getString("banner_id"));
                        model.setBanner_title(jsonObject.getString("web_title"));
                        model.setBanner_image(jsonObject.getString("banner_image"));
                        HomeFragment.this.apiSliderList.add(model);
                    }
                    HomeFragment.this.setUpSlider();
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

    private Handler handler = new Handler();
    private Runnable runnable;


    private void startAutoSlide() {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == apiSliderList.size()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 3000); // 3-second interval
            }
        };
        handler.postDelayed(runnable, 3000);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable); // Stop auto-sliding when fragment is destroyed
    }
       /* access modifiers changed from: private */
    public void setUpSlider() {
        if (apiSliderList != null && !apiSliderList.isEmpty()) {
            sliderAdapter = new SliderAdapter(requireContext(), apiSliderList);
            viewPager.setAdapter(sliderAdapter);

            // Optional: Auto-slide feature
            startAutoSlide();
        }
    }


    public void getpopup() {
        final Map<String, String> params = new HashMap<>();
        String user_id2 = BSession.getInstance().getUser_id(getActivity());
        Volley.newRequestQueue(getActivity()).add(new StringRequest(0, ProductConfig.offerimage, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        ((RequestBuilder) Glide.with(HomeFragment.this.getActivity()).load(jsonResponse.getString("offer_image")).placeholder((int) R.drawable.dummycat)).into(HomeFragment.this.customCarouselVieww);
                        HomeFragment.this.customCarouselVieww.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                HomeFragment.this.startActivity(new Intent(HomeFragment.this.getActivity(), MegaOfferActivity.class));
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

    private void setRecItem() {
        final Map<String, String> params = new HashMap<>();
        String customer_id = BSession.getInstance().getUser_id(getContext());
        if (customer_id.equalsIgnoreCase("")) {
            this.para_str = "?ip_address=" + this.deviceid;
            this.baseUrl = ProductConfig.recommentedproduct + this.para_str;
        } else {
            this.para_str = "?user_id=" + customer_id;
            this.baseUrl = ProductConfig.userrecomendedproducts + this.para_str;
        }
        Volley.newRequestQueue(getActivity()).add(new StringRequest(0, this.baseUrl, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        List unused = HomeFragment.this.productsModelList = new ArrayList();
                        JSONArray array = jsonResponse.getJSONArray("storeList");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jSONObject = array.getJSONObject(i);
                            ProductsModel model = new ProductsModel();
                            model.setProduct_id(array.getJSONObject(i).getString("pid"));
                            model.setCid(array.getJSONObject(i).getString("cid"));
                            model.setScid(array.getJSONObject(i).getString("scid"));
                            model.setProduct_name(array.getJSONObject(i).getString("productname"));
                            model.setOffer_status(array.getJSONObject(i).getString("offerstatus"));
                            model.setProduct_offer(array.getJSONObject(i).getString("offerpercentage"));
                            model.setProduct_image(array.getJSONObject(i).getString("productimage"));
                            model.setProduct_quantity(array.getJSONObject(i).getString("qty"));
                            model.setProduct_desc(array.getJSONObject(i).getString("description"));
                            model.setProduct_status(array.getJSONObject(i).getString("product_status"));
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
                                HomeFragment.this.productsModelList.add(model);
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
                                HomeFragment.this.productsModelList.add(model);
                            }
                        }
                        HomeFragment.this.rv_rec_item.setLayoutManager(new LinearLayoutManager(HomeFragment.this.getActivity(), RecyclerView.VERTICAL, false));
                        HomeFragment.this.rv_rec_item.setHasFixedSize(true);
                        HomeFragment.this.productRecomAdapter = new ProductRecomAdapter(HomeFragment.this.getActivity(), HomeFragment.this.productsModelList);
                        HomeFragment.this.rv_rec_item.setAdapter(HomeFragment.this.productRecomAdapter);
                        HomeFragment.this.rv_rec_item.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(HomeFragment.this.getActivity(), R.anim.layout_animation));
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

    private void setBestSeller() {
        this.productsModelList = new ArrayList();
        this.progressDialog.show();
        final Map<String, String> params = new HashMap<>();
        String customer_id = BSession.getInstance().getUser_id(getContext());
        if (customer_id.equalsIgnoreCase("")) {
            this.para_str = "?ip_address=" + this.deviceid;
            this.baseUrl = ProductConfig.bestseller + this.para_str;
        } else {
            this.para_str = "?user_id=" + customer_id;
            this.baseUrl = ProductConfig.userbestseller + this.para_str;
        }
        Volley.newRequestQueue(getActivity()).add(new StringRequest(0, this.baseUrl, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("products Response", response.toString());

                    HomeFragment.this.progressDialog.dismiss();
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                            HomeFragment.this.progressDialog.dismiss();
                           // Toast.makeText(HomeFragment.this.getActivity(), "No Product Result Found", 0).show();
                            return;
                        }
                        List unused = HomeFragment.this.productsModelList = new ArrayList();
                        JSONArray array = jsonResponse.getJSONArray("storeList");
                        for (int i = 0; i < 8; i++) {
                            JSONObject jSONObject = array.getJSONObject(i);
                            ProductsModel productsModel = new ProductsModel();
                            productsModel.setProduct_id(array.getJSONObject(i).getString("pid"));
                            productsModel.setCid(array.getJSONObject(i).getString("cid"));
                            productsModel.setScid(array.getJSONObject(i).getString("scid"));
                            productsModel.setProduct_name(array.getJSONObject(i).getString("productname"));
                            productsModel.setProduct_image(array.getJSONObject(i).getString("productimage"));
                            productsModel.setOffer_status(array.getJSONObject(i).getString("offerstatus"));
                            Log.e("offerstatus", productsModel.getOffer_status());
                            productsModel.setProduct_quantity(array.getJSONObject(i).getString("qty"));
                            productsModel.setProduct_desc(array.getJSONObject(i).getString("description"));
                            productsModel.setProduct_status(array.getJSONObject(i).getString("product_status"));
                            productsModel.setProduct_offer(array.getJSONObject(i).getString("offerpercentage"));
                            JSONArray weightArr = array.getJSONObject(i).getJSONArray("list");
                            if (weightArr == null || weightArr.length() <= 0) {
                                List<WeightModel> weightModelList = new ArrayList<>();
                                WeightModel weightModel = new WeightModel();
                                weightModel.setWid("0");
                                weightModel.setWeb_price("0");
                                weightModel.setWeb_title("0 kg");
                                weightModel.setMrp("0");
                                weightModelList.add(weightModel);
                                productsModel.setWeight(weightModelList);
                                HomeFragment.this.productsModelList.add(productsModel);
                            } else {
                                List<WeightModel> weightModelList2 = new ArrayList<>();
                                for (int j = 0; j < weightArr.length(); j++) {
                                    JSONObject jSONObject2 = weightArr.getJSONObject(j);
                                    WeightModel weightModel2 = new WeightModel();
                                    weightModel2.setWid(weightArr.getJSONObject(j).getString("vid"));
                                    weightModel2.setWeb_title(weightArr.getJSONObject(j).getString("weight"));
                                    weightModel2.setWeb_price(weightArr.getJSONObject(j).getString(FirebaseAnalytics.Param.PRICE));
                                    weightModel2.setMrp(weightArr.getJSONObject(j).getString("mrp"));
                                    weightModelList2.add(weightModel2);
                                }
                                productsModel.setWeight(weightModelList2);
                                HomeFragment.this.productsModelList.add(productsModel);
                            }
                        }
                        HomeFragment.this.productsAdapter = new ProductsAdapter(HomeFragment.this.getActivity(), HomeFragment.this.productsModelList);
                        HomeFragment.this.rv_bestSeller.setHasFixedSize(true);
                        HomeFragment.this.linearLayoutManager = new LinearLayoutManager(HomeFragment.this.getActivity(), RecyclerView.VERTICAL, false);
                        HomeFragment.this.rv_bestSeller.setLayoutManager(HomeFragment.this.linearLayoutManager);
                        HomeFragment.this.rv_bestSeller.setAdapter(HomeFragment.this.productsAdapter);
                        HomeFragment.this.rv_bestSeller.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(HomeFragment.this.getActivity(), R.anim.layout_animation));
                    } catch (JSONException e) {
                        e = e;
                        e.printStackTrace();
                        HomeFragment.this.progressDialog.dismiss();
                    }

            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                HomeFragment.this.progressDialog.dismiss();
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                return params;
            }
        });
    }

    private void setSlider() {
        final Map<String, String> params = new HashMap<>();
        this.sliderListModelList = new ArrayList();
        Volley.newRequestQueue(getActivity()).add(new StringRequest(0, ProductConfig.sliderlist, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        Toast.makeText(HomeFragment.this.getActivity(), "No slider result found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JSONArray jsonResarray = jsonResponse.getJSONArray("storeList");
                    for (int i = 0; i < jsonResarray.length(); i++) {
                        JSONObject jsonObject = jsonResarray.getJSONObject(i);
                        SliderListModel model = new SliderListModel();
                        model.setCid(jsonObject.getString("cid"));
                        model.setSl_id(jsonObject.getString("scid"));
                        model.setSl_name(jsonObject.getString("subcategoryname"));
                        model.setSl_image(jsonObject.getString("sliderimage"));
                        HomeFragment.this.sliderListModelList.add(model);
                    }
                    HomeFragment.this.rv_slider1.setLayoutManager(new LinearLayoutManager(HomeFragment.this.getActivity(), RecyclerView.VERTICAL, false));
                    HomeFragment.this.rv_slider1.setHasFixedSize(true);
                    HomeFragment.this.sliderListAdapter = new SliderListAdapter(HomeFragment.this.getActivity(), HomeFragment.this.sliderListModelList);
                    HomeFragment.this.rv_slider1.setAdapter(HomeFragment.this.sliderListAdapter);
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

//    private void NetwordDetect() {
//        boolean WIFI = false;
//        boolean MOBILE = false;
//        for (NetworkInfo netInfo : ((ConnectivityManager) getContext().getSystemService("connectivity")).getAllNetworkInfo()) {
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

    public String GetDeviceipWiFiData() {
        try {
            WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int ipAddress = wifiInfo.getIpAddress();

                // Convert int IP to String
                byte[] ipByteArray = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(ipAddress).array();
                InetAddress inetAddress = InetAddress.getByAddress(ipByteArray);
                return inetAddress.getHostAddress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0.0.0.0"; // Return default if no IP is found
    }
    private void banner() {
        int i = 0;
        while (true) {
            Integer[] numArr = XMEN;
            if (i < numArr.length) {
                this.XMENArray.add(numArr[i]);
                i++;
            } else {
                ViewPager viewPager = (ViewPager) this.view.findViewById(R.id.pager);
                mPager = viewPager;
                viewPager.setAdapter(new BannerAdapter(getActivity(), this.XMENArray));
                ((CircleIndicator) this.view.findViewById(R.id.indicator)).setViewPager(mPager);
                final Handler handler = new Handler();
                final Runnable Update = new Runnable() {
                    public void run() {
                        if (HomeFragment.currentPage == HomeFragment.XMEN.length) {
                            int unused = HomeFragment.currentPage = 0;
                        }
                        HomeFragment.mPager.setCurrentItem(HomeFragment.access$608(), true);
                    }
                };
                new Timer().schedule(new TimerTask() {
                    public void run() {
                        handler.post(Update);
                    }
                }, 3000, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                return;
            }
        }
    }

    private void setCategoryList() {
        this.categoryModelList = new ArrayList();
        final Map<String, String> params = new HashMap<>();
        this.progressDialog.show();
        Volley.newRequestQueue(getActivity()).add(new StringRequest(0, ProductConfig.categorylist, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    HomeFragment.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        Toast.makeText(HomeFragment.this.getActivity(), "No category list", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JSONArray jsonlist = jsonResponse.getJSONArray("storeList");
                    for (int j = 0; j < jsonlist.length(); j++) {
                        JSONObject jsonlistObject = jsonlist.getJSONObject(j);
                        CategoryModel model = new CategoryModel();
                        if (!HomeFragment.this.cat_id.equals(jsonlistObject.getString("cat_id"))) {
                            model.setCategory_id(jsonlistObject.getString("cat_id").toString());
                            model.setCategory_name(jsonlistObject.getString("web_title").toString());
                            model.setCategory_image(jsonlistObject.getString("web_image").toString());
                            HomeFragment.this.categoryModelList.add(model);
                            HomeFragment.this.gv_category1.setLayoutManager(new LinearLayoutManager(HomeFragment.this.getActivity(), RecyclerView.VERTICAL, false));
                            HomeFragment.this.categoryAdapter = new CategoryAdapter(HomeFragment.this.getActivity(), HomeFragment.this.categoryModelList);
                            HomeFragment.this.gv_category1.setAdapter(HomeFragment.this.categoryAdapter);
                            HomeFragment.this.gv_category1.setLayoutManager(new GridLayoutManager(HomeFragment.this.getActivity(), 2));
                            HomeFragment.this.gv_category1.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(HomeFragment.this.getActivity(), R.anim.layout_animation));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    HomeFragment.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                HomeFragment.this.progressDialog.dismiss();
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                return params;
            }
        });
    }

    private void setOfferGrid() {
        this.progressDialog.show();
        this.productsModelList = new ArrayList();
        final Map<String, String> params = new HashMap<>();
        String customer_id = BSession.getInstance().getUser_id(getContext());
        if (customer_id.equalsIgnoreCase("")) {
            this.para_str = "?ip_address=" + this.deviceid;
            this.baseUrl = ProductConfig.combooffer + this.para_str;
        } else {
            this.para_str = "?user_id=" + customer_id;
            this.baseUrl = ProductConfig.usercombooffer + this.para_str;
        }
        StringRequest jsObjRequest = new StringRequest(0, this.baseUrl, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    HomeFragment.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        List unused = HomeFragment.this.productsModelList = new ArrayList();
                        JSONArray array = jsonResponse.getJSONArray("storeList");
                        for (int i = 0; i < 8; i++) {
                            JSONObject jSONObject = array.getJSONObject(i);
                            ProductsModel model = new ProductsModel();
                            model.setProduct_id(array.getJSONObject(i).getString("pid"));
                            model.setCid(array.getJSONObject(i).getString("cid"));
                            model.setScid(array.getJSONObject(i).getString("scid"));
                            model.setProduct_name(array.getJSONObject(i).getString("productname"));
                            model.setOffer_status(array.getJSONObject(i).getString(NotificationCompat.CATEGORY_STATUS));
                            model.setProduct_offer(array.getJSONObject(i).getString("offerpercentage"));
                            model.setProduct_image(array.getJSONObject(i).getString("productimage"));
                            model.setProduct_quantity(array.getJSONObject(i).getString("qty"));
                            model.setProduct_desc(array.getJSONObject(i).getString("description"));
                            model.setProduct_status(array.getJSONObject(i).getString("product_status"));
                            TextView textView = HomeFragment.this.tv_offer;
                            textView.setText(array.getJSONObject(i).getString("percentage") + " \n Offer");
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
                                HomeFragment.this.productsModelList.add(model);
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
                                HomeFragment.this.productsModelList.add(model);
                            }
                        }
                        HomeFragment.this.products1Adapter = new Products1Adapter(HomeFragment.this.getActivity(), R.layout.layout_offer_home_list, HomeFragment.this.productsModelList);
                        HomeFragment.this.gv_offer.setAdapter(HomeFragment.this.products1Adapter);
                        HomeFragment.this.gv_offer.setClipToPadding(false);
                        HomeFragment.this.gv_offer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(HomeFragment.this.getActivity(), ComboOfferActivity.class);
                                intent.putExtra("page", "combo");
                                intent.putExtra("id", "0");
                                HomeFragment.this.startActivity(intent);
                            }
                        });
                        HomeFragment.this.fl_combo.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                Intent intent = new Intent(HomeFragment.this.getActivity(), ComboOfferActivity.class);
                                intent.putExtra("page", "combo");
                                intent.putExtra("id", "0");
                                HomeFragment.this.startActivity(intent);
                            }
                        });
                    } else if (jsonResponse.has("result")) {
                        jsonResponse.getString("result").equals("failed");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    HomeFragment.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                HomeFragment.this.progressDialog.dismiss();
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                return params;
            }
        };
        Volley.newRequestQueue(getActivity()).add(jsObjRequest);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 1, 1.0f));
    }
}
