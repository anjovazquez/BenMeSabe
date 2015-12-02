package com.avv.benmesabe.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avv.benmesabe.R;
import com.avv.benmesabe.domain.Product;
import com.avv.benmesabe.domain.order.OrderManager;
import com.avv.benmesabe.presentation.adapter.UltimateProductAdapter;
import com.avv.benmesabe.presentation.view.OrderListView;
import com.avv.benmesabe.presentation.view.activity.BarcodeReaderActivity;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.animators.SlideInLeftAnimator;
import com.marshalchen.ultimaterecyclerview.itemTouchHelper.SimpleItemTouchHelperCallback;

import org.rocko.bpb.BounceProgressBar;

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
    UltimateRecyclerView rvOrderProducts;

    @Bind(R.id.progress)
    BounceProgressBar progress;

    private UltimateProductAdapter productsAdapter;

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

        rvOrderProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.productsAdapter = new UltimateProductAdapter(getActivity(), new ArrayList<Product>());
        //StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(productsAdapter);
        //rvOrderProducts.addItemDecoration(headersDecor);
        this.rvOrderProducts.setAdapter(productsAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(productsAdapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(rvOrderProducts.mRecyclerView);

        rvOrderProducts.setItemAnimator(new SlideInLeftAnimator());
        rvOrderProducts.getItemAnimator().setAddDuration(400);
        rvOrderProducts.getItemAnimator().setRemoveDuration(400);
    }



    /**
     * Loads all products.
     */
    private void loadProductOrderList() {
       // this.productListPresenter.initialize();
        showLoading();
        if(OrderManager.getInstance().getProductOrderList()!=null) {
            productsAdapter.setProductsCollection(OrderManager.getInstance().getProductOrderList());
        }
        hideLoading();
    }

    @Override
    public void renderProductOrderList(Collection<Product> productOrderCollection) {
        if(OrderManager.getInstance().getProductOrderList()!=null) {
            productsAdapter.setProductsCollection(OrderManager.getInstance().getProductOrderList());
        }
    }

    @Override
    public void showLoading() {
        progress.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoading() {
        progress.setVisibility(View.GONE);

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
