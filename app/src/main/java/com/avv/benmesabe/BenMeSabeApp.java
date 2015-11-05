package com.avv.benmesabe;

import android.app.Application;

import com.avv.benmesabe.presentation.internal.di.components.BenMeSabeAppComponent;
import com.avv.benmesabe.presentation.internal.di.components.DaggerBenMeSabeAppComponent;
import com.avv.benmesabe.presentation.internal.di.modules.ApplicationModule;

/**
 * Created by angel on 19/08/2015.
 */
public class BenMeSabeApp extends Application {

    private BenMeSabeAppComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            //LayoutCast.init(this);
        }

        this.initializeInjector();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerBenMeSabeAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public BenMeSabeAppComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
