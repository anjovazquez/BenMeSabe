package com.avv.benmesabe.data.entity.mapper;

import com.avv.benmesabe.domain.Product;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.avv.benmesabe.data.entity.ProductEntity;

/**
 * Created by angel on 12/10/2015.
 */

@Singleton
public class BenMeSabeDataMapper {

    @Inject
    public BenMeSabeDataMapper(){}

    public Product transform(ProductEntity productEntity){
        Product product = new Product();
        if(productEntity!=null){
            product = new Product();

        }
        return product;
    }

    public List<Product> transform(List<ProductEntity> productEntityCollection){
        List<Product> productList = null;

        Product product;
        for(ProductEntity productEntity:productEntityCollection) {
            product = transform(productEntity);
            if(product!=null){
                productList.add(product);
            }
        }

        return productList;
    }
}
