package com.avv.benmesabe;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avv.benmesabe.domain.Product;
import com.avv.benmesabe.domain.order.OrderManager;
import com.avv.benmesabe.presentation.adapter.ProductAdapter;
import com.avv.benmesabe.presentation.view.OrderListView;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by angelvazquez on 2/11/15.
 */
public class OrderFragment extends Fragment implements OrderListView {

    public static final String NAME = "OrderFragment";

    @Bind(R.id.rvOrderProducts)
    RecyclerView rvOrderProducts;

    private ProductAdapter productsAdapter;

    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }

    public OrderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_fragment, container, false);
        ButterKnife.bind(this, view);

        setupUI();
        loadProductOrderList();
        return view;
    }

    private void setupUI() {
        rvOrderProducts.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        this.productsAdapter = new ProductAdapter(getActivity(), new ArrayList<Product>());
        //this.productsAdapter.setOnProductItemClickListener(this);
        this.rvOrderProducts.setAdapter(productsAdapter);
    }



    /**
     * Loads all products.
     */
    private void loadProductOrderList() {
       // this.productListPresenter.initialize();
        if(OrderManager.getInstance().getProductOrderList()!=null) {
            productsAdapter.setProductsCollection(OrderManager.getInstance().getProductOrderList());
        }
    }

    @Override
    public void renderProductOrderList(Collection<Product> productOrderCollection) {
        if(OrderManager.getInstance().getProductOrderList()!=null) {
            productsAdapter.setProductsCollection(OrderManager.getInstance().getProductOrderList());
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser) {
            ((BarcodeReaderActivity)getActivity()).setFloatingMenuOptions(OrderFragment.NAME);
        }
    }
}
