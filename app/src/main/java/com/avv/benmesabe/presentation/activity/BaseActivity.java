package com.avv.benmesabe.presentation.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.avv.benmesabe.BenMeSabeApp;
import com.avv.benmesabe.presentation.internal.di.components.BenMeSabeAppComponent;
import com.avv.benmesabe.presentation.internal.di.modules.ActivityModule;

/**
 * Created by angelvazquez on 18/10/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    protected BenMeSabeAppComponent getApplicationComponent() {
        return ((BenMeSabeApp)getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
