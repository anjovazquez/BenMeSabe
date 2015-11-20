package com.avv.benmesabe.presentation.adapter;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.expanx.SmartItem;
import com.marshalchen.ultimaterecyclerview.expanx.Util.easyTemplateChild;

/**
 * Created by angelvazquez on 20/11/15.
 */
public class CategoryProduct extends easyTemplateChild<SmartItem, TextView, RelativeLayout> {
    public CategoryProduct(View itemView) {
        super(itemView);
    }
}
