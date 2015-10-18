package com.avv.benmesabe.presentation.view;

import com.avv.benmesabe.domain.Product;

import java.util.Collection;

/**
 * Created by angelvazquez on 18/10/15.
 */
public interface ProductListView extends LoadDataView {

    /**
     * Render a product list in the UI.
     *
     * @param productModelCollection The collection of {@link com.avv.benmesabe.domain.Product} that will be shown.
     */
    void renderProductList(Collection<Product> productModelCollection);
}
