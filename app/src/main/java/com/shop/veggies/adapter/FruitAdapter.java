package com.shop.veggies.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.shop.veggies.R;
import com.shop.veggies.model.ProductsModel;

import java.util.ArrayList;
import java.util.List;

public class FruitAdapter extends ArrayAdapter<ProductsModel> {
    private Context context;
    private Filter exampleFilter = new Filter() {
        /* access modifiers changed from: protected */
        public FilterResults performFiltering(CharSequence constraint) {
            List<ProductsModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(FruitAdapter.this.tempItems);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ProductsModel item : FruitAdapter.this.tempItems) {
                    if (item.getProduct_name().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        /* access modifiers changed from: protected */
        public void publishResults(CharSequence constraint, FilterResults results) {
            FruitAdapter.this.items.clear();
            FruitAdapter.this.items.addAll((List) results.values);
            FruitAdapter.this.notifyDataSetChanged();
        }
    };
    /* access modifiers changed from: private */
    public List<ProductsModel> items;
    private int resourceId;
    private List<ProductsModel> suggestions;
    /* access modifiers changed from: private */
    public List<ProductsModel> tempItems;

    public FruitAdapter(Context context2, int resourceId2, ArrayList<ProductsModel> items2) {
        super(context2, resourceId2, items2);
        this.items = items2;
        this.context = context2;
        this.resourceId = resourceId2;
        this.tempItems = new ArrayList(items2);
        this.suggestions = new ArrayList();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            try {
                view = ((Activity) this.context).getLayoutInflater().inflate(this.resourceId, parent, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ((TextView) view.findViewById(R.id.pname)).setText(getItem(position).getProduct_name());
        TextView textView = (TextView) view.findViewById(R.id.pid);
        return view;
    }

    public ProductsModel getItem(int position) {
        return this.items.get(position);
    }

    public int getCount() {
        return this.items.size();
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public Filter getFilter() {
        return this.exampleFilter;
    }
}
