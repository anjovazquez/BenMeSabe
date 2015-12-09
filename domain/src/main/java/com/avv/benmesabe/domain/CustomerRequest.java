package com.avv.benmesabe.domain;

/**
 * Created by angelvazquez on 24/11/15.
 */
public class CustomerRequest {

    private String tableNo;
    private String type;
    private String idCompany;
    private String customerToken;

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(String idCompany) {
        this.idCompany = idCompany;
    }

    public String getCustomerToken() {
        return customerToken;
    }

    public void setCustomerToken(String customerToken) {
        this.customerToken = customerToken;
    }

    @Override
    public String toString() {
        return "CustomerRequest{" +
                "tableNo='" + tableNo + '\'' +
                ", type='" + type + '\'' +
                ", idCompany='" + idCompany + '\'' +
                ", customerToken='" + customerToken + '\'' +
                '}';
    }
}
