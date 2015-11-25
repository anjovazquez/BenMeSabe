package com.avv.benmesabe.presentation.presenter;

import android.support.annotation.NonNull;

import com.avv.benmesabe.domain.Order;
import com.avv.benmesabe.domain.interactor.DefaultSubscriber;
import com.avv.benmesabe.domain.interactor.UseCase;
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

    private void postOrder() {
        //this.hideViewRetry();
        this.showViewLoading();
        this.postOrderUseCase.execute(new OrderSubscriber());
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
}
