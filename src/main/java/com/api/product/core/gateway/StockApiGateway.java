package com.api.product.core.gateway;

import com.api.product.core.gateway.response.StockDetailsResponse;

public interface StockApiGateway {

  StockDetailsResponse getStockDetails(final String sku);
}
