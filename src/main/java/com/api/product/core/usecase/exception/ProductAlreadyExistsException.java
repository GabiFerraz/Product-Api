package com.api.product.core.usecase.exception;

import static java.lang.String.format;

public class ProductAlreadyExistsException extends BusinessException {

  private static final String ERROR_CODE = "ALREADY_EXISTS";
  private static final String MESSAGE = "Product with sku=[%s] already exists.";

  public ProductAlreadyExistsException(final String sku) {
    super(format(MESSAGE, sku), ERROR_CODE);
  }
}
