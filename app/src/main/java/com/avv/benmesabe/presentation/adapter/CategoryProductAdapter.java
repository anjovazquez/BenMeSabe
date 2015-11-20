package com.avv.benmesabe.presentation.adapter;

import android.content.Context;
import android.view.View;

import com.avv.benmesabe.R;
import com.marshalchen.ultimaterecyclerview.expanx.SmartItem;
import com.marshalchen.ultimaterecyclerview.expanx.Util.DataUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelvazquez on 20/11/15.
 */
public class CategoryProductAdapter<Category, CategoryProduct> {

    public CategoryProductAdapter(Context context) {
        super(context);
    }


    public static List<SmartItem> getPreCodeMenu(String[] a, String[] b, String[] c) {
        List<SmartItem> e = new ArrayList<>();
        e.add(SmartItem.parent("hones", "open", DataUtil.getSmallList(a)));
        e.add(SmartItem.parent("XXX", "open", DataUtil.getSmallList(b)));
        e.add(SmartItem.parent("RIVER", "open", DataUtil.getSmallList(c)));
        return e;
    }


    /**
     * please do work on this id the custom object is true
     *
     * @param parentview the inflated view
     * @return the actual parent holder
     */
    @Override
    protected Category iniCustomParentHolder(View parentview) {
        return new Category(parentview);
    }

    /**
     * please do work on this if the custom object is true
     *
     * @param childview the inflated view
     * @return the actual child holder
     */
    @Override
    protected CategoryProduct iniCustomChildHolder(View childview) {
        return new CategoryProduct(childview);
    }

    @Override
    protected int getLayoutResParent() {
        return R.layout.exp_parent;
    }

    @Override
    protected int getLayoutResChild() {
        return R.layout.exp_child;
    }

    @Override
    protected List<SmartItem> getChildrenByPath(String path, int depth, int position) {
        return null;
    }

}
