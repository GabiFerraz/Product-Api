package com.api.product.core.usecase;

import com.api.product.core.gateway.ProductGateway;
import com.api.product.core.usecase.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeleteProduct {

  private final ProductGateway productGateway;

  @Transactional
  public void execute(final String sku) {
    this.productGateway.findBySku(sku).orElseThrow(() -> new ProductNotFoundException(sku));
    // TODO: verificar se tem estoque, se tiver, não deixar deletar
    this.productGateway.deleteBySku(sku);
  }
}
