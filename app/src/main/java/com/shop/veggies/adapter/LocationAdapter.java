package com.shop.veggies.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shop.veggies.model.ZipcodeModel;

import java.util.List;

public class LocationAdapter extends ArrayAdapter<ZipcodeModel> {
    private List<ZipcodeModel> categoryModelList;
    private Context context;
    private int layoutResourceId;
    String pin;
    ProgressDialog progressDialog;

    public LocationAdapter(Context context2, int layoutResourceId2, List<ZipcodeModel> categoryModelList2, String pin2) {
        super(context2, layoutResourceId2, categoryModelList2);
        this.context = context2;
        this.layoutResourceId = layoutResourceId2;
        this.categoryModelList = categoryModelList2;
        this.pin = pin2;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: com.shop.veggis.adapter.LocationAdapter$RecordHolder} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getView(int r6, android.view.View r7, android.view.ViewGroup r8) {
        /*
            r5 = this;
            r0 = r7
            r1 = 0
            if (r0 != 0) goto L_0x004b
            android.content.Context r2 = r5.context
            java.lang.String r3 = "layout_inflater"
            java.lang.Object r2 = r2.getSystemService(r3)
            android.view.LayoutInflater r2 = (android.view.LayoutInflater) r2
            int r3 = r5.layoutResourceId
            r4 = 0
            android.view.View r0 = r2.inflate(r3, r8, r4)
            com.shop.veggis.adapter.LocationAdapter$RecordHolder r3 = new com.shop.veggis.adapter.LocationAdapter$RecordHolder
            r3.<init>()
            r1 = r3
            r3 = 2131296842(0x7f09024a, float:1.8211612E38)
            android.view.View r3 = r0.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r1.tv_catTitle = r3
            r3 = 2131296565(0x7f090135, float:1.821105E38)
            android.view.View r3 = r0.findViewById(r3)
            android.widget.LinearLayout r3 = (android.widget.LinearLayout) r3
            r1.ll_parent = r3
            android.app.ProgressDialog r3 = new android.app.ProgressDialog
            android.content.Context r4 = r5.context
            r3.<init>(r4)
            r5.progressDialog = r3
            java.lang.String r4 = "Loading....."
            r3.setMessage(r4)
            java.util.List<com.shop.veggis.model.ZipcodeModel> r3 = r5.categoryModelList
            java.lang.Object r3 = r3.get(r6)
            com.shop.veggis.model.ZipcodeModel r3 = (com.shop.veggis.model.ZipcodeModel) r3
            r0.setTag(r1)
            goto L_0x0052
        L_0x004b:
            java.lang.Object r2 = r0.getTag()
            r1 = r2
            com.shop.veggis.adapter.LocationAdapter$RecordHolder r1 = (com.shop.veggis.adapter.LocationAdapter.RecordHolder) r1
        L_0x0052:
            java.util.List<com.shop.veggis.model.ZipcodeModel> r2 = r5.categoryModelList
            java.lang.Object r2 = r2.get(r6)
            com.shop.veggis.model.ZipcodeModel r2 = (com.shop.veggis.model.ZipcodeModel) r2
            android.widget.TextView r3 = r1.tv_catTitle
            java.lang.String r4 = r2.getZipcode()
            r3.setText(r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.shop.veggies.adapter.LocationAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    static class RecordHolder {
        ImageView category_image;
        LinearLayout ll_parent;
        TextView tv_catTitle;

        RecordHolder() {
        }
    }
}
