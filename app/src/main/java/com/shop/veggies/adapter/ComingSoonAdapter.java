package com.shop.veggies.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shop.veggies.model.CategoryModel;

import java.util.List;

public class ComingSoonAdapter extends ArrayAdapter<CategoryModel> {
    private List<CategoryModel> categoryModelList;
    private Context context;
    private int layoutResourceId;

    public ComingSoonAdapter(Context context2, int layoutResourceId2, List<CategoryModel> categoryModelList2) {
        super(context2, layoutResourceId2, categoryModelList2);
        this.context = context2;
        this.layoutResourceId = layoutResourceId2;
        this.categoryModelList = categoryModelList2;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: com.shop.veggis.adapter.ComingSoonAdapter$RecordHolder} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getView(int r7, android.view.View r8, android.view.ViewGroup r9) {
        /*
            r6 = this;
            r0 = r8
            r1 = 0
            if (r0 != 0) goto L_0x0040
            android.content.Context r2 = r6.context
            java.lang.String r3 = "layout_inflater"
            java.lang.Object r2 = r2.getSystemService(r3)
            android.view.LayoutInflater r2 = (android.view.LayoutInflater) r2
            int r3 = r6.layoutResourceId
            r4 = 0
            android.view.View r0 = r2.inflate(r3, r9, r4)
            com.shop.veggis.adapter.ComingSoonAdapter$RecordHolder r3 = new com.shop.veggis.adapter.ComingSoonAdapter$RecordHolder
            r3.<init>()
            r1 = r3
            r3 = 2131296842(0x7f09024a, float:1.8211612E38)
            android.view.View r3 = r0.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r1.tv_catTitle = r3
            r3 = 2131296550(0x7f090126, float:1.821102E38)
            android.view.View r3 = r0.findViewById(r3)
            android.widget.ImageView r3 = (android.widget.ImageView) r3
            r1.category_image = r3
            r3 = 2131296577(0x7f090141, float:1.8211075E38)
            android.view.View r3 = r0.findViewById(r3)
            android.widget.LinearLayout r3 = (android.widget.LinearLayout) r3
            r1.ll_parent = r3
            r0.setTag(r1)
            goto L_0x0047
        L_0x0040:
            java.lang.Object r2 = r0.getTag()
            r1 = r2
            com.shop.veggis.adapter.ComingSoonAdapter$RecordHolder r1 = (com.shop.veggis.adapter.ComingSoonAdapter.RecordHolder) r1
        L_0x0047:
            java.util.List<com.shop.veggis.model.CategoryModel> r2 = r6.categoryModelList
            java.lang.Object r2 = r2.get(r7)
            com.shop.veggis.model.CategoryModel r2 = (com.shop.veggis.model.CategoryModel) r2
            android.widget.TextView r3 = r1.tv_catTitle
            java.lang.String r4 = r2.getCategory_name()
            r3.setText(r4)
            android.widget.TextView r3 = r1.tv_catTitle
            java.lang.String r4 = r2.getCategory_name()
            android.text.Spanned r4 = android.text.Html.fromHtml(r4)
            java.lang.String r4 = java.lang.String.valueOf(r4)
            android.text.Spanned r4 = android.text.Html.fromHtml(r4)
            r3.setText(r4)
            java.lang.String r3 = r2.getCategory_image()
            android.content.Context r4 = r6.context
            com.bumptech.glide.RequestManager r4 = com.bumptech.glide.Glide.with((android.content.Context) r4)
            com.bumptech.glide.RequestBuilder r4 = r4.load((java.lang.String) r3)
            r5 = 2131165347(0x7f0700a3, float:1.7944909E38)
            com.bumptech.glide.request.BaseRequestOptions r4 = r4.placeholder((int) r5)
            com.bumptech.glide.RequestBuilder r4 = (com.bumptech.glide.RequestBuilder) r4
            android.widget.ImageView r5 = r1.category_image
            r4.into((android.widget.ImageView) r5)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.shop.veggies.adapter.ComingSoonAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    static class RecordHolder {
        ImageView category_image;
        LinearLayout ll_parent;
        TextView tv_catTitle;

        RecordHolder() {
        }
    }
}
