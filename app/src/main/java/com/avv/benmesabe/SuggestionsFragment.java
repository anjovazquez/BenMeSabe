package com.avv.benmesabe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avv.benmesabe.domain.Product;
import com.avv.benmesabe.presentation.adapter.ProductAdapter;
import com.avv.benmesabe.presentation.internal.di.HasComponent;
import com.avv.benmesabe.presentation.internal.di.components.ProductComponent;
import com.avv.benmesabe.presentation.presenter.ProductListPresenter;
import com.avv.benmesabe.presentation.view.ProductListView;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by angelvazquez on 2/11/15.
 */
public class SuggestionsFragment extends Fragment implements ProductListView, ProductAdapter.OnProductItemClickListener {

    public static final String NAME = "SuggestionsFragment";

    @Bind(R.id.rv_products)
    RecyclerView rv_products;

    @Inject
    ProductListPresenter productListPresenter;

    private ProductAdapter productsAdapter;

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>)getActivity()).getComponent());
    }

    private void setupUI() {

        this.productsAdapter = new ProductAdapter(getActivity(), new ArrayList<Product>());
        this.productsAdapter.setOnProductItemClickListener(this);
        this.rv_products.setAdapter(productsAdapter);
    }

    public static SuggestionsFragment newInstance() {
        SuggestionsFragment fragment = new SuggestionsFragment();
        return fragment;
    }

    public SuggestionsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.suggestion_fragment, container, false);
        ButterKnife.bind(this, view);

        rv_products.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));

        this.getComponent(ProductComponent.class).inject(this);
        setupUI();
        if(productListPresenter!=null) {
            productListPresenter.setView(this);
            loadProductList();
        }

        return view;
    }



    /**
     * Loads all products.
     */
    private void loadProductList() {
        this.productListPresenter.initialize();
    }

    @Override
    public void renderProductList(Collection<Product> productModelCollection) {
        if (productModelCollection != null) {
            this.productsAdapter.setProductsCollection(productModelCollection);
        }
    }

    @Override
    public void showLoading() {

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
        return getActivity();
    }

    @Override
    public void onProductItemClicked(Product product) {
        Intent intentDetail = new Intent(getActivity(), DetailActivity.class);
        intentDetail.putExtra(DetailActivity.EXTRA_PRODUCT_ID, product.getProductId());
        intentDetail.putExtra(DetailActivity.EXTRA_PRODUCT_NAME, product.getProductName());
        intentDetail.putExtra(DetailActivity.EXTRA_PRODUCT_IMAGEURL, product.getImageURL());
        intentDetail.putExtra(DetailActivity.EXTRA_PRODUCT_DESC, product.getDescription());

        startActivity(intentDetail);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser) {
            ((BarcodeReaderActivity)getActivity()).setFloatingMenuOptions(SuggestionsFragment.NAME);
        }
    }
}
