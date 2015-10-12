package com.avv.benmesabe.data.repository.datasource;

import android.content.Context;

import javax.inject.Singleton;

/**
 * Created by angel on 12/10/2015.
 */
@Singleton
public class BenMeSabeDataStoreFactory {

    private final Context context;


    public BenMeSabeDataStoreFactory(Context context){
        this.context = context.getApplicationContext();

    }

    public BenMeSabeDataStore createCloudDataStore() {
        return new CloudDataStore();
    }
}
