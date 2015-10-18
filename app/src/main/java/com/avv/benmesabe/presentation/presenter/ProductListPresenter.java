package com.avv.benmesabe.presentation.presenter;

import android.support.annotation.NonNull;

import com.avv.benmesabe.domain.Product;
import com.avv.benmesabe.domain.exception.DefaultErrorBundle;
import com.avv.benmesabe.domain.exception.ErrorBundle;
import com.avv.benmesabe.domain.interactor.DefaultSubscriber;
import com.avv.benmesabe.domain.interactor.UseCase;
import com.avv.benmesabe.presentation.exception.ErrorMessageFactory;
import com.avv.benmesabe.presentation.internal.di.PerActivity;
import com.avv.benmesabe.presentation.view.ProductListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by angel on 13/10/2015.
 */
@PerActivity
public class ProductListPresenter extends DefaultSubscriber<List<Product>> implements Presenter {

    private final UseCase getProductListUseCase;
    private ProductListView viewListView;

    @Inject
    public ProductListPresenter(@Named("productList") UseCase getProductListUserCase) {
            this.getProductListUseCase = getProductListUserCase;
    }

    public void setView(@NonNull ProductListView view) {
        this.viewListView = view;
    }

    public void initialize(){
        loadProductList();
    }

    private void loadProductList() {
        //this.hideViewRetry();
        this.showViewLoading();
        this.getProductListUseCase.execute(new ProductListSubscriber());
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getProductListUseCase.unsubscribe();
    }

    private void showViewLoading() {
        this.viewListView.showLoading();
    }

    private void hideViewLoading() {
        this.viewListView.hideLoading();
    }

    private void showViewRetry() {
        this.viewListView.showRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewListView.getContext(),
                errorBundle.getException());
        this.viewListView.showError(errorMessage);
    }

    private void showProductCollectionInView(Collection<Product> productCollection) {
        this.viewListView.renderProductList(productCollection);
    }


    private final class ProductListSubscriber extends DefaultSubscriber<List<Product>> {

        @Override public void onCompleted() {
            ProductListPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            ProductListPresenter.this.hideViewLoading();
            ProductListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            ProductListPresenter.this.showViewRetry();
        }

        @Override public void onNext(List<Product> products) {
            ProductListPresenter.this.showProductCollectionInView(products);
        }
    }
}
