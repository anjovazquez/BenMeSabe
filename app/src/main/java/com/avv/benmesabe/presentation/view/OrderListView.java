package com.avv.benmesabe.presentation.view;

import com.avv.benmesabe.domain.Product;

import java.util.Collection;

/**
 * Created by angelvazquez on 18/10/15.
 */
public interface OrderListView extends LoadDataView {

    void renderProductOrderList(Collection<Product> productOrderCollection);

}
