package com.avv.benmesabe.data.repository;

import com.avv.benmesabe.data.entity.mapper.BenMeSabeDataMapper;
import com.avv.benmesabe.data.repository.datasource.BenMeSabeDataStore;
import com.avv.benmesabe.data.repository.datasource.BenMeSabeDataStoreFactory;
import com.avv.benmesabe.domain.Allergen;
import com.avv.benmesabe.domain.CustomerRequest;
import com.avv.benmesabe.domain.Ingredient;
import com.avv.benmesabe.domain.Order;
import com.avv.benmesabe.domain.Product;
import com.avv.benmesabe.domain.repository.BenMeSabeRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class BenMeSabeDataRepository implements BenMeSabeRepository {

    private final BenMeSabeDataStoreFactory benMeSabeDataStoreFactory;
    private final BenMeSabeDataMapper benMeSabeDataMapper;

    @Inject
    public BenMeSabeDataRepository(BenMeSabeDataStoreFactory benMeSabeDataStoreFactory, BenMeSabeDataMapper benMeSabeDataMapper){
        this.benMeSabeDataStoreFactory = benMeSabeDataStoreFactory;
        this.benMeSabeDataMapper = benMeSabeDataMapper;
    }

    @Override
    public Observable<List<Product>> products(){
        final BenMeSabeDataStore benMeSabeDataStore = this.benMeSabeDataStoreFactory.createCloudDataStore();
        return benMeSabeDataStore.productEntityList().map(productEntities -> this.benMeSabeDataMapper.transform(productEntities));
    }

    @Override
    public Observable<List<Ingredient>> getProductIngredients(Number productId) {
        final BenMeSabeDataStore benMeSabeDataStore = this.benMeSabeDataStoreFactory.createCloudDataStore();
        return benMeSabeDataStore.getProductIngredients(productId).map(ingredientEntities -> this.benMeSabeDataMapper.transformIngredientList(ingredientEntities));
    }

    @Override
    public Observable<List<Allergen>> getProductAllergens(Number productId) {
        final BenMeSabeDataStore benMeSabeDataStore = this.benMeSabeDataStoreFactory.createCloudDataStore();
        return benMeSabeDataStore.getProductAllergens(productId).map(allergenEntities -> this.benMeSabeDataMapper.transformAllergenList(allergenEntities));
    }

    @Override
    public Observable<Order> postOrder(Order order) {
        final BenMeSabeDataStore benMeSabeDataStore = this.benMeSabeDataStoreFactory.createCloudDataStore();
        return benMeSabeDataStore.postOrder(order);
    }

    @Override
    public Observable<CustomerRequest> postCustomerRequest(CustomerRequest customerRequest) {
        final BenMeSabeDataStore benMeSabeDataStore = this.benMeSabeDataStoreFactory.createCloudDataStore();
        return benMeSabeDataStore.postCustomerRequest(customerRequest);
    }


    @Override
    public Observable<Product> getProductDetail(Number productId) {
        final BenMeSabeDataStore benMeSabeDataStore = this.benMeSabeDataStoreFactory.createCloudDataStore();
        return benMeSabeDataStore.getProductDetail(productId).map(productEntity -> this.benMeSabeDataMapper.transform(productEntity));
    }
}
