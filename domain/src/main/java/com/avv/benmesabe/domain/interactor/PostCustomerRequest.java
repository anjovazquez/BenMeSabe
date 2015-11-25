package com.avv.benmesabe.domain.interactor;

import com.avv.benmesabe.domain.CustomerRequest;
import com.avv.benmesabe.domain.executor.PostExecutionThread;
import com.avv.benmesabe.domain.executor.ThreadExecutor;
import com.avv.benmesabe.domain.repository.BenMeSabeRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by angelvazquez on 1/11/15.
 */
public class PostCustomerRequest extends UseCase {

    private CustomerRequest customerRequest;
    private final BenMeSabeRepository benMeSabeRepository;

    @Inject
    public PostCustomerRequest(CustomerRequest customerRequest, BenMeSabeRepository benMeSabeRepository, ThreadExecutor threadExecutor,
                               PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.customerRequest = customerRequest;
        this.benMeSabeRepository = benMeSabeRepository;
    }

    @Override public Observable buildUseCaseObservable() {
        return this.benMeSabeRepository.postCustomerRequest(customerRequest);
    }


}
