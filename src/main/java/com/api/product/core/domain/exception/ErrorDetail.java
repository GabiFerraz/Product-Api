package com.api.product.core.domain.exception;

public record ErrorDetail(String field, String errorMessage, Object rejectedValue) {}
