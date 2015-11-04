package com.avv.benmesabe.domain;

/**
 * Created by angelvazquez on 1/11/15.
 */
public class Ingredient {

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

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredientId=" + ingredientId +
                ", ingredientName='" + ingredientName + '\'' +
                ", productId=" + productId +
                '}';
    }
}
