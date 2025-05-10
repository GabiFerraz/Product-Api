package com.api.product.core.usecase;

import static com.api.product.core.usecase.fixture.UpdateProductTestFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.api.product.core.domain.Product;
import com.api.product.core.gateway.ProductGateway;
import com.api.product.core.usecase.exception.ProductNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class UpdateProductTest {

  private final ProductGateway productGateway = mock(ProductGateway.class);
  private final UpdateProduct updateProduct = new UpdateProduct(productGateway);

  @Test
  void shouldUpdateClientSuccessfully() {
    final var sku = "BOLA-123-ABC";
    final var productFound = validProductFound();
    final ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
    final var productUpdated = validProductUpdated();
    final var request = validUpdateRequest();

    when(productGateway.findBySku(sku)).thenReturn(Optional.of(productFound));
    when(productGateway.update(productCaptor.capture())).thenReturn(productUpdated);

    final var response = updateProduct.execute(sku, request);

    assertThat(response).usingRecursiveComparison().isEqualTo(productUpdated);

    verify(productGateway).findBySku(sku);

    final var productCaptured = productCaptor.getValue();
    verify(productGateway).update(productCaptured);

    assertThat(productCaptured.getId()).isEqualTo(response.getId());
    assertThat(productCaptured.getName()).isEqualTo(response.getName());
    assertThat(productCaptured.getSku()).isEqualTo(response.getSku());
    assertThat(productCaptured.getPrice()).isEqualTo(response.getPrice());
  }

  @Test
  void shouldThrowExceptionWhenProductNotFound() {
    final var sku = "BOLA-123-ABC";
    final var request = validUpdateRequest();

    when(productGateway.findBySku(sku)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> updateProduct.execute(sku, request))
        .isInstanceOf(ProductNotFoundException.class)
        .hasMessage("Product with sku=[" + sku + "] not found.");

    verify(productGateway).findBySku(sku);
    verifyNoMoreInteractions(productGateway);
  }
}
