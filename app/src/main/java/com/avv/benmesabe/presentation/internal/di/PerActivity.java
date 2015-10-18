package com.avv.benmesabe.presentation.internal.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by angelvazquez on 18/10/15.
 */
@Scope
@Retention(RUNTIME)
public @interface PerActivity {}
