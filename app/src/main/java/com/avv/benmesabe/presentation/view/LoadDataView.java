package com.avv.benmesabe.presentation.view;

import android.content.Context;

/**
 * Created by angelvazquez on 18/10/15.
 */
public interface LoadDataView {

    void showLoading();

    void hideLoading();

    void showRetry();

    void hideRetry();

    void showError(String message);

    Context getContext();
}
