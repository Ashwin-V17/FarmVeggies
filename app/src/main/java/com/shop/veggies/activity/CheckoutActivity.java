package com.shop.veggies.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.shop.veggies.ExpandableHeightGridView;
import com.shop.veggies.R;
import com.shop.veggies.adapter.PincodeAdapter;
import com.shop.veggies.model.CartModel;
import com.shop.veggies.model.ZipcodeModel;
import com.shop.veggies.utils.BSession;
import com.shop.veggies.utils.ProductConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity implements PaymentResultListener, LocationListener {
    private static final String GOOGLE_TEZ_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    private static final String TAG = CheckoutActivity.class.getSimpleName();
    private static final int TEZ_REQUEST_CODE = 123;
    private static final int fail = 1;
    public static ZipcodeModel subcourcemodel = new ZipcodeModel();
    final int UPI_PAYMENT = 0;
    String address;
    List<ZipcodeModel> apiZipcodeList = new ArrayList();
    String baseUrl;
    Button cancel_btn;
    CartModel cartModel = new CartModel();
    List<CartModel> cartModelList = new ArrayList();
    Button confirm_btn;
    String customer_id = "";
    String date;
    String delivery = "";
    ArrayList<String> delivery_time = new ArrayList<>();
    String deviceid;
    AlertDialog dialog;
    Dialog dialogg;
    boolean doubleBackToExitPressedOnce = false;
    EditText et_address;
    EditText et_date;
    EditText et_name;
    EditText et_phone;
    EditText et_zipcode;
    String idd;
    String ipaddress;
    String latitude;
    int lisy = 0;
    LocationManager locationManager;
    String longitude;
    ExpandableHeightGridView lv_deliveryTime;
    String mobile;
    final Calendar myCalendar = Calendar.getInstance();
    String name;
    String num_id;
    double orderTotal = 0.0d;
    String order_date;
    String order_id;
    String order_sub;
    String order_total;
    String para_str;
    String payment_type = "unpaid";
    ArrayList<String> pincodelist = new ArrayList<>();
    ArrayList<String> pincodepricelist = new ArrayList<>();
    double point = 0.0d;
    ArrayList<String> pricelist = new ArrayList<>();
    ProgressDialog progressDialog;
    RadioGroup radiogroup;
    String selectedPaymentMethod = "COD";
    int thoufivpin;
    Toolbar toolbar;
    TextView tv_count;
    TextView tv_currentlocation;
    TextView tv_points;
    TextView txt_deliverycharge;
    TextView txt_subtotal;
    TextView txt_total;
    Boolean validZipcode = false;
    View view;
    String zipcode;
    ZipcodeModel zipcodeModel = new ZipcodeModel();


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_checkout);
        ProgressDialog progressDialog2 = new ProgressDialog(this);
        this.progressDialog = progressDialog2;
        progressDialog2.setMessage("Loading.....");
        this.et_date = (EditText) findViewById(R.id.et_date);
        this.deviceid = Settings.Secure.getString(getContentResolver(), "android_id");
        Checkout.preload(getApplicationContext());
        toolbar();
        if (!(ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(getApplicationContext(), "android.permission.ACCESS_COARSE_LOCATION") == 0)) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 101);
        }
        this.tv_count = (TextView) findViewById(R.id.tv_count);
        this.txt_total = (TextView) findViewById(R.id.tv_billtotal);
        this.txt_subtotal = (TextView) findViewById(R.id.tv_subtotal);
        this.txt_deliverycharge = (TextView) findViewById(R.id.tv_shippingcharge);
        this.et_zipcode = (EditText) findViewById(R.id.et_zipcode);
        getCartCount();
        setDelivery();
        this.et_name = (EditText) findViewById(R.id.et_name);
        this.et_phone = (EditText) findViewById(R.id.et_phone);
        this.et_address = (EditText) findViewById(R.id.et_add);
        String customer_name = BSession.getInstance().getUser_name(this);
        String customer_mobile = BSession.getInstance().getUser_mobile(this);
        String customer_address = BSession.getInstance().getUser_address(this);
        String pincode = BSession.getInstance().getPincode(this);
        this.et_name.setText(customer_name);
        this.et_phone.setText(customer_mobile);
        this.et_address.setText(customer_address);
        this.tv_currentlocation = (TextView) findViewById(R.id.cencel_butn);
        this.tv_currentlocation = (TextView) findViewById(R.id.tv_currentlocation);
        this.lv_deliveryTime = (ExpandableHeightGridView) findViewById(R.id.lv_deliveryTime);
        this.et_zipcode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckoutActivity.this.pincodde();
            }
        });
        this.tv_currentlocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckoutActivity.this.tv_currentlocation.startAnimation(AnimationUtils.loadAnimation(CheckoutActivity.this.getApplicationContext(), R.anim.fade_in));
                CheckoutActivity.this.progressDialog.show();
                CheckoutActivity.this.getLocation();
            }
        });
        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int month, int day) {
                CheckoutActivity.this.myCalendar.set(1, year);
                CheckoutActivity.this.myCalendar.set(2, month);
                CheckoutActivity.this.myCalendar.set(5, day);
                CheckoutActivity.this.updateLabel();
            }
        };
        this.et_date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CheckoutActivity checkoutActivity = CheckoutActivity.this;
                new DatePickerDialog(checkoutActivity, date2, checkoutActivity.myCalendar.get(1), CheckoutActivity.this.myCalendar.get(2), CheckoutActivity.this.myCalendar.get(5)).show();
            }
        });
    }

    /* access modifiers changed from: private */
    public void updateLabel() {
        this.et_date.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(this.myCalendar.getTime()));
        this.radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        listeners();
        this.cancel_btn = (Button) findViewById(R.id.cencel_butn);
        this.confirm_btn = (Button) findViewById(R.id.confirm_btn);
        this.cancel_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CheckoutActivity.this.cancel_btn.startAnimation(AnimationUtils.loadAnimation(CheckoutActivity.this.getApplicationContext(), R.anim.fade_in));
                CheckoutActivity.this.startActivity(new Intent(CheckoutActivity.this, MainActivity.class));
            }
        });
        PrintStream printStream = System.out;
        printStream.println("thuto---" + this.thoufivpin);
        this.confirm_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CheckoutActivity.this.confirm_btn.startAnimation(AnimationUtils.loadAnimation(CheckoutActivity.this.getApplicationContext(), R.anim.fade_in));
                CheckoutActivity checkoutActivity = CheckoutActivity.this;
                checkoutActivity.name = checkoutActivity.et_name.getText().toString().trim();
                CheckoutActivity checkoutActivity2 = CheckoutActivity.this;
                checkoutActivity2.mobile = checkoutActivity2.et_phone.getText().toString().trim();
                CheckoutActivity checkoutActivity3 = CheckoutActivity.this;
                checkoutActivity3.address = checkoutActivity3.et_address.getText().toString().trim();
                CheckoutActivity checkoutActivity4 = CheckoutActivity.this;
                checkoutActivity4.zipcode = checkoutActivity4.et_zipcode.getText().toString().trim();
                CheckoutActivity checkoutActivity5 = CheckoutActivity.this;
                checkoutActivity5.date = checkoutActivity5.et_date.getText().toString().trim();
                if (CheckoutActivity.this.name.isEmpty()) {
                    CheckoutActivity.this.et_name.setError("*Enter your name");
                    CheckoutActivity.this.et_name.requestFocus();
                } else if (CheckoutActivity.this.mobile.isEmpty()) {
                    CheckoutActivity.this.et_phone.setError("*Enter your mobile number");
                    CheckoutActivity.this.et_phone.requestFocus();
                } else if (CheckoutActivity.this.mobile.length() < 10 || CheckoutActivity.this.mobile.length() > 13) {
                    CheckoutActivity.this.et_phone.setError("*Enter your valid number");
                    CheckoutActivity.this.et_phone.requestFocus();
                } else if (CheckoutActivity.this.zipcode.isEmpty() || CheckoutActivity.this.zipcode.equalsIgnoreCase("")) {
                    CheckoutActivity.this.et_zipcode.setError("*Select your pincode");
                    CheckoutActivity.this.et_zipcode.requestFocus();
                    Toast.makeText(CheckoutActivity.this, "Choose your pin code", Toast.LENGTH_SHORT).show();
                } else if (CheckoutActivity.this.date.isEmpty()) {
                    CheckoutActivity.this.et_date.setError("*Enter your Date");
                    CheckoutActivity.this.et_date.requestFocus();
                } else if (CheckoutActivity.this.address.isEmpty()) {
                    CheckoutActivity.this.et_address.setError("*Enter your address");
                    CheckoutActivity.this.et_address.requestFocus();
                } else if (CheckoutActivity.this.selectedPaymentMethod.isEmpty()) {
                    Toast.makeText(CheckoutActivity.this, "Choose Payment Method", Toast.LENGTH_SHORT).show();
                } else if (CheckoutActivity.this.name != null && CheckoutActivity.this.name != "" && CheckoutActivity.this.mobile != null && CheckoutActivity.this.mobile != "" && CheckoutActivity.this.address != null && CheckoutActivity.this.address != "" && CheckoutActivity.this.zipcode != null && CheckoutActivity.this.zipcode != "") {
                    if (CheckoutActivity.this.delivery.isEmpty() || CheckoutActivity.this.delivery.equalsIgnoreCase("")) {
                        Toast.makeText(CheckoutActivity.this, "Choose your delivery time", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (CheckoutActivity.this.pincodelist.size() == 0) {
                        CheckoutActivity.this.alertValidZipcode();
                    } else {
                        CheckoutActivity checkoutActivity6 = CheckoutActivity.this;
                        checkoutActivity6.lisy = Integer.parseInt(String.valueOf(checkoutActivity6.pincodelist.indexOf(CheckoutActivity.this.zipcode)));
                        CheckoutActivity checkoutActivity7 = CheckoutActivity.this;
                        checkoutActivity7.thoufivpin = Integer.parseInt(checkoutActivity7.pricelist.get(CheckoutActivity.this.lisy));
                    }
                    PrintStream printStream = System.out;
                    printStream.println("thuto---" + CheckoutActivity.this.thoufivpin);
                    if (CheckoutActivity.this.thoufivpin == 0) {
                        CheckoutActivity.this.alertValidZipcode();
                    } else if (CheckoutActivity.this.orderTotal < ((double) CheckoutActivity.this.thoufivpin)) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(CheckoutActivity.this, R.style.AlertDialogTheme);
                        dialog.setTitle("' " + CheckoutActivity.this.txt_total.getText().toString().trim() + " ' Order Total Warning Alert !");
                        StringBuilder sb = new StringBuilder();
                        sb.append("Sorry for any inconvenience, order total amount should be minimum ₹");
                        sb.append(CheckoutActivity.this.thoufivpin);
                        dialog.setMessage(sb.toString()).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
                    } else if (CheckoutActivity.this.selectedPaymentMethod == "COD") {
                        CheckoutActivity.this.placeOrder();
                    } else if (CheckoutActivity.this.selectedPaymentMethod == "Online") {
                        CheckoutActivity.this.startPayment();
                    }
                }
            }
        });
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
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            return Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        }
        return "Unable to retrieve IP";
    }

    private void setDelivery() {
        final Map<String, String> params = new HashMap<>();
        this.progressDialog.show();
        Volley.newRequestQueue(this).add(new StringRequest(0, ProductConfig.deliverytime, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    CheckoutActivity.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        CheckoutActivity.this.cartModelList = new ArrayList();
                        JSONArray array = jsonResponse.getJSONArray("storeList");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jSONObject = array.getJSONObject(i);
                            CartModel model = new CartModel();
                            model.setDelivery_id(array.getJSONObject(i).getString("delivery_id"));
                            model.setDelivery_time(array.getJSONObject(i).getString("delivery_time"));
                            CheckoutActivity.this.cartModelList.add(model);
                        }
                        for (int j = 0; j < CheckoutActivity.this.cartModelList.size(); j++) {
                            CheckoutActivity.this.delivery_time.add(CheckoutActivity.this.cartModelList.get(j).getDelivery_time());
                        }
                        CheckoutActivity.this.lv_deliveryTime.setAdapter(
                                new ArrayAdapter<>(CheckoutActivity.this, android.R.layout.simple_list_item_1, CheckoutActivity.this.delivery_time)
                        );

                        CheckoutActivity.this.lv_deliveryTime.setExpanded(true);
                        CheckoutActivity.this.lv_deliveryTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                CheckoutActivity.this.delivery = CheckoutActivity.this.lv_deliveryTime.getItemAtPosition(position).toString();
                                Log.e("deliveryTime", CheckoutActivity.this.delivery);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    CheckoutActivity.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                CheckoutActivity.this.progressDialog.dismiss();
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                return params;
            }
        });
    }

    public void pincodde() {
        Dialog dialog2 = new Dialog(this);
        this.dialogg = dialog2;
        dialog2.setCancelable(true);
        this.dialogg.setContentView(R.layout.pincode_options);
        this.dialogg.getWindow().setLayout(-1, -2);
        final GridView rel = (GridView) this.dialogg.findViewById(R.id.rv_slider1);
        final Map<String, String> params = new HashMap<>();
        this.zipcodeModel.setAction("get_pincode");
        Volley.newRequestQueue(this).add(new StringRequest(1, ProductConfig.pincode, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    CheckoutActivity.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        Toast.makeText(CheckoutActivity.this, "No pincode records", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    CheckoutActivity.this.apiZipcodeList = new ArrayList();
                    JSONArray jsonlist = jsonResponse.getJSONArray("storeList");
                    for (int j = 0; j < jsonlist.length(); j++) {
                        JSONObject jsonlistObject = jsonlist.getJSONObject(j);
                        ZipcodeModel zipcodeModel = new ZipcodeModel();
                        zipcodeModel.setId(jsonlistObject.getString("pincode_id").toString());
                        zipcodeModel.setZipcode(jsonlistObject.getString("web_pincode").toString());
                        zipcodeModel.setPrice(jsonlistObject.getString("web_price").toString());
                        zipcodeModel.setPincodeprice(jsonlistObject.getString("web_pincodeprice").toString());
                        CheckoutActivity.this.pincodelist.add(jsonlistObject.getString("web_pincode"));
                        CheckoutActivity.this.pincodepricelist.add(jsonlistObject.getString("web_pincodeprice"));
                        CheckoutActivity.this.pricelist.add(jsonlistObject.getString("web_price"));
                        CheckoutActivity.this.apiZipcodeList.add(zipcodeModel);
                        rel.setAdapter(new PincodeAdapter(CheckoutActivity.this, R.layout.pincode_list, CheckoutActivity.this.apiZipcodeList));
                        rel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                ZipcodeModel zipcodeModel = CheckoutActivity.this.apiZipcodeList.get(i);
                                CheckoutActivity.this.et_zipcode.setText(CheckoutActivity.this.apiZipcodeList.get(i).getZipcode());
                                CheckoutActivity.this.dialogg.dismiss();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    CheckoutActivity.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                CheckoutActivity.this.progressDialog.dismiss();
                if (error instanceof NetworkError) {
                    Toast.makeText(CheckoutActivity.this.getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(CheckoutActivity.this.getApplicationContext(), "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(CheckoutActivity.this.getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(CheckoutActivity.this.getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(CheckoutActivity.this.getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(CheckoutActivity.this.getApplicationContext(), "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                return params;
            }
        });
        this.dialogg.show();
    }

    private void getPincode() {
        final Map<String, String> params = new HashMap<>();
        this.zipcodeModel.setAction("get_pincode");
        Volley.newRequestQueue(this).add(new StringRequest(1, ProductConfig.pincode, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    CheckoutActivity.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        Toast.makeText(CheckoutActivity.this, "No pincode records", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    CheckoutActivity.this.apiZipcodeList = new ArrayList();
                    JSONArray jsonlist = jsonResponse.getJSONArray("storeList");
                    for (int j = 0; j < jsonlist.length(); j++) {
                        JSONObject jsonlistObject = jsonlist.getJSONObject(j);
                        ZipcodeModel zipcodeModel = new ZipcodeModel();
                        zipcodeModel.setId(jsonlistObject.getString("pincode_id").toString());
                        zipcodeModel.setZipcode(jsonlistObject.getString("web_pincode").toString());
                        zipcodeModel.setPrice(jsonlistObject.getString("web_price").toString());
                        zipcodeModel.setPincodeprice(jsonlistObject.getString("web_pincodeprice").toString());
                        CheckoutActivity.this.pincodelist.add(jsonlistObject.getString("web_pincode"));
                        CheckoutActivity.this.pincodepricelist.add(jsonlistObject.getString("web_pincodeprice"));
                        CheckoutActivity.this.pricelist.add(jsonlistObject.getString("web_price"));
                        CheckoutActivity.this.apiZipcodeList.add(zipcodeModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    CheckoutActivity.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                CheckoutActivity.this.progressDialog.dismiss();
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                return params;
            }
        });
    }

    private Boolean checkZipcode(String zipcode2) {
        boolean flag = false;
        for (ZipcodeModel model : this.apiZipcodeList) {
            if (model.getZipcode().equalsIgnoreCase(zipcode2)) {
                flag = true;
            }
        }
        return flag;
    }

    /* access modifiers changed from: private */
    public void alertValidZipcode() {
        String zipcodeModelStr = "";
        for (ZipcodeModel model : this.apiZipcodeList) {
            zipcodeModelStr = zipcodeModelStr + "\n" + model.getZipcode();
        }
        AlertDialog.Builder dialog2 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        dialog2.setTitle("' " + this.zipcode + " ' Pincode Warning Alert !");
        StringBuilder sb = new StringBuilder();
        sb.append("Sorry for inconvenience, We are currently providing services only for below mentioned pincodes. We are yet to touch more areas in the nearest future.");
        sb.append(zipcodeModelStr);
        dialog2.setMessage(sb.toString()).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        }).show();
    }

    /* access modifiers changed from: private */
    public void placeOrder() {
        this.cartModelList = new ArrayList();
        String customer_id2 = BSession.getInstance().getUser_id(this);
        final Map<String, String> params = new HashMap<>();
        String para_str7 = "&datetime=" + this.delivery;
        String para_str8 = "&delivery_date=" + this.et_date.getText().toString().trim();
        this.progressDialog.setMessage("Placing order...");
        this.progressDialog.show();
        String str = para_str8;
        String str2 = para_str7;
        Volley.newRequestQueue(this).add(new StringRequest(0, ProductConfig.placeorder + ("?user_id=" + customer_id2) + ("&user_name=" + this.et_name.getText().toString().trim()) + ("&user_mobile=" + this.et_phone.getText().toString().trim()) + ("&user_address=" + this.et_address.getText().toString().trim()) + ("&payment_mode=" + this.selectedPaymentMethod) + ("&payment_type=" + this.payment_type) + ("&pincode=" + this.et_zipcode.getText().toString()) + para_str7 + para_str8, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    CheckoutActivity.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutActivity.this, R.style.AlertDialogTheme);
                        builder.setMessage(CheckoutActivity.this.getApplicationContext().getResources().getString(R.string.minimum)).setCancelable(false).setPositiveButton(CheckoutActivity.this.getApplicationContext().getResources().getString(R.string.alert_ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                CheckoutActivity.this.startActivity(new Intent(CheckoutActivity.this, MainActivity.class));
                                dialog.cancel();
                            }
                        });
                        builder.create().show();
                        return;
                    }
                    BSession.getInstance().initialize(CheckoutActivity.this, jsonResponse.getString("user_id"), jsonResponse.getString("user_name"), jsonResponse.getString("user_mobile"), jsonResponse.getString("user_address"), "", jsonResponse.getString("refer_id"), "");
                    CheckoutActivity.this.startActivity(new Intent(CheckoutActivity.this, MyordersActivity.class));
                    CheckoutActivity.this.finish();
                    Toast.makeText(CheckoutActivity.this, "Order placed successfully", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    CheckoutActivity.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                CheckoutActivity.this.progressDialog.dismiss();
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                return params;
            }
        });
    }

    private void listeners() {
        this.radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup arg0, int id) {
                boolean isChecked = ((RadioButton) CheckoutActivity.this.radiogroup.findViewById(R.id.cod)).isChecked();
                boolean isChecked1 = ((RadioButton) CheckoutActivity.this.radiogroup.findViewById(R.id.online)).isChecked();
                if (isChecked) {
                    CheckoutActivity.this.selectedPaymentMethod = "COD";
                    CheckoutActivity.this.payment_type = "unpaid";
                } else if (isChecked1) {
                    CheckoutActivity.this.selectedPaymentMethod = "Online";
                    CheckoutActivity.this.payment_type = "paid";
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void getLocation() {
        this.progressDialog.show();
        try {
            LocationManager locationManager2 = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            this.locationManager = locationManager2;
            if (locationManager2 != null) {
                locationManager2.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 8000, 5.0f, this);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


    public void onLocationChanged(Location location) {
        try {
            List<Address> addresses = new Geocoder(this, Locale.getDefault()).getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            EditText editText = this.et_address;
            editText.setText("" + addresses.get(0).getAddressLine(0));
            this.progressDialog.dismiss();
            this.latitude = String.valueOf(location.getLatitude());
            this.longitude = String.valueOf(location.getLongitude());
            this.et_zipcode.setText(addresses.get(0).getPostalCode());
            Log.e("Latitude: ", this.latitude + " & Longitude: " + this.longitude);
        } catch (Exception e) {
        }
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    public void onProviderDisabled(String provider) {
    }

    public void getCartCount() {
        final Map<String, String> params = new HashMap<>();
        this.customer_id = BSession.getInstance().getUser_id(this);
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
                    if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Success")) {
                        TextView textView = CheckoutActivity.this.tv_count;
                        textView.setText(jsonResponse.getString("count") + " Items");
                        TextView textView2 = CheckoutActivity.this.txt_subtotal;
                        textView2.setText(jsonResponse.getString("total") + " ₹ ");
                        TextView textView3 = CheckoutActivity.this.txt_total;
                        textView3.setText(jsonResponse.getString("total") + " ₹ ");
                        CheckoutActivity.this.order_sub = jsonResponse.getString("total");
                        CheckoutActivity.this.orderTotal = Double.parseDouble(jsonResponse.getString("total"));
                        double subTotal = Double.parseDouble(jsonResponse.getString("total"));
                        TextView textView4 = CheckoutActivity.this.txt_subtotal;
                        textView4.setText(subTotal + " ₹ ");
                        CheckoutActivity.this.txt_deliverycharge.setText("0 ₹ ");
                        String billtotal = String.valueOf(Double.parseDouble(jsonResponse.getString("total")));
                        TextView textView5 = CheckoutActivity.this.txt_total;
                        textView5.setText(billtotal + " ₹ ");
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

    private void toolbar() {
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar);
        this.toolbar = toolbar2;
        setSupportActionBar(toolbar2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CheckoutActivity.this.onBackPressed();
            }
        });
        getSupportActionBar().setTitle((CharSequence) "Checkout");
        this.toolbar.setTitleTextColor(-1);
    }

    public void startPayment() {
        Checkout co = new Checkout();
        co.setKeyID("rzp_live_MEuzrVgLVJJgpH");
        try {
            JSONObject options = new JSONObject();
            options.put(AppMeasurementSdk.ConditionalUserProperty.NAME, "Farm Veggies");
            options.put("description", "Happy Shopping");
            options.put(FirebaseAnalytics.Param.CURRENCY, "INR");
            options.put("amount", this.orderTotal * 100.0d);
            JSONObject preFill = new JSONObject();
            preFill.put("email", this.et_name.getText().toString());
            preFill.put("contact", this.et_phone.getText().toString());
            options.put("prefill", preFill);
            co.open(this, options);
        } catch (Exception e) {
            Toast.makeText(this, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, CheckoutActivity.class));
            e.printStackTrace();
        }
    }

    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            this.progressDialog.show();
            this.payment_type = "Paid";
            placeOrder();
            this.progressDialog.show();
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    public void onPaymentError(int code, String response) {
        try {
            this.payment_type = "Unpaid";
            Toast.makeText(this, "Payment failed: " + code + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + response, Toast.LENGTH_SHORT).show();
            onRestart();
            finish();
            overridePendingTransition(0, 0);
            startActivity(new Intent(this, CheckoutActivity.class));
            overridePendingTransition(0, 0);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                finish();  // Close the activity
//            }
//        });
//    }



}
