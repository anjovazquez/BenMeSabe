package com.avv.benmesabe.data.repository.datasource;

import com.avv.benmesabe.data.entity.ProductEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit.Retrofit;
import retrofit.http.GET;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by angel on 12/10/2015.
 */
public class CloudDataStore implements BenMeSabeDataStore {

    private Retrofit retrofit;
    private BenMeSabeService benMeSabeService;

    public CloudDataStore(){
        Gson gson = new GsonBuilder().create();

        retrofit = new Retrofit.Builder().
                baseUrl("http://52.26.71.31:8080/RestMenus")
                .build();

        benMeSabeService = retrofit.create(BenMeSabeService.class);
    }

    public interface BenMeSabeService {
        @GET("/product")
        List<ProductEntity> listProduct();

        /*@GET("/allergen")
        Call<List<Allergen>> listAllergen();*/
    }

    public Observable<List<ProductEntity>> productEntityList(){
        return Observable.create(new Observable.OnSubscribe<List<ProductEntity>>(){
            @Override
            public void call(Subscriber<? super List<ProductEntity>> subscriber) {

                try{
                    benMeSabeService.listProduct();
                    List<ProductEntity> products = benMeSabeService.listProduct();
                    subscriber.onNext(products);
                    subscriber.onCompleted();
                }
                catch (Exception e){
                    e.printStackTrace();
                    subscriber.onError(new Exception(e.getMessage()));
                }
            }
        });
    }
}
