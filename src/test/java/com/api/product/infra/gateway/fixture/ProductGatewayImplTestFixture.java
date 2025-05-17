package com.api.product.infra.gateway.fixture;

import com.api.product.core.domain.Product;
import com.api.product.infra.persistence.entity.ProductEntity;

import java.math.BigDecimal;

public class ProductGatewayImplTestFixture {

  private static final String ID = "1";
  private static final String NAME = "Bola de Futebol";
  private static final String SKU = "BOLA-123-ABC";
  private static final BigDecimal PRICE = BigDecimal.valueOf(10.0);
  private static final String NEW_NAME = "Bola de Futebol 2";
  private static final BigDecimal NEW_PRICE = BigDecimal.valueOf(20.0);

  public static ProductEntity productEntity() {
    return ProductEntity.builder().name(NAME).sku(SKU).price(PRICE).build();
  }

  public static ProductEntity productEntityResponse() {
    return ProductEntity.builder().id(Integer.valueOf(ID)).name(NAME).sku(SKU).price(PRICE).build();
  }

  public static Product productDomain() {
    return new Product(null, NAME, SKU, PRICE);
  }

  public static ProductEntity productEntityUpdate() {
    return ProductEntity.builder()
        .id(Integer.valueOf(ID))
        .name(NEW_NAME)
        .sku(SKU)
        .price(NEW_PRICE)
        .build();
  }

  public static Product productDomainUpdate() {
    return new Product(ID, NEW_NAME, SKU, NEW_PRICE);
  }
}
