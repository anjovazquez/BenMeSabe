package com.avv.benmesabe.data.repository.datasource;

import com.avv.benmesabe.data.entity.AllergenEntity;
import com.avv.benmesabe.data.entity.IngredientEntity;
import com.avv.benmesabe.data.entity.ProductEntity;
import com.avv.benmesabe.domain.CustomerRequest;
import com.avv.benmesabe.domain.Order;

import java.util.List;

import rx.Observable;

/**
 * Created by angel on 12/10/2015.
 */
public interface BenMeSabeDataStore {

    Observable<List<ProductEntity>> productEntityList();

    Observable<List<IngredientEntity>> getProductIngredients(Number productId);

    Observable<List<AllergenEntity>> getProductAllergens(Number productId);

    Observable<Order> postOrder(Order order);

    Observable<CustomerRequest> postCustomerRequest(CustomerRequest customerRequest);

    Observable<ProductEntity> getProductDetail(Number productId);

}
