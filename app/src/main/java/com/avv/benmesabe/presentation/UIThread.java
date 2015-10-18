package com.avv.benmesabe.presentation;

import com.avv.benmesabe.domain.executor.PostExecutionThread;

import javax.inject.Inject;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by angelvazquez on 18/10/15.
 */
public class UIThread implements PostExecutionThread{

    @Inject
    public UIThread() {}

    @Override public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
