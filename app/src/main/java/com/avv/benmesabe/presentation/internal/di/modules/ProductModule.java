package com.avv.benmesabe.presentation.internal.di.modules;

import com.avv.benmesabe.domain.interactor.GetProductList;
import com.avv.benmesabe.domain.interactor.UseCase;
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

    /*@Provides @PerActivity
    @Named("productDetails") UseCase provideGetProductDetailsUseCase(
            BenMeSabeRepository benMeSabeRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        //return new GetUserDetails(userId, userRepository, threadExecutor, postExecutionThread);
        return null;
    }*/
}
