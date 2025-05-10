package com.api.product.core.usecase.exception;

import static java.lang.String.format;

public class ProductCannotBeDeletedException extends BusinessException {

  private static final String ERROR_CODE = "PRODUCT_HAS_STOCK";
  private static final String MESSAGE = "Product with sku=[%s] has stock and cannot be deleted.";

  public ProductCannotBeDeletedException(final String sku) {
    super(format(MESSAGE, sku), ERROR_CODE);
  }
}
