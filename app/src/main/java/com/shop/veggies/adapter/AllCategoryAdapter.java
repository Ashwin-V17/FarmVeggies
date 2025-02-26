package com.shop.veggies.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.shop.veggies.R;
import com.shop.veggies.model.CategoryModel;

import java.util.List;

public class AllCategoryAdapter extends ArrayAdapter<CategoryModel> {
        private Context context;
        private int layoutResourceId;
        private List<CategoryModel> categoryModelList;


        public AllCategoryAdapter(Context context, int layoutResourceId, List<CategoryModel> categoryModelList) {
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
                holder.tv_catTitle = (TextView) row.findViewById(R.id.title);
                holder.category_image = row.findViewById(R.id.ivMenu);
                holder.ll_parent = row.findViewById(R.id.cv_card);

                row.setTag(holder);
            } else {
                holder = (RecordHolder) row.getTag();
            }
            CategoryModel item = categoryModelList.get(position);
            holder.tv_catTitle.setText(item.getCategory_name());
            holder.tv_catTitle.setText(Html.fromHtml(String.valueOf(Html.fromHtml(item.getCategory_name()))));
            //  holder.category_image.setImageResource(item.getCimage());

            String img = item.getCategory_image();
            Glide.with(context)
                    .load(img)
                    .placeholder(R.drawable.dummycat)
                    .into(holder.category_image);

            return row;
        }

        static class RecordHolder {
             CircularImageView category_image;
            CardView ll_parent;
            TextView tv_catTitle;
        }
    }
