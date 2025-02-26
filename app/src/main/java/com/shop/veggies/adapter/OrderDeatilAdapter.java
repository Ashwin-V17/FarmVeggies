package com.shop.veggies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.shop.veggies.R;
import com.shop.veggies.model.MyordersModel;

import java.util.List;

public class OrderDeatilAdapter extends RecyclerView.Adapter<OrderDeatilAdapter.Holder> {
    private CategoryAdapterCallback callback;
    private List<MyordersModel> categoryModelList;
    private Context context;

    public interface CategoryAdapterCallback {
        void categoryItem(int i, MyordersModel myordersModel);
    }

    public OrderDeatilAdapter(Context context2, List<MyordersModel> categoryModelList2) {
        this.context = context2;
        this.categoryModelList = categoryModelList2;
    }

    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return getViewHolder(viewGroup, LayoutInflater.from(viewGroup.getContext()));
    }

    private Holder getViewHolder(ViewGroup viewGroup, LayoutInflater inflater) {
        return new Holder(inflater.inflate(R.layout.layout_myorder_detail_list, viewGroup, false));
    }

    public void onBindViewHolder(Holder holder, int position) {
        MyordersModel model = this.categoryModelList.get(position);
        holder.tv_pTitle.setText(model.getProduct_name());
        TextView textView = holder.tv_pqyt;
        textView.setText("Quantity " + model.getProduct_quantity());
        holder.tv_pwgt.setText(model.getProduct_weight());
        TextView textView2 = holder.tv_pPrice;
        textView2.setText("Price ₹ " + model.getProduct_price());
        ((RequestBuilder) Glide.with(this.context).load(model.getProduct_image()).placeholder((int) R.drawable.dummy)).into(holder.ivMenu);
    }

    public int getItemCount() {
        List<MyordersModel> list = this.categoryModelList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void setCallback(CategoryAdapterCallback callback2) {
        this.callback = callback2;
    }

    public static class Holder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView ivMenu;
        public TextView tv_pPrice;
        public TextView tv_pTitle;
        public TextView tv_pqyt;
        public TextView tv_pwgt;

        private Holder(View itemView) {
            super(itemView);
            this.tv_pTitle = (TextView) itemView.findViewById(R.id.tv_pTitle);
            this.tv_pqyt = (TextView) itemView.findViewById(R.id.tv_pqyt);
            this.tv_pwgt = (TextView) itemView.findViewById(R.id.tv_pwgt);
            this.tv_pPrice = (TextView) itemView.findViewById(R.id.tv_pPrice);
            this.ivMenu = (ImageView) itemView.findViewById(R.id.ivMenu);
        }
    }
}
