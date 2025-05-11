package com.api.product.core.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.api.product.core.domain.exception.DomainException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductTest {

  @Test
  void shouldCreateProductSuccessfully() {
    final var product = Product.createProduct("Bola de Futebol", "BOLA-123-ABC", 10.0);

    assert product.getName().equals("Bola de Futebol");
    assert product.getSku().equals("BOLA-123-ABC");
    assert product.getPrice().equals(10.0);
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {" "})
  void shouldNotCreateProductWithInvalidName(final String name) {
    assertThatThrownBy(() -> Product.createProduct(name, "BOLA-123-ABC", 10.0))
        .isInstanceOf(DomainException.class)
        .hasMessage("Field=[name] should not be empty or null by domain product");
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"A1B2", "ABCDEFGHIJKLMNOPQRSTU", "abcdefg", "AB_CD56", "AB@CD5"})
  void shouldNotCreateProductWithInvalidSku(final String sku) {
    assertThatThrownBy(() -> Product.createProduct("Bola de Futebol", sku, 10.0))
        .isInstanceOf(DomainException.class)
        .hasMessage("The field=[sku] is null or has an invalid pattern by domain product");
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(doubles = {-0.01, -1.0, -100.0})
  void shouldNotCreateProductWithInvalidPrice(final Double price) {
    assertThatThrownBy(() -> Product.createProduct("Bola de Futebol", "BOLA-123-ABC", price))
        .isInstanceOf(DomainException.class)
        .hasMessageContaining("Field=[price]")
        .hasMessageContaining("by domain product");
  }
}
