package com.shop.veggies.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.IntentSenderRequest;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.shop.veggies.R;
import com.shop.veggies.adapter.PincodeAdapter;
import com.shop.veggies.fragment.HomeFragment;
import com.shop.veggies.model.ZipcodeModel;
import com.shop.veggies.utils.BSession;
import com.shop.veggies.utils.ProductConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import com.google.android.play.core.install.model.InstallStatus;

import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationBarView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.gms.tasks.*;
//import com.google.android.play.appupdate.UpdateAvailability;

import android.app.Activity;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import com.google.android.play.appupdate.*;
import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.play.appupdate.AppUpdateManager;
//import com.google.android.play.appupdate.AppUpdateManagerFactory;
//import com.google.android.play.appupdate.AppUpdateInfo;
//import com.google.android.play.appupdate.AppUpdateType;
//import com.google.android.play.appupdate.UpdateAvailability;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.model.InstallStatus;




public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int FLEXIBLE_APP_UPDATE_REQ_CODE = 123;
    private static final int RC_APP_UPDATE = 100;
    public static Dialog dialog;
    public static ZipcodeModel subcourcemodel = new ZipcodeModel();
    List<ZipcodeModel> apiZipcodeList = new ArrayList();
    TextView badge_notification;
    String baseUrl;
    MenuItem bottom_category;
    TextView butom_notitext;
    CircleImageView capture_img;
    CardView cart1;
    CardView cart2;
    private AppUpdateManager mAppUpdateManager;

    MenuItem category;
    LinearLayout closed_ll;
    String currentVersion;
    String customer_id = null;
    String deviceid;
    Dialog dialogg;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FrameLayout frame_container;
    MenuItem help;
    ImageView imagee;
    String img;
    private final InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {

        private final ActivityResultLauncher<IntentSenderRequest> appUpdateLauncher =
                registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(),
                        result -> {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                Log.d("AppUpdate", "Update flow completed successfully.");
                            } else {
                                Log.d("AppUpdate", "Update flow failed or canceled.");
                            }
                        });
        public void onStateUpdate(InstallState state) {
            if (state.installStatus() == InstallStatus.INSTALLED) {
                Toast.makeText(MainActivity.this, "Update Installed!", Toast.LENGTH_SHORT).show();
                showCompletedUpdate();
            }
        }

    };



    String ipaddress;
    int lang_selected;
    String latestVersion;
    LinearLayout location;
    TextView location_text;
    TextView location_text1;
    /* access modifiers changed from: private */

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment) // Ensure `fragment_container` exists in layout
                .commit();
    }

    final int HOME = R.id.navigation_home;

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_cart1) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                return true;
            } else if (itemId == R.id.navigation_category) {
                startActivity(new Intent(MainActivity.this, CategoryListActivity.class));
                return true;
            } else if (itemId == R.id.navigation_home) {
                loadFragment(new HomeFragment());
                return true;
            } else if (itemId == R.id.navigation_mega) {
                startActivity(new Intent(MainActivity.this, MyordersActivity.class));
                return true;
            } else if (itemId == R.id.navigation_user) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                return true;
            }
            return false;
        });
        // Set default selection (Home)
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }
    LinearLayout main;
    BottomNavigationView navigation;
    String opinn;
    String para_str;
    String pin = "pin";
    ArrayList<String> pincodelist = new ArrayList<>();
    ArrayList<String> pincodepricelist = new ArrayList<>();
    String price;
    ArrayList<String> pricelist = new ArrayList<>();
    MenuItem profile;
    ProgressDialog progressDialog;
    MenuItem refer;
    LinearLayout search;
    TextView tv_call;
    TextView tv_name;
    RelativeLayout wallet_rel;
    TextView walletamount;
    String zip;
    ZipcodeModel zipcodeModel = new ZipcodeModel();

    private void installStateUpdatedListener(InstallState installState) {
        if (installState.installStatus() == InstallStatus.DOWNLOADED) {
            // Restart the app to complete the update
            mAppUpdateManager.completeUpdate();
        }
    }
    private ActivityResultLauncher<IntentSenderRequest> appUpdateLauncher;
    /* access modifiers changed from: protected */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Progress Dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.....");

        // Get device ID
        deviceid = Settings.Secure.getString(getContentResolver(), "android_id");
        Log.d("deviceid", deviceid);

        // Initialize App Update Manager
        mAppUpdateManager = AppUpdateManagerFactory.create(this);

        // Register Activity Result Launcher for Update Flow
        appUpdateLauncher = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(),
                result -> {
                    if (result.getResultCode() != Activity.RESULT_OK) {
                        Log.e("AppUpdate", "Update flow failed!");
                    }
                });

        // Check for App Updates
        checkForAppUpdate();

        // Set up Bottom Navigation
        setupBottomNavigation();

        // Load Other Functions
        all();
        walletamount();
    }
    // 🔹 Function to Check for App Updates
    private void checkForAppUpdate() {
        Task<AppUpdateInfo> appUpdateInfoTask = mAppUpdateManager.getAppUpdateInfo();

//        appUpdateInfoTask.addOnSuccessListener(this::onSuccess2);
    }
    public void all() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show()
        );

        location_text = findViewById(R.id.location_text);
        main = findViewById(R.id.main);
        cart1 = findViewById(R.id.cart1);
        cart2 = findViewById(R.id.cart2);
        frame_container = findViewById(R.id.frame_container);
        closed_ll = findViewById(R.id.closed_ll);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Menu menu = navigationView.getMenu();
        String customer = BSession.getInstance().getUser_name(getApplicationContext());

        profile = menu.findItem(R.id.nav_profile);
        refer = menu.findItem(R.id.nav_refer);
        help = menu.findItem(R.id.nav_help);
        category = menu.findItem(R.id.nav_category);

        if (customer.isEmpty()) {
            profile.setVisible(false);
            refer.setVisible(false);
            help.setVisible(false);
        } else {
            profile.setVisible(true);
            refer.setVisible(true);
            help.setVisible(true);
        }

        navigationView.setNavigationItemSelectedListener(this);
//home
        View hView = navigationView.getHeaderView(0);
        capture_img = hView.findViewById(R.id.iv_profile);
        tv_name = hView.findViewById(R.id.tv_name);
        tv_call = hView.findViewById(R.id.tv_call);
        wallet_rel = hView.findViewById(R.id.wallet_rel);
        walletamount = hView.findViewById(R.id.wallet_amount);

        String img2 = BSession.getInstance().getProfileimage(this);
        String customer_name = BSession.getInstance().getUser_name(this);

        if (img2 != null && !img2.isEmpty()) {
            Glide.with(this)
                    .load(img2)
                    .placeholder(R.drawable.pimage)
                    .into(capture_img);
        }

        tv_name.setText(customer_name);

        tv_call.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:7299232425"));
            startActivity(intent);
        });

        // Setup Bottom Navigation
        navigation = findViewById(R.id.navigation);
        navigation.setOnItemSelectedListener(this::onNavigationItemSelected);

        // Load Home Fragment by Default
        loadFragment(new HomeFragment());

        // Add Badge to Cart Icon Using BadgeDrawable (Safe API)
        addBadgeToCart();

        // Location Click Listener
        location = findViewById(R.id.location);
        location_text1 = findViewById(R.id.location_text1);
        location.setOnClickListener(v -> pincodde());

        // Search Click Listener
        search = findViewById(R.id.search);
        search.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SearchActivity.class)));
    }
    private void addBadgeToCart() {
        BadgeDrawable badge = navigation.getOrCreateBadge(R.id.navigation_cart1);
        badge.setVisible(true);
        badge.setNumber(5); // Example count
    }

//    onNavigationItemSelected
    public void onStop() {
        AppUpdateManager appUpdateManager = this.mAppUpdateManager;
        if (appUpdateManager != null) {
            appUpdateManager.registerListener(this.installStateUpdatedListener);
        }
        super.onStop();
    }

    /* access modifiers changed from: private */
    public void showCompletedUpdate() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), (CharSequence) "New App is Ready", -2);
        snackbar.setAction((CharSequence) "Install", (View.OnClickListener) new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "UPDATESTATUS1", Toast.LENGTH_SHORT).show();
                mAppUpdateManager.completeUpdate();
            }
        });
        snackbar.show();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 100 && requestCode != -1) {
            Toast.makeText(this, "CANCEL", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_cart1);
        View actionView = MenuItemCompat.getActionView(menuItem);
        this.badge_notification = (TextView) actionView.findViewById(R.id.badge_notification);
        getCartCount();
        loadleave();
        actionView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.onOptionsItemSelected(menuItem);
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

    private void walletamount() {
        String userid = BSession.getInstance().getUser_id(getApplicationContext());
        final Map<String, String> params = new HashMap<>();
        this.progressDialog.show();
        System.out.println("user_id" + userid);
        StringRequest jsObjRequest = new StringRequest(1, ProductConfig.wallet_amount + ("?user_id=" + userid), new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) || !jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                        MainActivity.this.progressDialog.dismiss();
                        MainActivity.this.wallet_rel.setVisibility(View.VISIBLE);
                        return;
                    }
                    MainActivity.this.progressDialog.dismiss();
                    MainActivity.this.wallet_rel.setVisibility(View.VISIBLE);
                    MainActivity.this.walletamount.setText(jsonResponse.getString("available_wallete_amount"));
                    MainActivity.this.wallet_rel.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            MainActivity.this.startActivity(new Intent(MainActivity.this, WalletHistoryActivity.class));
                        }
                    });
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
        Volley.newRequestQueue(this).add(jsObjRequest);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(3000, 1, 1.0f));
    }

    private void loadleave() {
        final Map<String, String> params = new HashMap<>();
        this.progressDialog.show();
        StringRequest jsObjRequest = new StringRequest(0, ProductConfig.appstatus, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.has(FirebaseAnalytics.Param.SUCCESS) && jsonResponse.getString(FirebaseAnalytics.Param.SUCCESS).equals("1")) {
                        MainActivity.this.progressDialog.dismiss();
                        if (jsonResponse.getString("app_status").equalsIgnoreCase("Enable")) {
                            MainActivity.this.cart1.setVisibility(View.VISIBLE);
                            MainActivity.this.cart2.setVisibility(View.VISIBLE);
                            MainActivity.this.category.setVisible(true);
                            MainActivity.this.navigation.getMenu().findItem(R.id.navigation_category).setVisible(true);
                            MainActivity.this.butom_notitext.setVisibility(View.VISIBLE);
                            MainActivity.this.badge_notification.setVisibility(View.VISIBLE);
                            return;
                        }
                        MainActivity.this.cart1.setVisibility(View.GONE);
                        MainActivity.this.cart2.setVisibility(View.GONE);
                        MainActivity.this.category.setVisible(false);
                        MainActivity.this.navigation.getMenu().findItem(R.id.navigation_category).setVisible(false);
                        MainActivity.this.butom_notitext.setVisibility(View.GONE);
                        MainActivity.this.badge_notification.setVisibility(View.GONE);
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
        Volley.newRequestQueue(this).add(jsObjRequest);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
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
                        MainActivity.this.butom_notitext.setText("0");
                        MainActivity.this.badge_notification.setText("0");
                        return;
                    }
                    MainActivity.this.badge_notification.setText(jsonResponse.getString("count"));
                    MainActivity.this.butom_notitext.setText(jsonResponse.getString("count"));
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


    public void pincodde() {
        Dialog dialog2 = new Dialog(this);
        this.dialogg = dialog2;
        dialog2.setCancelable(true);
        this.dialogg.setContentView(R.layout.pincode_options);
        this.dialogg.getWindow().setLayout(-1, -2);
        final GridView rel = (GridView) this.dialogg.findViewById(R.id.rv_slider1);
        final Map<String, String> params = new HashMap<>();
        this.zipcodeModel.setAction("get_pincode");
        Request<String> add = Volley.newRequestQueue(this).add(new StringRequest(1, ProductConfig.pincode, new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("Response", response.toString());
                try {
                    MainActivity.this.progressDialog.dismiss();
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                        Toast.makeText(MainActivity.this, "No pincode records", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    MainActivity.this.apiZipcodeList = new ArrayList();
                    JSONArray jsonlist = jsonResponse.getJSONArray("storeList");
                    for (int j = 0; j < jsonlist.length(); j++) {
                        JSONObject jsonlistObject = jsonlist.getJSONObject(j);
                        ZipcodeModel zipcodeModel = new ZipcodeModel();
                        zipcodeModel.setId(jsonlistObject.getString("pincode_id").toString());
                        zipcodeModel.setZipcode(jsonlistObject.getString("web_pincode").toString());
                        zipcodeModel.setPrice(jsonlistObject.getString("web_price").toString());
                        zipcodeModel.setPincodeprice(jsonlistObject.getString("web_pincodeprice").toString());
                        MainActivity.this.pincodelist.add(jsonlistObject.getString("web_pincode"));
                        MainActivity.this.pincodepricelist.add(jsonlistObject.getString("web_pincodeprice"));
                        MainActivity.this.pricelist.add(jsonlistObject.getString("web_price"));
                        MainActivity.this.apiZipcodeList.add(zipcodeModel);
                        rel.setAdapter(new PincodeAdapter(MainActivity.this, R.layout.pincode_list, MainActivity.this.apiZipcodeList));
                        rel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                ZipcodeModel zipcodeModel = MainActivity.this.apiZipcodeList.get(i);
                                String zip = MainActivity.this.apiZipcodeList.get(i).getZipcode();
                                MainActivity.this.price = MainActivity.this.apiZipcodeList.get(i).getPrice();
                                MainActivity.this.location_text.setText(zip);
                                MainActivity.this.dialogg.dismiss();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    MainActivity.this.progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                MainActivity.this.progressDialog.dismiss();
                if (error instanceof NetworkError) {
                    Toast.makeText(MainActivity.this.getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(MainActivity.this.getApplicationContext(), "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(MainActivity.this.getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(MainActivity.this.getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(MainActivity.this.getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(MainActivity.this.getApplicationContext(), "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
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

    public void onBackPressed() {
        super.onBackPressed();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen((int) GravityCompat.START)) {
            drawer.closeDrawer((int) GravityCompat.START);
            return;
        }
        AlertDialog.Builder BackAlertDialog = new AlertDialog.Builder(this);
        BackAlertDialog.setTitle((CharSequence) "Activity Exit Alert");
        BackAlertDialog.setMessage((CharSequence) "Are you sure want to exit ?");
        BackAlertDialog.setIcon((int) R.drawable.new_logo);
        BackAlertDialog.setPositiveButton((CharSequence) "NO", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        BackAlertDialog.setNegativeButton((CharSequence) "YES", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.moveTaskToBack(true);
                MainActivity.this.finish();
            }
        });
        BackAlertDialog.show();
    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_category) {
            startActivity(new Intent(getApplicationContext(), CategoryListActivity.class));
        } else if (id == R.id.nav_whatsup) {
            try {
                Intent msg = new Intent("android.intent.action.VIEW");
                msg.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + "+91 7299232425" + "&text=" + "Your Message to Contact Number"));
                startActivity(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.nav_refer) {
            startActivity(new Intent(this, ReferEarnActivity.class));
        } else if (id == R.id.nav_orders) {
            startActivity(new Intent(this, MyordersActivity.class));
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
        } else if (id == R.id.nav_help) {
            startActivity(new Intent(this, HelpSupportActivity.class));
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutausActivity.class));
        } else if (id == R.id.nav_offers) {
            startActivity(new Intent(this, OfferActivity.class));
        } else if (id != R.id.nav_store) {
            if (id == R.id.nav_logout) {
                logoutAlert();
            } else if (id == R.id.nav_helpreturn) {
                startActivity(new Intent(this, ShippingandRetrun.class));
            }
        }
        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer((int) GravityCompat.START);
        return true;
    }

    private void logoutAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setMessage(getApplicationContext().getResources().getString(R.string.alert_want_logout)).setCancelable(false).setPositiveButton(getApplicationContext().getResources().getString(R.string.alert_yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                MainActivity.this.logout();
            }
        }).setNegativeButton(getApplicationContext().getResources().getString(R.string.alert_no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    /* access modifiers changed from: private */
    public void logout() {
        BSession.getInstance().destroy(this);
        Toast.makeText(this, getResources().getString(R.string.logout_success), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void onRestart() {
        super.onRestart();
        startActivity(getIntent());
    }

    private void getCurrentVersion() {
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        this.currentVersion = pInfo.versionName;
        new GetLatestVersion().execute(new String[0]);
    }

    public InstallStateUpdatedListener getInstallStateUpdatedListener() {
        return this.installStateUpdatedListener;
    }

/*    private void onSuccess(AppUpdateInfo appUpdateInfo) {
        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
            try {
                IntentSenderRequest intentSenderRequest = new IntentSenderRequest.Builder(
                        appUpdateInfo.requestUpdateIntentSender(AppUpdateType.IMMEDIATE, this)).build();

                // Launch the update
                appUpdateLauncher.launch(intentSenderRequest);
                ActivityResultLauncher<IntentSenderRequest> appUpdateLauncher = null;
                appUpdateLauncher.launch(intentSenderRequest);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }
    }*/

//    private void onSuccess2(AppUpdateInfo appUpdateInfo) {
//        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
//                appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
//            try {
//                IntentSenderRequest intentSenderRequest = new IntentSenderRequest.Builder(
//                        appUpdateInfo.requestUpdateIntentSender(AppUpdateType.IMMEDIATE, this)).build();
//                // Launch the update
//                appUpdateLauncher.launch(intentSenderRequest);
//            } catch (IntentSender.SendIntentException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private class GetLatestVersion extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog progressDialog;

        private GetLatestVersion() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* access modifiers changed from: protected */
        public JSONObject doInBackground(String... params) {
            try {
                Document doc = Jsoup.connect("https://play.google.com/store/apps/details?id=com.shop.veggies&hl=en").get();
                MainActivity.this.latestVersion = ((Element) doc.getElementsByClass("htlgb").get(6)).text();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new JSONObject();
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(JSONObject jsonObject) {
            if (MainActivity.this.latestVersion != null && !MainActivity.this.currentVersion.equalsIgnoreCase(MainActivity.this.latestVersion) && !MainActivity.this.isFinishing()) {
                MainActivity.this.showUpdateDialog();
            }
            super.onPostExecute(jsonObject);
        }
    }

    /* access modifiers changed from: private */
    public void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle((CharSequence) "A New Update is Available");
        builder.setIcon((int) R.drawable.new_logo);
        builder.setPositiveButton((CharSequence) "Update", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.shop.veggies")));
                dialog.dismiss();
            }
        });
        builder.setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        dialog = builder.show();
    }
}
