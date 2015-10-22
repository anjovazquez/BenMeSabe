package com.avv.benmesabe.data.repository.datasource;

import com.avv.benmesabe.data.entity.ProductEntity;

import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
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
        retrofit = new Retrofit.Builder().
                baseUrl("http://52.26.71.31:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        benMeSabeService = retrofit.create(BenMeSabeService.class);
    }

    public interface BenMeSabeService {
        @GET("/RestMenus/product")
        Call<List<ProductEntity>> listProduct();

        /*@GET("/allergen")
        Call<List<Allergen>> listAllergen();*/
    }

    public Observable<List<ProductEntity>> productEntityList(){
        return Observable.create(new Observable.OnSubscribe<List<ProductEntity>>(){
            @Override
            public void call(Subscriber<? super List<ProductEntity>> subscriber) {

                try{
                    Call<List<ProductEntity>> productsCall = benMeSabeService.listProduct();
                    Response<List<ProductEntity>> productsResponse = productsCall.execute();
                    subscriber.onNext(productsResponse.body());
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
