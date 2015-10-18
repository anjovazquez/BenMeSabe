package com.avv.benmesabe.presentation.internal.di.modules;

import android.content.Context;

import com.avv.benmesabe.BenMeSabeApp;
import com.avv.benmesabe.data.executor.JobExecutor;
import com.avv.benmesabe.data.repository.BenMeSabeDataRepository;
import com.avv.benmesabe.domain.executor.PostExecutionThread;
import com.avv.benmesabe.domain.executor.ThreadExecutor;
import com.avv.benmesabe.domain.repository.BenMeSabeRepository;
import com.avv.benmesabe.presentation.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by angelvazquez on 18/10/15.
 */
@Module
public class ApplicationModule {

    private final BenMeSabeApp application;

    public ApplicationModule(BenMeSabeApp application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides @Singleton
    BenMeSabeRepository provideBenMeSabeRepository(BenMeSabeDataRepository benMeSabeDataRepository) {
        return benMeSabeDataRepository;
    }
}
