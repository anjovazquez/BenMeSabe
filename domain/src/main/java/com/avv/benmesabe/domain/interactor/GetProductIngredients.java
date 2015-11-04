package com.avv.benmesabe.domain.interactor;

import com.avv.benmesabe.domain.executor.PostExecutionThread;
import com.avv.benmesabe.domain.executor.ThreadExecutor;
import com.avv.benmesabe.domain.repository.BenMeSabeRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by angelvazquez on 1/11/15.
 */
public class GetProductIngredients extends UseCase {

    private Number productId;
    private final BenMeSabeRepository benMeSabeRepository;

    @Inject
    public GetProductIngredients(Number productId, BenMeSabeRepository benMeSabeRepository, ThreadExecutor threadExecutor,
                          PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.productId = productId;
        this.benMeSabeRepository = benMeSabeRepository;
    }

    @Override public Observable buildUseCaseObservable() {
        return this.benMeSabeRepository.getProductIngredients(productId);
    }


}
