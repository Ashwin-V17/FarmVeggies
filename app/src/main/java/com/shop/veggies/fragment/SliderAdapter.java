package com.shop.veggies.fragment;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
 // Change to your actual package name
import com.shop.veggies.R;
import com.shop.veggies.activity.OfferActivity;
import com.shop.veggies.model.BannerModel;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    private List<BannerModel> sliderList;
    private Context context;

    public SliderAdapter(Context context, List<BannerModel> sliderList) {
        this.context = context;
        this.sliderList = sliderList;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        BannerModel sliderItem = sliderList.get(position);

        Glide.with(context)
                .load(sliderItem.getBanner_image())
                .placeholder(R.drawable.dummycat)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, OfferActivity.class);
            intent.putExtra("page", "brand");
            intent.putExtra("id", sliderItem.getBanner_id());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return sliderList.size();
    }

    public static class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
