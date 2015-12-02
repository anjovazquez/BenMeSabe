package com.avv.benmesabe.domain.repository;

import com.avv.benmesabe.domain.Allergen;
import com.avv.benmesabe.domain.CustomerRequest;
import com.avv.benmesabe.domain.Ingredient;
import com.avv.benmesabe.domain.Order;
import com.avv.benmesabe.domain.Product;

import java.util.List;

import rx.Observable;

/**
 * Created by angel on 13/10/2015.
 */
public interface BenMeSabeRepository {
    Observable<List<Product>> products();
    Observable<List<Ingredient>> getProductIngredients(Number productId);
    Observable<List<Allergen>> getProductAllergens(Number productId);
    Observable<Order> postOrder(Order order);
    Observable<CustomerRequest> postCustomerRequest(CustomerRequest customerRequest);
    Observable<Product> getProductDetail(Number productId);
}
