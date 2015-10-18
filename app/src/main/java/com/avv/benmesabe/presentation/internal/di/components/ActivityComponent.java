package com.avv.benmesabe.presentation.internal.di.components;

import android.app.Activity;

import com.avv.benmesabe.presentation.internal.di.PerActivity;
import com.avv.benmesabe.presentation.internal.di.modules.ActivityModule;

import dagger.Component;

/**
 * Created by angelvazquez on 18/10/15.
 */
@PerActivity
@Component(dependencies = BenMeSabeAppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    //Exposed to sub-graphs.
    Activity activity();
}
