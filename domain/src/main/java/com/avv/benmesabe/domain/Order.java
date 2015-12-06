package com.avv.benmesabe.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelvazquez on 4/11/15.
 */
public class Order {

    private String tableNo;
    private String orderName;
    private String customerToken;
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

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getCustomerToken() {
        return customerToken;
    }

    public void setCustomerToken(String customerToken) {
        this.customerToken = customerToken;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
