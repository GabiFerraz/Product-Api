package com.api.product.entrypoint.fixture;

import com.api.product.core.domain.Product;
import com.api.product.core.dto.ProductDto;
import com.api.product.core.dto.UpdateProductDto;
import java.math.BigDecimal;

public class ProductControllerTestFixture {

  private static final String ID = "1";
  private static final String NAME = "Bola de Futebol";
  private static final String SKU = "BOLA-123-ABC";
  private static final BigDecimal PRICE = BigDecimal.valueOf(10.0);
  private static final String NEW_NAME = "Bola de Futebol 2";
  private static final BigDecimal NEW_PRICE = BigDecimal.valueOf(20.0);

  public static ProductDto validRequest() {
    return new ProductDto(NAME, SKU, PRICE);
  }

  public static Product validResponse() {
    return new Product(ID, NAME, SKU, PRICE);
  }

  public static String expectedProductNotFoundBySkuResponse() {
    return """
            {
                "errorMessage": "Product with sku=[BOLA-123-ABC] not found."
            }
            """;
  }

  public static UpdateProductDto validUpdateRequest() {
    return new UpdateProductDto(NEW_NAME, NEW_PRICE);
  }

  public static Product validProductUpdated() {
    return new Product(ID, NEW_NAME, SKU, NEW_PRICE);
  }
}
