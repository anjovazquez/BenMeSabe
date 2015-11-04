package com.avv.benmesabe.presentation.internal.di.modules;

import com.avv.benmesabe.domain.executor.PostExecutionThread;
import com.avv.benmesabe.domain.executor.ThreadExecutor;
import com.avv.benmesabe.domain.interactor.GetProductAllergens;
import com.avv.benmesabe.domain.interactor.GetProductIngredients;
import com.avv.benmesabe.domain.interactor.GetProductList;
import com.avv.benmesabe.domain.interactor.UseCase;
import com.avv.benmesabe.domain.repository.BenMeSabeRepository;
import com.avv.benmesabe.presentation.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by angelvazquez on 18/10/15.
 */
@Module
public class ProductModule {

    private int productId = -1;

    public ProductModule() {}

    public ProductModule(int productId) {
        this.productId = productId;
    }

    @Provides
    @PerActivity @Named("productList")
    UseCase provideGetProductListUseCase(
            GetProductList getProductList) {
        return getProductList;
    }

    @Provides
    @PerActivity @Named("productIngredientsList")
    UseCase provideGetProductIngredientsListUseCase(BenMeSabeRepository benMeSabeRepository,
                                                    ThreadExecutor threadExecutor,
                                                    PostExecutionThread postExecutionThread) {
        return new GetProductIngredients(productId, benMeSabeRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerActivity @Named("productAllergensList")
    UseCase provideGetProductAllergensListUseCase(BenMeSabeRepository benMeSabeRepository,
                                                    ThreadExecutor threadExecutor,
                                                    PostExecutionThread postExecutionThread) {
        return new GetProductAllergens(productId, benMeSabeRepository, threadExecutor, postExecutionThread);
    }

    /*@Provides @PerActivity
    @Named("productDetails") UseCase provideGetProductDetailsUseCase(
            BenMeSabeRepository benMeSabeRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        //return new GetUserDetails(userId, userRepository, threadExecutor, postExecutionThread);
        return null;
    }*/
}
