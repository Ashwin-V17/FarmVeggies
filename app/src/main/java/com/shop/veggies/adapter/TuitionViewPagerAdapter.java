package com.shop.veggies.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.shop.veggies.fragment.TutionMyclassDynamicFragment;

import java.util.ArrayList;
import java.util.List;

public class TuitionViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList = new ArrayList();
    private List<String> mFragmentTitleList = new ArrayList();
    private ArrayList<String> mFragmentscidList = new ArrayList<>();
    String scid = "";
    private String[] title = {"Atta & Other Flours", " Dal & Pulses", " Dry Fruits & Nuts", "Edible Oils", " Ghee &Vanaspati", "Milk Products", "Pets", "Other Grains", "Salt,Sugar &Jaggery", "Spices & Ready Masala"};

    public TuitionViewPagerAdapter(FragmentManager manager, List<Fragment> mFragmentList2, List<String> mFragmentTitleList2, ArrayList<String> mFragmentscidList2) {
        super(manager);
        this.mFragmentList = mFragmentList2;
        this.mFragmentTitleList = mFragmentTitleList2;
        this.mFragmentscidList = mFragmentscidList2;
    }

    public Fragment getItem(int position) {
        this.mFragmentList.get(position);
        this.mFragmentscidList.get(position);
        String str = this.mFragmentscidList.get(position);
        this.scid = str;
        return TutionMyclassDynamicFragment.getInstance(position, str);
    }

    public int getCount() {
        return this.mFragmentTitleList.size();
    }

    public CharSequence getPageTitle(int position) {
        return this.mFragmentTitleList.get(position);
    }
}
