package com.avv.benmesabe.presentation.presenter;

import android.support.annotation.NonNull;

import com.avv.benmesabe.domain.Allergen;
import com.avv.benmesabe.domain.Ingredient;
import com.avv.benmesabe.domain.exception.DefaultErrorBundle;
import com.avv.benmesabe.domain.exception.ErrorBundle;
import com.avv.benmesabe.domain.interactor.DefaultSubscriber;
import com.avv.benmesabe.domain.interactor.UseCase;
import com.avv.benmesabe.presentation.exception.ErrorMessageFactory;
import com.avv.benmesabe.presentation.internal.di.PerActivity;
import com.avv.benmesabe.presentation.view.DetailProductView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by angel on 13/10/2015.
 */
@PerActivity
public class DetailProductPresenter extends DefaultSubscriber<List<Ingredient>> implements Presenter {

    private UseCase getProductIngredientListUseCase;
    private UseCase getProductAllergensListUseCase;
    private DetailProductView detailProductView;

    @Inject
    public DetailProductPresenter(@Named("productIngredientsList") UseCase getProductIngredientListUseCase,
                                  @Named("productAllergensList") UseCase getProductAllergensListUseCase) {
            this.getProductIngredientListUseCase = getProductIngredientListUseCase;
            this.getProductAllergensListUseCase = getProductAllergensListUseCase;
    }

    public void setView(@NonNull DetailProductView view) {
        this.detailProductView = view;
    }

    public void initialize(){
        loadProductIngredientsList();
        loadProductAllergensList();
    }

    private void loadProductIngredientsList() {
        //this.hideViewRetry();
        this.showViewLoading();
        this.getProductIngredientListUseCase.execute(new DetailProductSubscriber());
    }

    private void loadProductAllergensList() {
        //this.hideViewRetry();
        this.showViewLoading();
        this.getProductAllergensListUseCase.execute(new DetailProductAllergenSubscriber());
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getProductIngredientListUseCase.unsubscribe();
    }

    private void showViewLoading() {
        this.detailProductView.showLoading();
    }

    private void hideViewLoading() {
        this.detailProductView.hideLoading();
    }

    private void showViewRetry() {
        this.detailProductView.showRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.detailProductView.getContext(),
                errorBundle.getException());
        this.detailProductView.showError(errorMessage);
    }

    private void showProductIngredientsCollectionInView(Collection<Ingredient> productIngredientsCollection) {
        this.detailProductView.renderProductIngredientsList(productIngredientsCollection);
    }

    private void showProductAllergenCollectionInView(Collection<Allergen> productIngredientsCollection) {
        this.detailProductView.renderProductAllergensList(productIngredientsCollection);
    }


    private final class DetailProductSubscriber extends DefaultSubscriber<List<Ingredient>> {

        @Override public void onCompleted() {
            DetailProductPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            DetailProductPresenter.this.hideViewLoading();
            ((Exception) e).printStackTrace();
            DetailProductPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            DetailProductPresenter.this.showViewRetry();
        }

        @Override public void onNext(List<Ingredient> productIngredients) {
            DetailProductPresenter.this.showProductIngredientsCollectionInView(productIngredients);
        }
    }

    private final class DetailProductAllergenSubscriber extends DefaultSubscriber<List<Allergen>> {

        @Override public void onCompleted() {
            DetailProductPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            DetailProductPresenter.this.hideViewLoading();
            ((Exception) e).printStackTrace();
            DetailProductPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            DetailProductPresenter.this.showViewRetry();
        }

        @Override public void onNext(List<Allergen> productAllergens) {
            DetailProductPresenter.this.showProductAllergenCollectionInView(productAllergens);
        }
    }
}
