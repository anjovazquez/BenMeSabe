package com.avv.benmesabe.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelvazquez on 4/11/15.
 */
public class Order {

    private String tableNo;
    private String name;
    private List<Product> productList;

    public void addProduct(Product product){
        if(productList == null){
            productList = new ArrayList<Product>();
        }
        productList.add(product);
    }

    public void removeProduct(Product product){
        productList.remove(product);
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
