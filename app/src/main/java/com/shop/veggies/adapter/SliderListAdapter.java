package com.shop.veggies.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.shop.veggies.R;
import com.shop.veggies.activity.ComboOfferActivity;
import com.shop.veggies.model.SliderListModel;

import java.util.List;

public class SliderListAdapter extends RecyclerView.Adapter<SliderListAdapter.Holder> {
    public Context context;
    /* access modifiers changed from: private */
    public List<SliderListModel> papularModelList;
    private Holder holder;

    public static class Holder extends RecyclerView.ViewHolder {
        ImageView img;
        CardView llParent;

        private Holder(View itemView) {
            super(itemView);
            this.llParent = (CardView) itemView.findViewById(R.id.llParent);
            this.img = (ImageView) itemView.findViewById(R.id.civMenu);
        }
    }

    public SliderListAdapter(Context context2, List<SliderListModel> papularModelList2) {
        this.context = context2;
        this.papularModelList = papularModelList2;
    }

    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return getViewHolder(viewGroup, LayoutInflater.from(viewGroup.getContext()));
    }

    private Holder getViewHolder(ViewGroup viewGroup, LayoutInflater inflater) {
        return new Holder(inflater.inflate(R.layout.layout_home_slider1, viewGroup, false));
    }

    public void onBindViewHolder(Holder holder, @SuppressLint("RecyclerView") final int position) {
        this.holder = holder;
        SliderListModel sliderListModel = this.papularModelList.get(position);
        ((RequestBuilder) Glide.with(this.context).load(this.papularModelList.get(position).getSl_image()).placeholder((int) R.drawable.dummy)).into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SliderListAdapter.this.context, ComboOfferActivity.class);
                intent.putExtra("page", "slider");
                intent.putExtra("id", ((SliderListModel) SliderListAdapter.this.papularModelList.get(position)).getSl_id());
                SliderListAdapter.this.context.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        List<SliderListModel> list = this.papularModelList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }
}
