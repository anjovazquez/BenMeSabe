package com.avv.benmesabe.presentation.internal.di.components;

import com.avv.benmesabe.BarcodeReaderActivity;
import com.avv.benmesabe.DetailActivity;
import com.avv.benmesabe.SuggestionsFragment;
import com.avv.benmesabe.presentation.internal.di.PerActivity;
import com.avv.benmesabe.presentation.internal.di.modules.ActivityModule;
import com.avv.benmesabe.presentation.internal.di.modules.ProductModule;

import dagger.Component;

/**
 * Created by angelvazquez on 18/10/15.
 */
@PerActivity
@Component(dependencies = BenMeSabeAppComponent.class, modules = {ActivityModule.class, ProductModule.class})
public interface ProductComponent extends ActivityComponent {
    //void inject(BaseActivity baseActivity);
    void inject(SuggestionsFragment suggestionsFragment);
    void inject(DetailActivity detailActivity);
    void inject(BarcodeReaderActivity barcodeReaderActivity);
}
