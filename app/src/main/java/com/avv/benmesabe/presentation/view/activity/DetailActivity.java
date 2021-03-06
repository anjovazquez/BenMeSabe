package com.avv.benmesabe.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.avv.benmesabe.R;
import com.avv.benmesabe.domain.Allergen;
import com.avv.benmesabe.domain.Ingredient;
import com.avv.benmesabe.domain.Product;
import com.avv.benmesabe.domain.order.OrderManager;
import com.avv.benmesabe.presentation.adapter.IngredientAdapter;
import com.avv.benmesabe.presentation.internal.di.HasComponent;
import com.avv.benmesabe.presentation.internal.di.components.DaggerProductComponent;
import com.avv.benmesabe.presentation.internal.di.components.ProductComponent;
import com.avv.benmesabe.presentation.internal.di.modules.ProductModule;
import com.avv.benmesabe.presentation.presenter.DetailProductPresenter;
import com.avv.benmesabe.presentation.view.DetailProductView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by angel on 30/07/2015.
 */
public class DetailActivity extends BaseActivity implements HasComponent<ProductComponent>, DetailProductView, IngredientAdapter.OnIngredientItemClickListener {

    public static final String EXTRA_PRODUCT_ID = "productId";

    private Product currentProduct;
    private int productId;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.ingredientList)
    RecyclerView rvProductIngredients;

    @Bind(R.id.addProduct)
    FloatingActionButton bAddProduct;

    @Inject
    DetailProductPresenter detailProductPresenter;

    private ProductComponent productComponent;
    private IngredientAdapter productIngredientsAdapter;

    private CollapsingToolbarLayout collapsingToolbar;

    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        Intent intent = getIntent();
        productId = intent.getIntExtra(EXTRA_PRODUCT_ID, 0);

        /*if (productURL == null){
            productId = intent.getIntExtra(EXTRA_PRODUCT_ID, 0);
            productName = intent.getStringExtra(EXTRA_PRODUCT_NAME);
            imageURL = intent.getStringExtra(EXTRA_PRODUCT_IMAGEURL);
            description = intent.getStringExtra(EXTRA_PRODUCT_DESC);
            //productDescription.setText(description);
            loadBackdrop();
            collapsingToolbar.setTitle(productName);
        }
        else{
            productId = Integer.parseInt(productURL.substring(productURL.indexOf("/")+1));
        }*/

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupUI();

        getApplicationComponent().inject(this);
        this.initializeInjector();
        productComponent.inject(this);

        detailProductPresenter.setView(this);
        detailProductPresenter.initialize();


        bAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderManager.getInstance().addProduct(currentProduct);
                finish();
            }
        });


       imageView = (ImageView) findViewById(R.id.backdrop);
    }

    @Override
    public ProductComponent getComponent() {
        return productComponent;
    }

    @Override
    public void onIngredientItemClicked(Ingredient ingredient) {

    }

    private void loadBackdrop() {
        Picasso.with(this).load(currentProduct.getImageURL()).into(imageView);
    }

    private void initializeInjector() {
        this.productComponent = DaggerProductComponent.builder()
                .benMeSabeAppComponent(getApplicationComponent())
                .activityModule(getActivityModule()).productModule(new ProductModule(productId))
                .build();
    }

    private void setupUI() {

        rvProductIngredients.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.productIngredientsAdapter = new IngredientAdapter(this, new ArrayList<Ingredient>());
        this.productIngredientsAdapter.setOnIngredientItemClickListener(this);
        this.rvProductIngredients.setAdapter(productIngredientsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sample_actions, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void renderProductIngredientsList(Collection<Ingredient> productIngredientsModelCollection) {
        //Toast.makeText(this, "Ingredientes del produto "+productIngredientsModelCollection, Toast.LENGTH_LONG).show();
        if (productIngredientsModelCollection != null) {
            this.productIngredientsAdapter.setIngredientsCollection(productIngredientsModelCollection);
        }
    }

    @Override
    public void renderProductAllergensList(Collection<Allergen> productAllergensModelCollection) {

        View view = this.findViewById(android.R.id.content);
        if (productAllergensModelCollection != null) {
            //Toast.makeText(this, "Alérgenos del produto "+productAllergensModelCollection, Toast.LENGTH_LONG).show();
            for(Allergen allergen:productAllergensModelCollection){
                if(allergen.getAllergenId()!=null){
                   view.findViewWithTag("al"+ allergen.getAllergenId()).setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void renderProductDetail(Product product) {
        this.currentProduct = product;
        loadBackdrop();
        collapsingToolbar.setTitle(product.getProductName());
    }

    private void hideAllergenIcons(){
        View view = this.findViewById(android.R.id.content);
        for(int i=1;i<15;i++){
            view.findViewWithTag("al"+i).setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoading() {
        hideAllergenIcons();

    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context getContext() {
        return this;
    }
}
