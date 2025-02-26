package com.shop.veggies.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.internal.view.SupportMenu;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shop.veggies.R;
import com.shop.veggies.adapter.OrderDeatilAdapter;
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

public class OrderDetailActivity extends AppCompatActivity {
    EditText Review;
    Button can_btn;
    TextView deliverydate;
    AlertDialog dialog;
    TextView item_tolprice;
    LinearLayoutManager linearLayoutManager;
    MyordersModel myordersModel = new MyordersModel();
    List<MyordersModel> myordersModelList = new ArrayList();
    OrderDeatilAdapter orderDeatilAdapter;
    TextView order_cancel_tx;
    TextView order_status_tx;
    TextView orderdate;
    TextView orderid;
    RecyclerView oreder_itemry;
    ProgressDialog progressDialog;
    LinearLayout ratevaluell;
    String rating1;
    RatingBar ratingBar;
    RatingBar ratingBar2;
    TextView reatll;
    TextView returntx;
    String review1;
    TextView review2;
    String review_product;
    String rtv;
    String status;
    TextView title_rate;
    TextView tv_pymt;
    TextView tv_subtotal;
    TextView user_add;
    TextView user_mn;
    TextView user_name;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_order_detail);
        ProgressDialog progressDialog2 = new ProgressDialog(this);
        this.progressDialog = progressDialog2;
        progressDialog2.setMessage("Loading.....");
        toolbar();
        this.orderid = (TextView) findViewById(R.id.orderid);
        this.orderdate = (TextView) findViewById(R.id.orderdate);
        this.user_name = (TextView) findViewById(R.id.user_name);
        this.user_add = (TextView) findViewById(R.id.user_add);
        this.user_mn = (TextView) findViewById(R.id.user_mn);
        this.tv_subtotal = (TextView) findViewById(R.id.tv_subtotal);
        this.tv_pymt = (TextView) findViewById(R.id.tv_pymt);
        this.item_tolprice = (TextView) findViewById(R.id.item_tolprice);
        this.order_status_tx = (TextView) findViewById(R.id.order_status_tx);
        this.deliverydate = (TextView) findViewById(R.id.deliverydate);
        getBundle();
        this.ratevaluell = (LinearLayout) findViewById(R.id.ratevaluell);
        this.review2 = (TextView) findViewById(R.id.review);
        this.ratingBar2 = (RatingBar) findViewById(R.id.ratingBar2);
        this.reatll = (TextView) findViewById(R.id.ratell);
        this.oreder_itemry = (RecyclerView) findViewById(R.id.item_orderry);
        this.can_btn = (Button) findViewById(R.id.can_btn);
        this.returntx = (TextView) findViewById(R.id.return_tx);
        this.order_cancel_tx = (TextView) findViewById(R.id.order_cancel_tx);
        this.title_rate = (TextView) findViewById(R.id.title_rate);
        this.returntx.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OrderDetailActivity.this.startActivity(new Intent(OrderDetailActivity.this, ReturnProductActivity.class).putExtra("orderid", OrderDetailActivity.this.orderid.getText().toString()));
            }
        });
        this.can_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailActivity.this, R.style.AlertDialogTheme);
                builder.setMessage((CharSequence) OrderDetailActivity.this.getApplicationContext().getResources().getString(R.string.alert_want_cancel)).setCancelable(false).setPositiveButton((CharSequence) OrderDetailActivity.this.getApplicationContext().getResources().getString(R.string.alert_yes), (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        OrderDetailActivity.this.loadcancelbtn();
                        dialog.cancel();
                    }
                }).setNegativeButton((CharSequence) OrderDetailActivity.this.getApplicationContext().getResources().getString(R.string.alert_no), (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        this.reatll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View alertLayout = OrderDetailActivity.this.getLayoutInflater().inflate(R.layout.review_layout, (ViewGroup) null);
                OrderDetailActivity.this.Review = (EditText) alertLayout.findViewById(R.id.review);
                OrderDetailActivity.this.ratingBar = (RatingBar) alertLayout.findViewById(R.id.ratingBar);
                AlertDialog.Builder alert = new AlertDialog.Builder(OrderDetailActivity.this);
                alert.setView(alertLayout);
                alert.setCancelable(false);
                OrderDetailActivity.this.dialog = alert.create();
                OrderDetailActivity.this.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        if (ratingBar.getRating() <= 3.0f) {
                            OrderDetailActivity.this.Review.setVisibility(View.VISIBLE);
                        } else if (ratingBar.getRating() <= 4.0f) {
                            OrderDetailActivity.this.Review.setVisibility(View.GONE);
                        } else if (ratingBar.getRating() <= 5.0f) {
                            OrderDetailActivity.this.Review.setVisibility(View.GONE);
                        }
                    }
                });
                ((Button) alertLayout.findViewById(R.id.submitRateBtn)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (OrderDetailActivity.this.ratingBar.getRating() == 0.0f) {
                            Toast.makeText(OrderDetailActivity.this.getApplicationContext(), "Please Rating", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        OrderDetailActivity.this.rating1 = String.valueOf(OrderDetailActivity.this.ratingBar.getRating());
                        OrderDetailActivity.this.review1 = OrderDetailActivity.this.Review.getText().toString();
                        OrderDetailActivity.this.loadratting();
                    }
                });
                OrderDetailActivity.this.dialog.show();
            }
        });
        getOrdersList();
    }

    private void getBundle() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.orderid.setText(extras.getString("order_id"));
            TextView textView = this.deliverydate;
            textView.setText("Delivery date on " + extras.getString("delivery_date"));
            TextView textView2 = this.orderdate;
            textView2.setText("Order placed on " + extras.getString("order_date"));
            return;
        }
        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
    }

    /* access modifiers changed from: private */
    public void loadratting() {
        final Map<String, String> params = new HashMap<>();
        if (this.rating1.equalsIgnoreCase(String.valueOf(1.0d))) {
            this.rtv = "1";
        } else if (this.rating1.equalsIgnoreCase(String.valueOf(2.0d))) {
            this.rtv = ExifInterface.GPS_MEASUREMENT_2D;
        } else if (this.rating1.equalsIgnoreCase(String.valueOf(3.0d))) {
            this.rtv = ExifInterface.GPS_MEASUREMENT_3D;
        } else if (this.rating1.equalsIgnoreCase(String.valueOf(4.0d))) {
            this.rtv = "4";
        } else if (this.rating1.equalsIgnoreCase(String.valueOf(5.0d))) {
            this.rtv = "5";
        } else {
            this.rtv = "0";
        }
        Volley.newRequestQueue(this).add(new StringRequest(0, ProductConfig.reviewupdate + ("?order_id=" + this.orderid.getText().toString()) + ("&review_status=" + this.rtv) + ("&review_comment=" + this.review1), new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        Toast.makeText(OrderDetailActivity.this, "Failed..!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    OrderDetailActivity.this.ratevaluell.setVisibility(View.VISIBLE);
                    OrderDetailActivity.this.review2.setVisibility(View.VISIBLE);
                    OrderDetailActivity.this.ratingBar2.setVisibility(View.VISIBLE);
                    OrderDetailActivity.this.reatll.setVisibility(View.GONE);
                    OrderDetailActivity.this.review_product = OrderDetailActivity.this.Review.getText().toString().trim();
                    OrderDetailActivity.this.review2.setText(OrderDetailActivity.this.review_product);
                    OrderDetailActivity.this.ratingBar2.setRating(Float.parseFloat(OrderDetailActivity.this.rating1));
                    OrderDetailActivity.this.dialog.cancel();
                    OrderDetailActivity.this.dialog.dismiss();
                    Toast.makeText(OrderDetailActivity.this, "Thank You for Your Rating", Toast.LENGTH_SHORT).show();
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

    /* access modifiers changed from: private */
    public void loadcancelbtn() {
        final Map<String, String> params = new HashMap<>();
        String para_str = "?order_id=" + this.orderid.getText().toString();
        Volley.newRequestQueue(getApplicationContext()).add(new StringRequest(0, ProductConfig.ordercancel + para_str + ("&customer_id=" + BSession.getInstance().getUser_id(getApplicationContext())), new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        OrderDetailActivity.this.can_btn.setVisibility(View.GONE);
                        OrderDetailActivity.this.order_cancel_tx.setVisibility(View.VISIBLE);
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

    private void getOrdersList() {
        this.myordersModelList = new ArrayList();
        final Map<String, String> params = new HashMap<>();
        this.progressDialog.show();
        Volley.newRequestQueue(this).add(new StringRequest(0, ProductConfig.myorderdetails + ("?order_id=" + this.orderid.getText().toString()), new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    OrderDetailActivity.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        Toast.makeText(OrderDetailActivity.this.getApplicationContext(), "No orders Result Found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    OrderDetailActivity.this.orderid.setText(jsonResponse.getString("order_id"));
                    OrderDetailActivity.this.user_name.setText(jsonResponse.getString("username"));
                    OrderDetailActivity.this.user_add.setText(jsonResponse.getString("address"));
                    OrderDetailActivity.this.user_mn.setText(jsonResponse.getString("phone"));
                    OrderDetailActivity.this.tv_subtotal.setText(jsonResponse.getString("total"));
                    OrderDetailActivity.this.tv_pymt.setText(jsonResponse.getString("payment_mode"));
                    OrderDetailActivity.this.item_tolprice.setText(jsonResponse.getString("grandtotal"));
                    OrderDetailActivity.this.status = jsonResponse.getString("review_status");
                    if (OrderDetailActivity.this.status.contentEquals("0")) {
                        OrderDetailActivity.this.ratevaluell.setVisibility(View.GONE);
                        OrderDetailActivity.this.ratingBar2.setVisibility(View.GONE);
                        OrderDetailActivity.this.review2.setVisibility(View.GONE);
                        OrderDetailActivity.this.reatll.setVisibility(View.VISIBLE);
                    } else {
                        OrderDetailActivity.this.ratevaluell.setVisibility(View.VISIBLE);
                        OrderDetailActivity.this.ratingBar2.setVisibility(View.VISIBLE);
                        OrderDetailActivity.this.review2.setVisibility(View.VISIBLE);
                        OrderDetailActivity.this.reatll.setVisibility(View.GONE);
                    }
                    OrderDetailActivity.this.review2.setText(jsonResponse.getString("review_comment"));
                    OrderDetailActivity.this.ratingBar2.setRating(Float.parseFloat(jsonResponse.getString("review_status")));
                    if (jsonResponse.getString("order_status").equalsIgnoreCase("1")) {
                        OrderDetailActivity.this.order_cancel_tx.setVisibility(View.GONE);
                        OrderDetailActivity.this.can_btn.setVisibility(View.VISIBLE);
                        OrderDetailActivity.this.order_status_tx.setText("ORDER PROCESS going on");
                    } else if (jsonResponse.getString("order_status").equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_2D)) {
                        OrderDetailActivity.this.order_cancel_tx.setVisibility(View.GONE);
                        OrderDetailActivity.this.can_btn.setVisibility(View.VISIBLE);
                        OrderDetailActivity.this.order_status_tx.setText("ORDER COMPLETED");
                        OrderDetailActivity.this.order_status_tx.setTextColor(OrderDetailActivity.this.getResources().getColor(R.color.white));
                    } else if (jsonResponse.getString("order_status").equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_3D)) {
                        OrderDetailActivity.this.ratevaluell.setVisibility(View.GONE);
                        OrderDetailActivity.this.reatll.setVisibility(View.GONE);
                        OrderDetailActivity.this.title_rate.setVisibility(View.GONE);
                        OrderDetailActivity.this.order_cancel_tx.setVisibility(View.VISIBLE);
                        OrderDetailActivity.this.can_btn.setVisibility(View.GONE);
                        OrderDetailActivity.this.order_status_tx.setText("ORDER CANCELLED");
                        OrderDetailActivity.this.order_status_tx.setTextColor(-65536);
                    }
                    JSONArray array = jsonResponse.getJSONArray("storeList");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jSONObject = array.getJSONObject(i);
                        MyordersModel myordersModel = new MyordersModel();
                        myordersModel.setProduct_name(array.getJSONObject(i).getString("product_name"));
                        myordersModel.setProduct_quantity(array.getJSONObject(i).getString("qty"));
                        myordersModel.setProduct_price(array.getJSONObject(i).getString("total"));
                        myordersModel.setProduct_weight(array.getJSONObject(i).getString("weight"));
                        myordersModel.setProduct_image(array.getJSONObject(i).getString("web_image"));
                        OrderDetailActivity.this.myordersModelList.add(myordersModel);
                    }
                    OrderDetailActivity.this.linearLayoutManager = new LinearLayoutManager(OrderDetailActivity.this, RecyclerView.VERTICAL, false);
                    OrderDetailActivity.this.oreder_itemry.setLayoutManager(OrderDetailActivity.this.linearLayoutManager);
                    OrderDetailActivity.this.orderDeatilAdapter = new OrderDeatilAdapter(OrderDetailActivity.this, OrderDetailActivity.this.myordersModelList);
                    OrderDetailActivity.this.oreder_itemry.setHasFixedSize(true);
                    OrderDetailActivity.this.oreder_itemry.setAdapter(OrderDetailActivity.this.orderDeatilAdapter);
                    OrderDetailActivity.this.orderDeatilAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    OrderDetailActivity.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                OrderDetailActivity.this.progressDialog.dismiss();
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
                OrderDetailActivity.this.onBackPressed();
            }
        });
        getSupportActionBar().setTitle((CharSequence) "Order Details");
        toolbar.setTitleTextColor(-1);
    }
}
