package com.avv.benmesabe.presentation.internal.di.components;

import android.content.Context;

import com.avv.benmesabe.domain.executor.PostExecutionThread;
import com.avv.benmesabe.domain.executor.ThreadExecutor;
import com.avv.benmesabe.domain.repository.BenMeSabeRepository;
import com.avv.benmesabe.presentation.view.activity.BaseActivity;
import com.avv.benmesabe.presentation.internal.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by angelvazquez on 18/10/15.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface BenMeSabeAppComponent {

    void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs.
    Context context();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
    BenMeSabeRepository benMeSabeRepository();
}
