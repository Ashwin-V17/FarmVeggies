package com.shop.veggies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.shop.veggies.R;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<Integer> images;
    private LayoutInflater inflater;

    public BannerAdapter(Context context2, ArrayList<Integer> images2) {
        this.context = context2;
        this.images = images2;
        this.inflater = LayoutInflater.from(context2);
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public int getCount() {
        return this.images.size();
    }

    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = this.inflater.inflate(R.layout.slide, view, false);
        ((ImageView) myImageLayout.findViewById(R.id.image)).setImageResource(this.images.get(position).intValue());
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
