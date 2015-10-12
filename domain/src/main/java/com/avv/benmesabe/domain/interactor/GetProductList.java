package com.avv.benmesabe.domain.interactor;

import com.avv.benmesabe.domain.executor.PostExecutionThread;
import com.avv.benmesabe.domain.executor.ThreadExecutor;
import com.avv.benmesabe.domain.repository.BenMeSabeRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by angel on 13/10/2015.
 */
public class GetProductList extends UseCase {

    private final BenMeSabeRepository benMeSabeRepository;

    @Inject
    public GetProductList(BenMeSabeRepository benMeSabeRepository, ThreadExecutor threadExecutor,
                       PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.benMeSabeRepository = benMeSabeRepository;
    }

    @Override public Observable buildUseCaseObservable() {
        return this.benMeSabeRepository.products();
    }
}
