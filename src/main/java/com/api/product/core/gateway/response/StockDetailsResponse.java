package com.api.product.core.gateway.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class StockDetailsResponse {

  private final String productSku;
  private final Integer quantity;
}
