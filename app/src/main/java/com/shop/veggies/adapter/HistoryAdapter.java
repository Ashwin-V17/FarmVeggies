package com.shop.veggies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shop.veggies.R;
import com.shop.veggies.model.HistoryModel;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MailViewHolder> {
    String deviceid;
    List<HistoryModel> historymodelist;
    private Context mContext;
    String product_id = "";
    String qty;
    String scid;

    public HistoryAdapter(Context mContext2, List<HistoryModel> historymodelist2) {
        this.mContext = mContext2;
        this.historymodelist = historymodelist2;
    }

    public MailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_history_item, parent, false));
    }

    public void onBindViewHolder(MailViewHolder holder, int position) {
        HistoryModel model = this.historymodelist.get(position);
        holder.transfer_id.setText(model.getTransaction_id());
        TextView textView = holder.amount;
        textView.setText(" ₹ " + model.getAmount());
        holder.comments.setText(model.getComments());
        holder.created_at.setText(model.getCreated_at());
    }

    public int getItemCount() {
        return this.historymodelist.size();
    }

    public class MailViewHolder extends RecyclerView.ViewHolder {
        TextView amount;
        TextView comments;
        TextView created_at;
        TextView transfer_id;

        public MailViewHolder(View itemView) {
            super(itemView);
            this.transfer_id = (TextView) itemView.findViewById(R.id.transfer_id);
            this.amount = (TextView) itemView.findViewById(R.id.amount);
            this.comments = (TextView) itemView.findViewById(R.id.comments);
            this.created_at = (TextView) itemView.findViewById(R.id.created_at);
        }
    }
}
