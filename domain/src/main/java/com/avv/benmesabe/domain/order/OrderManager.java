package com.avv.benmesabe.domain.order;

import com.avv.benmesabe.domain.Order;
import com.avv.benmesabe.domain.Product;

import java.util.List;

/**
 * Created by angelvazquez on 4/11/15.
 */
public class OrderManager {

    private Order currentOrder;
    private static OrderManager ourInstance = new OrderManager();

    public static OrderManager getInstance() {
        return ourInstance;
    }

    private OrderManager() {
        this.currentOrder = new Order();
    }

    public void openOrder(){
        this.currentOrder = new Order();
    }

    public void setTableNo(String tableNo){
        currentOrder.setTableNo(tableNo);
    }

    public void setOrderName(String orderName){
        currentOrder.setOrderName(orderName);
    }

    public void addProduct(Product product){
        currentOrder.addProduct(product);
    }

    public void removeProduct(Product product){
        currentOrder.removeProduct(product);
    }

    public Order closeOrder(){
        Order closedOrder = currentOrder;
        closedOrder.setOrderName("TRACACACACTRA");
        closedOrder.setTableNo("t23");
        currentOrder = new Order();
        return closedOrder;
    }

    public Order getCurrentOrder(){
        return currentOrder;
    }

    public List<Product> getProductOrderList(){
        return currentOrder.getProductList();
    }
}
