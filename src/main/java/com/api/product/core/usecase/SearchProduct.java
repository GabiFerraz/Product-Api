package com.api.product.core.usecase;

import com.api.product.core.domain.Product;
import com.api.product.core.gateway.ProductGateway;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchProduct {

  private final ProductGateway productGateway;

  public Optional<Product> execute(final String sku) {
    return productGateway.findBySku(sku);
  }
}
