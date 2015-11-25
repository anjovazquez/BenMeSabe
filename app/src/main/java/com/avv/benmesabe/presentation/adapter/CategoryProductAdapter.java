package com.avv.benmesabe.presentation.adapter;

import android.content.Context;
import android.view.View;

import com.avv.benmesabe.R;
import com.avv.benmesabe.domain.Product;
import com.avv.benmesabe.domain.SectionProduct;
import com.marshalchen.ultimaterecyclerview.expanx.SmartItem;
import com.marshalchen.ultimaterecyclerview.expanx.customizedAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by angelvazquez on 20/11/15.
 */
public class CategoryProductAdapter extends customizedAdapter<Category, CategoryProduct> implements CategoryProduct.OnCategoryProductItemListener {

    private Collection<Product> productCollection;

    public CategoryProductAdapter(Context context) {
        super(context);
    }

    private Product searchProductByName(String productName){
        for(Product p:productCollection){
            if(productName.equals(p.getProductName())){
                return p;
            }
        }
        return null;
    }

    public void setProductsCollection(Collection<Product> productCollection){
        this.productCollection = productCollection;
        setProductBySection(transformSectionsProductList(productCollection));
    }

    private ArrayList<SectionProduct> transformSectionsProductList(Collection<Product> productModelCollection){
        Collections.sort((ArrayList<Product>) productModelCollection, new Comparator<Product>() {
            @Override
            public int compare(Product lhs, Product rhs) {
                if (lhs.getProductSection() != null && rhs.getProductSection() != null) {
                    return lhs.getProductSection().compareTo(rhs.getProductSection());
                }
                return 0;
            }
        });

        ArrayList<SectionProduct> sectionProduct = new ArrayList<SectionProduct>();
        SectionProduct defaultSection = new SectionProduct();
        defaultSection.setProductSection("Variado");
        for(int i=0;i<productModelCollection.size();i++){
            Product prod = ((ArrayList<Product>) productModelCollection).get(i);
            if(prod.getProductSection()!=null) {
                SectionProduct sp = getSection(sectionProduct, prod.getProductSection());
                if(sp!=null){
                    sp.addProduct(prod);
                }
                else{
                    sp = new SectionProduct();
                    sp.setProductSection(prod.getProductSection());
                    sp.addProduct(prod);
                    sectionProduct.add(sp);
                }
            }
            else{
                defaultSection.addProduct(prod);
            }
        }
        sectionProduct.add(defaultSection);

        return sectionProduct;
    }

    private SectionProduct getSection(ArrayList<SectionProduct> sectionProducts, String section){
        for(SectionProduct sp:sectionProducts){
            if(sp.getProductSection().equals(section)) {
                return sp;
            }
        }
        return null;
    }

    private void setProductBySection(List<SectionProduct> sectionProducts){
        List<SmartItem> items = new ArrayList<SmartItem>();
        for(SectionProduct sp:sectionProducts) {
            List<SmartItem> itemsChildList = new ArrayList<SmartItem>();
            for(int i=0;i<sp.getProductList().size();i++){
                itemsChildList.add(SmartItem.child(sp.getProductList().get(i).getProductName(), sp.getProductList().get(i).getImageURL()));
            }
            items.add(SmartItem.parent(sp.getProductSection(), "open", itemsChildList));
        }
        this.addAll(items, 0);
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
        CategoryProduct categoryProduct = new CategoryProduct(childview);
        categoryProduct.setOnCategoryProductItemClickListener(this);
        return categoryProduct;
    }

    @Override
    protected int getLayoutResParent() {
        return R.layout.row_category;
    }

    @Override
    protected int getLayoutResChild() {
        return R.layout.row_category_product;
    }

    @Override
    protected List<SmartItem> getChildrenByPath(String path, int depth, int position) {
        return null;
    }

    @Override
    public void onCategoryProductItemClicked(String productName) {
        if(onItemClickListener!=null) {
            onItemClickListener.onProductItemClicked(searchProductByName(productName));
        }
    }

    private OnProductItemClickListener onItemClickListener;

    public void setOnProductItemClickListener (OnProductItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnProductItemClickListener {
        void onProductItemClicked(Product product);
    }

}
