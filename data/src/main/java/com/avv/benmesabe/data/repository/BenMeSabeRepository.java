package com.avv.benmesabe.data.repository;

import com.avv.benmesabe.data.repository.datasource.BenMeSabeDataStore;
import com.avv.benmesabe.data.repository.datasource.BenMeSabeDataStoreFactory;
import com.avv.benmesabe.domain.Product;

import java.util.List;

import entity.mapper.BenMeSabeDataMapper;
import rx.Observable;

public class BenMeSabeRepository {

    private final BenMeSabeDataStoreFactory benMeSabeDataStoreFactory;
    private final BenMeSabeDataMapper benMeSabeDataMapper;

    public BenMeSabeRepository(BenMeSabeDataStoreFactory benMeSabeDataStoreFactory, BenMeSabeDataMapper benMeSabeDataMapper){
        this.benMeSabeDataStoreFactory = benMeSabeDataStoreFactory;
        this.benMeSabeDataMapper = benMeSabeDataMapper;
    }

    public Observable<List<Product>> products(){
        final BenMeSabeDataStore benMeSabeDataStore = this.benMeSabeDataStoreFactory.createCloudDataStore();
        return benMeSabeDataStore.productEntityList().map(productEntities -> this.benMeSabeDataMapper.transform(productEntities));
    }

    /*public Observable<Product> product(){

    }

    public Observable<List<Allergen>> allergens(){

    }

    public Observable<List<Ingredient>> ingredients(){

    }*/
}
