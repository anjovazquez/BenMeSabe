package com.avv.benmesabe.data.repository;

import com.avv.benmesabe.data.entity.mapper.BenMeSabeDataMapper;
import com.avv.benmesabe.data.repository.datasource.BenMeSabeDataStore;
import com.avv.benmesabe.data.repository.datasource.BenMeSabeDataStoreFactory;
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
