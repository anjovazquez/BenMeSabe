package com.avv.benmesabe.domain.interactor;

import com.avv.benmesabe.domain.Order;
import com.avv.benmesabe.domain.executor.PostExecutionThread;
import com.avv.benmesabe.domain.executor.ThreadExecutor;
import com.avv.benmesabe.domain.repository.BenMeSabeRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by angelvazquez on 1/11/15.
 */
public class PostOrder extends UseCase {

    private Order order;
    private final BenMeSabeRepository benMeSabeRepository;

    @Inject
    public PostOrder(Order order, BenMeSabeRepository benMeSabeRepository, ThreadExecutor threadExecutor,
                     PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.order = order;
        this.benMeSabeRepository = benMeSabeRepository;
    }

    @Override public Observable buildUseCaseObservable() {
        return this.benMeSabeRepository.postOrder(order);
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
