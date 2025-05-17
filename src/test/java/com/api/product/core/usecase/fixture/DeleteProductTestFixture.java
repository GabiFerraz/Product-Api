package com.api.product.core.usecase.fixture;

import com.api.product.core.domain.Product;
import com.api.product.core.gateway.response.StockDetailsResponse;

import java.math.BigDecimal;

public class DeleteProductTestFixture {

  private static final String ID = "1";
  private static final String NAME = "Bola de Futebol";
  private static final String SKU = "BOLA-123-ABC";
  private static final BigDecimal PRICE = BigDecimal.valueOf(10.0);

  public static Product validResponse() {
    return new Product(ID, NAME, SKU, PRICE);
  }

  public static StockDetailsResponse validStockResponse(final int quantity) {
    return new StockDetailsResponse(SKU, quantity);
  }
}
