package com.api.product.core.usecase.fixture;

import com.api.product.core.domain.Product;

public class DeleteProductTestFixture {

  private static final String ID = "1";
  private static final String NAME = "Bola de Futebol";
  private static final String SKU = "BOLA-123-ABC";
  private static final Double PRICE = 10.0;

  public static Product validResponse() {
    return new Product(ID, NAME, SKU, PRICE);
  }
}
