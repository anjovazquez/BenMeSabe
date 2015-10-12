package com.avv.benmesabe.data.repository.datasource;

import java.util.List;
import rx.Observable;

import com.avv.benmesabe.data.entity.ProductEntity;

/**
 * Created by angel on 12/10/2015.
 */
public interface BenMeSabeDataStore {

    Observable<List<ProductEntity>> productEntityList();

}
