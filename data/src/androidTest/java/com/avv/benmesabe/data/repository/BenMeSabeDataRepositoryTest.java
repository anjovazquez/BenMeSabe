package com.avv.benmesabe.data.repository;

import com.avv.benmesabe.data.ApplicationTestCase;
import com.avv.benmesabe.data.entity.ProductEntity;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by angelvazquez on 22/10/15.
 */
public class BenMeSabeDataRepositoryTest extends ApplicationTestCase {

    @Test
    public void testGetProductsHappyCase() {
        List<ProductEntity> productList = new ArrayList<>();
        productList.add(new ProductEntity());
        //given(mockUserDataStore.userEntityList()).willReturn(Observable.just(usersList));

    }
}
