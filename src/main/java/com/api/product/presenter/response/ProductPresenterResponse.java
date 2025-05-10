package com.api.product.presenter.response;

import lombok.Builder;

@Builder
public record ProductPresenterResponse(int id, String name, String sku, Double price) {}
