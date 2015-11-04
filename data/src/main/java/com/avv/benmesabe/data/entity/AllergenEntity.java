package com.avv.benmesabe.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by angelvazquez on 1/11/15.
 */
public class AllergenEntity {

    @SerializedName("idAllergen")
    private Number allergenId;
    private String allergenName;
    private Number productId;

    public Number getAllergenId() {
        return allergenId;
    }

    public void setAllergenId(Number allergenId) {
        this.allergenId = allergenId;
    }

    public String getAllergenName() {
        return allergenName;
    }

    public void setAllergenName(String allergenName) {
        this.allergenName = allergenName;
    }

    public Number getProductId() {
        return productId;
    }

    public void setProductId(Number productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "AllergenEntity{" +
                "allergenId=" + allergenId +
                ", allergenName='" + allergenName + '\'' +
                ", productId=" + productId +
                '}';
    }
}
