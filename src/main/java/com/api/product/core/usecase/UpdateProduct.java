package com.api.product.core.usecase;

import com.api.product.core.domain.Product;
import com.api.product.core.dto.UpdateProductDto;
import com.api.product.core.gateway.ProductGateway;
import com.api.product.core.usecase.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateProduct {

  private final ProductGateway productGateway;

  public Product execute(final String sku, final UpdateProductDto request) {
    final var existingProduct =
        this.productGateway.findBySku(sku).orElseThrow(() -> new ProductNotFoundException(sku));

    if (request.name() != null) existingProduct.setName(request.name());
    if (request.price() != null) existingProduct.setPrice(request.price());

    return this.productGateway.update(existingProduct);
  }
}
