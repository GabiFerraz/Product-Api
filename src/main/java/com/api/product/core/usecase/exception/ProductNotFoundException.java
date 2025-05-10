package com.api.product.core.usecase.exception;

import static java.lang.String.format;

public class ProductNotFoundException extends BusinessException {

  private static final String ERROR_CODE = "NOT_FOUND";
  private static final String MESSAGE = "Product with sku=[%s] not found.";

  public ProductNotFoundException(final String sku) {
    super(format(MESSAGE, sku), ERROR_CODE);
  }
}
