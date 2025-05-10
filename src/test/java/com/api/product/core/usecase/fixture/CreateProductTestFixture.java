package com.api.product.core.usecase.fixture;

import com.api.product.core.domain.Product;
import com.api.product.core.dto.ProductDto;

public class CreateProductTestFixture {

  private static final String ID = "1";
  private static final String NAME = "Bola de Futebol";
  private static final String SKU = "BOLA-123-ABC";
  private static final Double PRICE = 10.0;

  public static ProductDto validRequest() {
    return new ProductDto(NAME, SKU, PRICE);
  }

  public static Product validResponse() {
    return new Product(ID, NAME, SKU, PRICE);
  }
}
