package com.shop.veggies.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.shop.veggies.R;
import com.shop.veggies.activity.SubCategoryActivity;
import com.shop.veggies.model.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holder> {
    public Context context;
    /* access modifiers changed from: private */
    public List<CategoryModel> papularModelList;

    public static class Holder extends RecyclerView.ViewHolder {
        ImageView img;
        LinearLayout llParent;
        TextView tv_catTitle;

        private Holder(View itemView) {
            super(itemView);
            this.llParent = (LinearLayout) itemView.findViewById(R.id.ll_parent);
            this.img = (ImageView) itemView.findViewById(R.id.iv_catImage);
            this.tv_catTitle = (TextView) itemView.findViewById(R.id.tv_catTitle);
        }
    }

    public CategoryAdapter(Context context2, List<CategoryModel> papularModelList2) {
        this.context = context2;
        this.papularModelList = papularModelList2;
    }

    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return getViewHolder(viewGroup, LayoutInflater.from(viewGroup.getContext()));
    }

    private Holder getViewHolder(ViewGroup viewGroup, LayoutInflater inflater) {
        return new Holder(inflater.inflate(R.layout.layout_category_list, viewGroup, false));
    }

    public void onBindViewHolder(Holder holder, @SuppressLint("RecyclerView") final int position) {
        holder.tv_catTitle.setText(Html.fromHtml(String.valueOf(Html.fromHtml(this.papularModelList.get(position).getCategory_name()))));
        ((RequestBuilder) Glide.with(this.context).load(this.papularModelList.get(position).getCategory_image()).placeholder((int)R.drawable.dummy)).into(holder.img);
        holder.llParent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CategoryAdapter.this.context, SubCategoryActivity.class);
                SubCategoryActivity.subcourcemodel = (CategoryModel) CategoryAdapter.this.papularModelList.get(position);
                CategoryAdapter.this.context.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        List<CategoryModel> list = this.papularModelList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }
}
