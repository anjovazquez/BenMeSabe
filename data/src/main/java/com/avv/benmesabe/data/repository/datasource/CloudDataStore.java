package com.avv.benmesabe.data.repository.datasource;

import com.avv.benmesabe.data.entity.AllergenEntity;
import com.avv.benmesabe.data.entity.IngredientEntity;
import com.avv.benmesabe.data.entity.ProductEntity;
import com.avv.benmesabe.domain.Order;

import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
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

        @GET("/RestMenus/product/{productId}")
        Call<List<ProductEntity>> getProduct(@Path("productId") Number productId);

        @GET("/RestMenus/product/{productId}/ingredient")
        Call<List<IngredientEntity>> listProductIngredients(@Path("productId") Number productId);

        @GET("/RestMenus/product/{productId}/allergen")
        Call<List<AllergenEntity>> listProductAllergens(@Path("productId") Number productId);

        @POST("/RestMenus/order")
        Call<Order> postOrder(@Body Order order);
    }

    public Observable<Order> postOrder(final Order order){
        return Observable.create(new Observable.OnSubscribe<Order>() {
            @Override
            public void call(Subscriber<? super Order> subscriber) {
                try{
                    Call<Order> orderCall = benMeSabeService.postOrder(order);
                    Response<Order> orderResponse = orderCall.execute();
                    subscriber.onNext(orderResponse.body());
                    subscriber.onCompleted();
                }
                catch (Exception e){
                    e.printStackTrace();
                    subscriber.onError(new Exception(e.getMessage()));
                }
            }
        });

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


    @Override
    public Observable<List<IngredientEntity>> getProductIngredients(final Number productId) {
        return Observable.create(new Observable.OnSubscribe<List<IngredientEntity>>(){
            @Override
            public void call(Subscriber<? super List<IngredientEntity>> subscriber) {

                try{
                    Call<List<IngredientEntity>> productIngredientsCall = benMeSabeService.listProductIngredients(productId);
                    Response<List<IngredientEntity>> ingredientsResponse = productIngredientsCall.execute();
                    subscriber.onNext(ingredientsResponse.body());
                    subscriber.onCompleted();
                }
                catch (Exception e){
                    e.printStackTrace();
                    subscriber.onError(new Exception(e.getMessage()));
                }
            }
        });
    }

    @Override
    public Observable<List<AllergenEntity>> getProductAllergens(final Number productId) {
        return Observable.create(new Observable.OnSubscribe<List<AllergenEntity>>(){
            @Override
            public void call(Subscriber<? super List<AllergenEntity>> subscriber) {

                try{
                    Call<List<AllergenEntity>> productAllergensCAll = benMeSabeService.listProductAllergens(productId);
                    Response<List<AllergenEntity>> allergensResponse = productAllergensCAll.execute();
                    subscriber.onNext(allergensResponse.body());
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
