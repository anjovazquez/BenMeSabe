package com.avv.benmesabe.domain.executor;

import rx.Scheduler;

/**
 * Created by angel on 13/10/2015.
 */
public interface PostExecutionThread {
        Scheduler getScheduler();
}
