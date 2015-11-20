package com.avv.benmesabe.presentation.adapter;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.expanx.SmartItem;
import com.marshalchen.ultimaterecyclerview.expanx.Util.easyTemplateParent;

/**
 * Created by angelvazquez on 20/11/15.
 */
public class Category extends easyTemplateParent<SmartItem, RelativeLayout, TextView> {
    public Category(View itemView) {
        super(itemView);
    }
}
