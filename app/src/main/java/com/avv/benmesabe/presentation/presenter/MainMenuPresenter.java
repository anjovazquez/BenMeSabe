package com.avv.benmesabe.presentation.presenter;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.avv.benmesabe.domain.CustomerRequest;
import com.avv.benmesabe.domain.Order;
import com.avv.benmesabe.domain.interactor.DefaultSubscriber;
import com.avv.benmesabe.domain.interactor.PostCustomerRequest;
import com.avv.benmesabe.domain.interactor.PostOrder;
import com.avv.benmesabe.domain.interactor.UseCase;
import com.avv.benmesabe.presentation.gcm.service.BenMeSabePreferences;
import com.avv.benmesabe.presentation.view.MainMenuView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by angelvazquez on 25/11/15.
 */
public class MainMenuPresenter extends DefaultSubscriber<Order> implements Presenter {

    private UseCase postOrderUseCase;
    private UseCase postCustomerRequestUseCase;

    private MainMenuView mainMenuView;

    @Inject
    public MainMenuPresenter(@Named("postOrder") UseCase postOrderUseCase, @Named("postCustomerRequest") UseCase postCustomerRequestUseCase) {
        this.postOrderUseCase = postOrderUseCase;
        this.postCustomerRequestUseCase = postCustomerRequestUseCase;
    }

    public void postOrder(Order order) {
        //this.hideViewRetry();
        this.showViewLoading();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mainMenuView.getContext());
        order.setCustomerToken(sharedPreferences.getString(BenMeSabePreferences.CURRENT_TOKEN, ""));
        ((PostOrder)postOrderUseCase).setOrder(order);
        this.postOrderUseCase.execute(new OrderSubscriber());
    }

    public void postCustomerRequest(CustomerRequest request){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mainMenuView.getContext());
        request.setCustomerToken(sharedPreferences.getString(BenMeSabePreferences.CURRENT_TOKEN, ""));
        ((PostCustomerRequest)postCustomerRequestUseCase).setCustomerRequest(request);
        this.postCustomerRequestUseCase.execute(new CustomerRequestSubscriber());
    }

    public void setView(@NonNull MainMenuView view) {
        this.mainMenuView = view;
    }

    private void showViewLoading() {
        this.mainMenuView.showLoading();
    }

    private void hideViewLoading() {
        this.mainMenuView.hideLoading();
    }

    private void showViewRetry() {
        this.mainMenuView.showRetry();
    }

    private void showOrderInView(Order order) {
        mainMenuView.showOrderInView(order);
    }

    private void showCustomerRequestInView(CustomerRequest customerRequest) {
        mainMenuView.showCustomerRequestInView(customerRequest);
    }


        @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    private final class OrderSubscriber extends DefaultSubscriber<Order> {

        @Override public void onCompleted() {
            hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            hideViewLoading();
            ((Exception) e).printStackTrace();
            //showErrorMessage(new DefaultErrorBundle((Exception) e));
            showViewRetry();
        }

        @Override public void onNext(Order order) {
            showOrderInView(order);
        }
    }

    private final class CustomerRequestSubscriber extends DefaultSubscriber<CustomerRequest> {

        @Override public void onCompleted() {
            hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            hideViewLoading();
            ((Exception) e).printStackTrace();
            //showErrorMessage(new DefaultErrorBundle((Exception) e));
            showViewRetry();
        }

        @Override public void onNext(CustomerRequest customerRequest) {
            showCustomerRequestInView(customerRequest);
        }
    }
}
