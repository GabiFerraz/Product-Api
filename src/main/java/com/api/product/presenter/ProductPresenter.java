package com.api.product.presenter;

import com.api.product.core.domain.Product;
import com.api.product.presenter.response.ProductPresenterResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductPresenter {

  public ProductPresenterResponse parseToResponse(final Product product) {
    return ProductPresenterResponse.builder()
        .id(Integer.parseInt(product.getId()))
        .name(product.getName())
        .sku(product.getSku())
        .price(product.getPrice())
        .build();
  }
}
