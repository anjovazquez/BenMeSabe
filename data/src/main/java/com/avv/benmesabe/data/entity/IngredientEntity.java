package com.avv.benmesabe.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by angelvazquez on 1/11/15.
 */
public class IngredientEntity {

    @SerializedName("idIngredient")
    private Number ingredientId;
    private String ingredientName;
    private Number productId;

    public Number getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Number ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public Number getProductId() {
        return productId;
    }

    public void setProductId(Number productId) {
        this.productId = productId;
    }
}
