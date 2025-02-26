package com.shop.veggies.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.internal.view.SupportMenu;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.RecyclerView;

import com.shop.veggies.R;
import com.shop.veggies.activity.OrderDetailActivity;
import com.shop.veggies.model.MyordersModel;

import java.util.List;

public class MyordersListAdapter extends RecyclerView.Adapter<MyordersListAdapter.MailViewHolder> {
    MyordersModel hotelModel = new MyordersModel();
    List<MyordersModel> hotelModelList;
    /* access modifiers changed from: private */
    public Context mContext;

    public MyordersListAdapter(Context mContext2, List<MyordersModel> hotelModelList2) {
        this.mContext = mContext2;
        this.hotelModelList = hotelModelList2;
    }

    public MailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_orders_list, parent, false));
    }

    public void onBindViewHolder(final MailViewHolder holder, int position) {
        MyordersModel model = this.hotelModelList.get(position);
        holder.delivery_date = model.getDelivery_date();
        holder.orderid.setText(model.getOrder_id());
        TextView textView = holder.tv_total;
        textView.setText("₹ " + model.getOrder_total());
        TextView textView2 = holder.orderdate;
        textView2.setText("Order placed on " + model.getOrder_date());
        holder.ll_parent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MyordersListAdapter.this.mContext, OrderDetailActivity.class);
                intent.putExtra("order_id", holder.orderid.getText().toString());
                intent.putExtra("delivery_date", holder.delivery_date);
                intent.putExtra("order_date", holder.orderdate.getText().toString());
                MyordersListAdapter.this.mContext.startActivity(intent);
            }
        });
        if (model.getOrder_status().equalsIgnoreCase("1")) {
            holder.cancel_tx.setVisibility(8);
        } else if (model.getOrder_status().equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_2D)) {
            holder.cancel_tx.setVisibility(0);
            holder.cancel_tx.setText("Order Complete");
            holder.cancel_tx.setTextColor(this.mContext.getResources().getColor(R.color.colorGreen));
        } else if (model.getOrder_status().equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_3D)) {
            holder.cancel_tx.setVisibility(0);
            holder.cancel_tx.setText("Order Cancelled");
            holder.cancel_tx.setTextColor(SupportMenu.CATEGORY_MASK);
        }
    }

    public int getItemCount() {
        return this.hotelModelList.size();
    }

    public class MailViewHolder extends RecyclerView.ViewHolder {
        TextView cancel_tx;
        String delivery_date;
        LinearLayout ll_parent;
        TextView orderdate;
        TextView orderid;
        TextView tv_total;

        public MailViewHolder(View itemView) {
            super(itemView);
            this.orderid = (TextView) itemView.findViewById(R.id.orderid);
            this.tv_total = (TextView) itemView.findViewById(R.id.tv_total);
            this.orderdate = (TextView) itemView.findViewById(R.id.orderdate);
            this.ll_parent = (LinearLayout) itemView.findViewById(R.id.ll_parent);
            this.cancel_tx = (TextView) itemView.findViewById(R.id.cancel_tx);
        }
    }
}
