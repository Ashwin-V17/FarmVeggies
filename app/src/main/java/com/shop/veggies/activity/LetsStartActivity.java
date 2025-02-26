package com.shop.veggies.activity;

import static com.shop.veggies.R.id.indicator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.shop.veggies.R;
import com.shop.veggies.model.BannerModel;
import com.shop.veggies.utils.ProductConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import me.relex.circleindicator.CircleIndicator3;

public class LetsStartActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private CircleIndicator3 indicator;
    private Button start;
    private List<BannerModel> apiSliderList = new ArrayList<>();
    private SliderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lets_start);

        viewPager = findViewById(R.id.viewPager);
        indicator = findViewById(R.id.indicator_viewPager2);
        start = findViewById(R.id.start_button);

        start.setOnClickListener(v -> startActivity(new Intent(LetsStartActivity.this, MainActivity.class)));

        loadSlider();
    }

    private void loadSlider() {
        Volley.newRequestQueue(this).add(new StringRequest(0, ProductConfig.startbanner,
                response -> {
                    Log.e("Response", response);
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        if (!jsonResponse.has("result") || !jsonResponse.getString("result").equals("Success")) {
                            Toast.makeText(LetsStartActivity.this, "No slider result found", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONArray jsonResarray = jsonResponse.getJSONArray("storeList");
                        for (int i = 0; i < jsonResarray.length(); i++) {
                            JSONObject jsonObject = jsonResarray.getJSONObject(i);
                            BannerModel model = new BannerModel();
                            model.setBanner_title(jsonObject.getString("web_title"));
                            model.setBanner_image(jsonObject.getString("startbanner_image"));
                            apiSliderList.add(model);
                        }
                        setUpSlider();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("Error", error.toString())
        ));
    }

    private void setUpSlider() {
        adapter = new SliderAdapter(this, apiSliderList);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
    }
}
