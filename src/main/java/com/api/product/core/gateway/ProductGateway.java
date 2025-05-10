package com.api.product.core.gateway;

import com.api.product.core.domain.Product;
import java.util.Optional;

public interface ProductGateway {

  Product save(final Product product);

  Optional<Product> findBySku(final String sku);

  Product update(final Product product);

  void deleteBySku(final String sku);
}
