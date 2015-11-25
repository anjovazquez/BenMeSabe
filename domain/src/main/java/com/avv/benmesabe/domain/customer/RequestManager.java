package com.avv.benmesabe.domain.customer;

import com.avv.benmesabe.domain.CustomerRequest;

/**
 * Created by angelvazquez on 4/11/15.
 */
public class RequestManager {

    private CustomerRequest currentRequest;
    private static RequestManager ourInstance = new RequestManager();

    public static RequestManager getInstance() {
        return ourInstance;
    }

    private RequestManager() {
        this.currentRequest = new CustomerRequest();
    }

    public void openRequest(){
        this.currentRequest = new CustomerRequest();
    }

    public void setTableNo(String tableNo){
        currentRequest.setTableNo(tableNo);
    }

    public void setType(String type){
        currentRequest.setType(type);
    }

    public CustomerRequest closeRequest(){
        CustomerRequest closedRequest = currentRequest;
        currentRequest = new CustomerRequest();
        return closedRequest;
    }

    public CustomerRequest getCurrentOrder(){
        return currentRequest;
    }
}
