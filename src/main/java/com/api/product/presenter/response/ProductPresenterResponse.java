package com.api.product.presenter.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductPresenterResponse(int id, String name, String sku, BigDecimal price) {}
