package com.api.product.presenter.response;

import lombok.Builder;

@Builder
public record ErrorPresenterResponse(String errorMessage) {}
