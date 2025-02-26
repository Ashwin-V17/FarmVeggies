package com.shop.veggies.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shop.veggies.R;
import com.shop.veggies.model.PlanChildModel;
import com.shop.veggies.model.PlanHeaderModel;

import java.util.ArrayList;

public class CategoryListAdapter extends BaseExpandableListAdapter {
    ArrayList<PlanHeaderModel> categoryChildModelArrayList;
    private Context context;
    ArrayList<PlanHeaderModel> originalCategoryChildModelArrayList;

    public CategoryListAdapter(Context context2, ArrayList<PlanHeaderModel> categoryChildModelArrayList2) {
        this.context = context2;
        ArrayList<PlanHeaderModel> arrayList = new ArrayList<>();
        this.originalCategoryChildModelArrayList = arrayList;
        arrayList.addAll(arrayList);
        ArrayList<PlanHeaderModel> arrayList2 = new ArrayList<>();
        this.categoryChildModelArrayList = arrayList2;
        arrayList2.addAll(categoryChildModelArrayList2);
    }

    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
    }

    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
    }

    public void onGroupExpanded(int i) {
    }

    public void onGroupCollapsed(int i) {
    }

    public boolean isEmpty() {
        if (this.categoryChildModelArrayList.size() == 0) {
            return true;
        }
        return false;
    }

    public int getGroupCount() {
        return this.categoryChildModelArrayList.size();
    }

    public int getChildrenCount(int i) {
        return this.categoryChildModelArrayList.get(i).getCategoryChildModelArrayList().size();
    }

    public PlanHeaderModel getGroup(int i) {
        return this.categoryChildModelArrayList.get(i);
    }

    public PlanChildModel getChild(int groupPosition, int childPosition) {
        return this.categoryChildModelArrayList.get(groupPosition).getCategoryChildModelArrayList().get(childPosition);
    }

    public long getGroupId(int i) {
        return (long) i;
    }

    public long getChildId(int i, int i1) {
        return (long) i1;
    }

    public boolean hasStableIds() {
        return true;
    }

    public View getGroupView(int position, boolean b, View contentView, ViewGroup parent) {
        PlanHeaderModel planHeaderModel = this.categoryChildModelArrayList.get(position);
        if (contentView == null) {
            contentView = LayoutInflater.from(this.context).inflate(R.layout.layout_header, parent, false);
        }
        ((TextView) contentView.findViewById(R.id.tv_cat_title)).setText(planHeaderModel.getPlanHeader_title());
        ((ImageView) contentView.findViewById(R.id.ivGroupIndicator)).setSelected(b);
        RelativeLayout relativeLayout = (RelativeLayout) contentView.findViewById(R.id.ll_parent);
        return contentView;
    }

    public View getChildView(int groupPosition, int childPosition, boolean b, View contentView, ViewGroup parent) {
        PlanChildModel planChildModel = this.categoryChildModelArrayList.get(groupPosition).getCategoryChildModelArrayList().get(childPosition);
        if (contentView == null) {
            contentView = LayoutInflater.from(this.context).inflate(R.layout.layout_child, parent, false);
        }
        ((TextView) contentView.findViewById(R.id.tv_sub_title)).setText(planChildModel.getChild_name());
        return contentView;
    }

    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public boolean areAllItemsEnabled() {
        return true;
    }

    public long getCombinedChildId(long l, long l1) {
        return l1;
    }

    public long getCombinedGroupId(long l) {
        return l;
    }
}
