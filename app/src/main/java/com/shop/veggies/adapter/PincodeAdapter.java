package com.shop.veggies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shop.veggies.R;
import com.shop.veggies.model.ZipcodeModel;

import java.util.List;

public class PincodeAdapter extends ArrayAdapter<ZipcodeModel> {
    private Context context;
    private int layoutResourceId;
    private List<ZipcodeModel> categoryModelList;


    public PincodeAdapter(Context context, int layoutResourceId, List<ZipcodeModel> categoryModelList) {
        super(context, layoutResourceId, categoryModelList);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.categoryModelList = categoryModelList;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new RecordHolder();
            holder.tv_catTitle = (TextView) row.findViewById(R.id.pincode);

            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }
        ZipcodeModel item = categoryModelList.get(position);
        holder.tv_catTitle.setText(item.getZipcode());


        return row;
    }

    static class RecordHolder {
        ImageView category_image;
        LinearLayout ll_parent;
        TextView tv_catTitle;
    }
}