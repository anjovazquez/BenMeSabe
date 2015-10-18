package com.avv.benmesabe.presentation.internal.di.modules;

import android.app.Activity;

import com.avv.benmesabe.presentation.internal.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by angelvazquez on 18/10/15.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }
}
