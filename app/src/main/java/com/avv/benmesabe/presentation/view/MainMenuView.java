package com.avv.benmesabe.presentation.view;

import com.avv.benmesabe.domain.CustomerRequest;
import com.avv.benmesabe.domain.Order;

/**
 * Created by angelvazquez on 18/10/15.
 */
public interface MainMenuView extends LoadDataView {
    void showOrderInView(Order order);
    void showCustomerRequestInView(CustomerRequest order);
}
