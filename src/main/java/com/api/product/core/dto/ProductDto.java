package com.api.product.core.dto;

import java.math.BigDecimal;

public record ProductDto(String name, String sku, BigDecimal price) {}
