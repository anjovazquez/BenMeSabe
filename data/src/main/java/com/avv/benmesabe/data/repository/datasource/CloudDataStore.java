package com.avv.benmesabe.data.repository.datasource;

import java.util.List;

import entity.ProductEntity;
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
        retrofit = new Retrofit.Builder().baseUrl("").build();
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
                    List<ProductEntity> products = benMeSabeService.listProduct();
                    subscriber.onNext(products);
                    subscriber.onCompleted();
                }
                catch (Exception e){
                    subscriber.onError(new Exception());
                }
            }
        });
    }
}
