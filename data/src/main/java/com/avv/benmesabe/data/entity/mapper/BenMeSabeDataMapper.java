package com.avv.benmesabe.data.entity.mapper;

import com.avv.benmesabe.data.entity.ProductEntity;
import com.avv.benmesabe.domain.Product;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

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
            product.setDescription(productEntity.getDescription());

        }
        return product;
    }

    public List<Product> transform(List<ProductEntity> productEntityCollection){
        List<Product> productList = new ArrayList<Product>();

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
