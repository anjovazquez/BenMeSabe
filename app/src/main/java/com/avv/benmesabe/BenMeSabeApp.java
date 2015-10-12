package com.avv.benmesabe;

import android.app.Application;

import com.github.mmin18.layoutcast.LayoutCast;

/**
 * Created by angel on 19/08/2015.
 */
public class BenMeSabeApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            LayoutCast.init(this);
        }
    }
}
