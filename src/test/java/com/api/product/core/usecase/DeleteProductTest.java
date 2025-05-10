package com.api.product.core.usecase;

import static com.api.product.core.usecase.fixture.DeleteProductTestFixture.validResponse;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.api.product.core.gateway.ProductGateway;
import com.api.product.core.usecase.exception.ProductNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class DeleteProductTest {

  private final ProductGateway productGateway = mock(ProductGateway.class);
  private final DeleteProduct deleteProduct = new DeleteProduct(productGateway);

  @Test
  void shouldDeleteProductSuccessfully() {
    final var sku = "BOLA-123-ABC";
    final var product = validResponse();

    when(productGateway.findBySku(sku)).thenReturn(Optional.of(product));
    doNothing().when(productGateway).deleteBySku(sku);

    deleteProduct.execute(sku);

    verify(productGateway).findBySku(sku);
    verify(productGateway).deleteBySku(sku);
  }

  @Test
  void shouldThrowExceptionWhenProductNotFound() {
    final var sku = "BOLA-123-ABC";

    when(productGateway.findBySku(sku)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> deleteProduct.execute(sku))
        .isInstanceOf(ProductNotFoundException.class)
        .hasMessage("Product with sku=[" + sku + "] not found.");

    verify(productGateway).findBySku(sku);
    verifyNoMoreInteractions(productGateway);
  }
}
