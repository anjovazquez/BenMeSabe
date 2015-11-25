package com.avv.benmesabe.presentation.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avv.benmesabe.R;
import com.marshalchen.ultimaterecyclerview.expanx.SmartItem;
import com.marshalchen.ultimaterecyclerview.expanx.Util.easyTemplateChild;
import com.squareup.picasso.Picasso;

/**
 * Created by angelvazquez on 20/11/15.
 */
public class CategoryProduct extends easyTemplateChild<SmartItem, TextView, RelativeLayout> {
    public CategoryProduct(View itemView) {
        super(itemView);
    }

    @Override
    public void bindView(final SmartItem itemData, int position) {
        ((TextView)itemView.findViewById(R.id.title)).setText(itemData.getText());
        Picasso.with(itemView.getContext()).load(itemData.getPath()).into(((ImageView)itemView.findViewById(R.id.imageProduct)));

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCategoryProductItemListener.onCategoryProductItemClicked(itemData.getText());
            }
        });
    }

    private OnCategoryProductItemListener onCategoryProductItemListener;

    public void setOnCategoryProductItemClickListener (OnCategoryProductItemListener onCategoryProductItemListener) {
        this.onCategoryProductItemListener = onCategoryProductItemListener;
    }

    public interface OnCategoryProductItemListener {
        void onCategoryProductItemClicked(String productName);
    }



}
