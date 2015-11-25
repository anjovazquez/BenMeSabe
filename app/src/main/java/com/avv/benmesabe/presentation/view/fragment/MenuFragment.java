package com.avv.benmesabe.presentation.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avv.benmesabe.R;
import com.avv.benmesabe.domain.Product;
import com.avv.benmesabe.presentation.adapter.CategoryProductAdapter;
import com.avv.benmesabe.presentation.adapter.UltimateProductAdapter;
import com.avv.benmesabe.presentation.internal.di.HasComponent;
import com.avv.benmesabe.presentation.internal.di.components.ProductComponent;
import com.avv.benmesabe.presentation.presenter.ProductMenuListPresenter;
import com.avv.benmesabe.presentation.view.ProductListView;
import com.avv.benmesabe.presentation.view.activity.BarcodeReaderActivity;
import com.avv.benmesabe.presentation.view.activity.DetailActivity;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.itemTouchHelper.SimpleItemTouchHelperCallback;
import com.marshalchen.ultimaterecyclerview.ui.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by angelvazquez on 2/11/15.
 */
public class MenuFragment extends Fragment implements ProductListView, CategoryProductAdapter.OnProductItemClickListener {

    public static final String NAME = "MenuFragment";

    @Bind(R.id.rv_products_menu)
    UltimateRecyclerView rv_products_menu;

    private UltimateProductAdapter productsAdapter;

    @Inject
    ProductMenuListPresenter productListPresenter;

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    //private StepPagerStrip mStepPagerStrip;

    public static MenuFragment newInstance() {
        MenuFragment fragment = new MenuFragment();
        return fragment;
    }

    public MenuFragment() {
    }

    private void setupUI() {

        rv_products_menu.setHasFixedSize(true);
        rv_products_menu.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));
        rv_products_menu.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));


        this.productsAdapter = new UltimateProductAdapter(getActivity(), new ArrayList<Product>());
        //this.productsAdapter.setOnProductItemClickListener(this);
        this.rv_products_menu.setAdapter(productsAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(productsAdapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(rv_products_menu.mRecyclerView);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment, container, false);
        /*mStepPagerStrip = (StepPagerStrip) view.findViewById(R.id.strip);
        mStepPagerStrip.setPageCount(4);
        mStepPagerStrip.setCurrentPage(2);*/

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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser) {
            ((BarcodeReaderActivity)getActivity()).setFloatingMenuOptions(MenuFragment.NAME);
        }
    }


    private CategoryProductAdapter simpleRecyclerViewAdapter = null;

    @Override
    public void renderProductList(Collection<Product> productModelCollection) {
        this.productsAdapter.setProductsCollection(productModelCollection);
        rv_products_menu.setAdapter(productsAdapter);

        simpleRecyclerViewAdapter = new CategoryProductAdapter(getActivity());
        simpleRecyclerViewAdapter.setProductsCollection(productModelCollection);
        this.simpleRecyclerViewAdapter.setOnProductItemClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv_products_menu.setLayoutManager(linearLayoutManager);
        rv_products_menu.setAdapter(simpleRecyclerViewAdapter);
        rv_products_menu.setRecylerViewBackgroundColor(Color.parseColor("#ffffff"));
        addExpandableFeatures();
    }

    private void addExpandableFeatures() {
        rv_products_menu.getItemAnimator().setAddDuration(100);
        rv_products_menu.getItemAnimator().setRemoveDuration(100);
        rv_products_menu.getItemAnimator().setMoveDuration(200);
        rv_products_menu.getItemAnimator().setChangeDuration(100);
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
    public void onProductItemClicked(Product product) {
        Intent intentDetail = new Intent(getActivity(), DetailActivity.class);
        intentDetail.putExtra(DetailActivity.EXTRA_PRODUCT_ID, product.getProductId());
        intentDetail.putExtra(DetailActivity.EXTRA_PRODUCT_NAME, product.getProductName());
        intentDetail.putExtra(DetailActivity.EXTRA_PRODUCT_IMAGEURL, product.getImageURL());
        intentDetail.putExtra(DetailActivity.EXTRA_PRODUCT_DESC, product.getDescription());

        getActivity().startActivity(intentDetail, ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity()).toBundle());
    }
}
