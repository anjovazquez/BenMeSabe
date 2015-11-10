package com.avv.benmesabe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avv.benmesabe.domain.Product;
import com.avv.benmesabe.presentation.adapter.ProductAdapter;
import com.avv.benmesabe.presentation.adapter.ProductSectionAdapter;
import com.avv.benmesabe.presentation.internal.di.HasComponent;
import com.avv.benmesabe.presentation.internal.di.components.ProductComponent;
import com.avv.benmesabe.presentation.presenter.ProductListPresenter;
import com.avv.benmesabe.presentation.view.ProductListView;
import com.marshalchen.ultimaterecyclerview.ui.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    private void setupUI() {

        rv_products.setHasFixedSize(true);
        //rv_products.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));
        rv_products.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_products.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));


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


        this.getComponent(ProductComponent.class).inject(this);
        setupUI();
        if (productListPresenter != null) {
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

            //This is the code to provide a sectioned list
            List<ProductSectionAdapter.Section> sections =
                    new ArrayList<ProductSectionAdapter.Section>();

            //Sections
            sections.add(new ProductSectionAdapter.Section(0, "Section 1"));
            sections.add(new ProductSectionAdapter.Section(5, "Section 2"));
            sections.add(new ProductSectionAdapter.Section(12, "Section 3"));
            //sections.add(new ProductSectionAdapter.Section(14, "Section 4"));
            //sections.add(new ProductSectionAdapter.Section(20, "Section 5"));

            this.productsAdapter.setProductsCollection(productModelCollection);

            ProductSectionAdapter.Section[] dummy = new ProductSectionAdapter.Section[sections.size()];
            ProductSectionAdapter mSectionedAdapter = new
                    ProductSectionAdapter(getActivity(), R.layout.section, R.id.section_text, productsAdapter);
            mSectionedAdapter.setSections(sections.toArray(dummy));

            //Apply this adapter to the RecyclerView
            rv_products.setAdapter(mSectionedAdapter);


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

        if (isVisibleToUser) {
            ((BarcodeReaderActivity) getActivity()).setFloatingMenuOptions(SuggestionsFragment.NAME);
        }
    }
}
