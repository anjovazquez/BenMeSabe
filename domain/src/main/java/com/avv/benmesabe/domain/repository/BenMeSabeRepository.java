package com.avv.benmesabe.domain.repository;

import com.avv.benmesabe.domain.Product;

import java.util.List;

import rx.Observable;

/**
 * Created by angel on 13/10/2015.
 */
public interface BenMeSabeRepository {
    Observable<List<Product>> products();
}
