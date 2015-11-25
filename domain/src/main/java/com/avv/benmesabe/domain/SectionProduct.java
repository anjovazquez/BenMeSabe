package com.avv.benmesabe.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelvazquez on 21/11/15.
 */
public class SectionProduct {

    private String productSection;

    private List<Product> productList;

    public String getProductSection() {
        return productSection;
    }

    public void setProductSection(String productSection) {
        this.productSection = productSection;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public void addProduct(Product product){
        if(productList==null) {
            productList = new ArrayList<Product>();
        }
        productList.add(product);
    }

    @Override
    public String toString() {
        return "SectionProduct{" +
                "productSection='" + productSection + '\'' +
                ", productList=" + productList +
                '}';
    }
}
