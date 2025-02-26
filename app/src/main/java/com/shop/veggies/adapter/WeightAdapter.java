package com.shop.veggies.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shop.veggies.R;
import com.shop.veggies.model.WeightModel;

import java.util.List;

public class WeightAdapter extends RecyclerView.Adapter<WeightAdapter.Holder> {
    public Context context;
    private List<WeightModel> papularModelList;
    int selectedPosition = 0;

    public static class Holder extends RecyclerView.ViewHolder {
        TextView txtTitle;

        private Holder(View itemView) {
            super(itemView);
            this.txtTitle = (TextView) itemView.findViewById(R.id.tv_wgt);
        }
    }

    public WeightAdapter(Context context2, List<WeightModel> papularModelList2) {
        this.context = context2;
        this.papularModelList = papularModelList2;
    }

    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return getViewHolder(viewGroup, LayoutInflater.from(viewGroup.getContext()));
    }

    private Holder getViewHolder(ViewGroup viewGroup, LayoutInflater inflater) {
        return new Holder(inflater.inflate(R.layout.layout_weight_list, viewGroup, false));
    }

    public void onBindViewHolder(final Holder holder, int position) {
        holder.txtTitle.setText(this.papularModelList.get(position).getWeb_title());
        if (this.selectedPosition == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#e6e6e6"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int currentPosition = holder.getLayoutPosition();
                if (WeightAdapter.this.selectedPosition != currentPosition) {
                    int lastSelectedPosition = WeightAdapter.this.selectedPosition;
                    WeightAdapter.this.selectedPosition = currentPosition;
                    WeightAdapter.this.notifyItemChanged(lastSelectedPosition);
                    holder.itemView.setBackgroundColor(Color.parseColor("#e6e6e6"));
                }
            }
        });
    }

    public int getItemCount() {
        List<WeightModel> list = this.papularModelList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }
}
