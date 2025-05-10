package com.api.product.core.usecase;

import com.api.product.core.domain.Product;
import com.api.product.core.dto.ProductDto;
import com.api.product.core.gateway.ProductGateway;
import com.api.product.core.usecase.exception.ProductAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateProduct {

  private final ProductGateway productGateway;

  public Product execute(final ProductDto request) {
    final var product = this.productGateway.findBySku(request.sku());

    if (product.isPresent()) {
      throw new ProductAlreadyExistsException(request.sku());
    }

    final var buildDomain = Product.createProduct(request.name(), request.sku(), request.price());

    return this.productGateway.save(buildDomain);
  }
}
