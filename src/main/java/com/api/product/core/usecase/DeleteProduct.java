package com.api.product.core.usecase;

import com.api.product.core.gateway.ProductGateway;
import com.api.product.core.gateway.StockApiGateway;
import com.api.product.core.usecase.exception.ProductCannotBeDeletedException;
import com.api.product.core.usecase.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeleteProduct {

  private final ProductGateway productGateway;
  private final StockApiGateway stockApiGateway;

  @Transactional
  public void execute(final String sku) {
    this.productGateway.findBySku(sku).orElseThrow(() -> new ProductNotFoundException(sku));

    final var stock = this.stockApiGateway.getStockDetails(sku);

    if (stock.getQuantity() > 0) {
      throw new ProductCannotBeDeletedException(sku);
    }

    this.productGateway.deleteBySku(sku);
  }
}
