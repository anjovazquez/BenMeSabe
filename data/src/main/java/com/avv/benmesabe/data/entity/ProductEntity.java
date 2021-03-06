package com.avv.benmesabe.data.entity;

/**
 * Created by angel on 12/10/2015.
 */
public class ProductEntity {

    private Number productId;
    private String productName;
    private String description;
    private String imageURL;
    private String productSection;
    private Number productPrice;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Number getProductId() {
        return productId;
    }

    public void setProductId(Number productId) {
        this.productId = productId;
    }

    public String getProductSection() {
        return productSection;
    }

    public void setProductSection(String productSection) {
        this.productSection = productSection;
    }

    public Number getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Number productPrice) {
        this.productPrice = productPrice;
    }
}
