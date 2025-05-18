package com.api.product.presenter.response;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record ProductPresenterResponse(int id, String name, String sku, BigDecimal price) {}
