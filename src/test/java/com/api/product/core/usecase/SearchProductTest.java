package com.api.product.core.usecase;

import static com.api.product.core.usecase.fixture.SearchProductTestFixture.validResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.api.product.core.gateway.ProductGateway;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class SearchProductTest {

  private final ProductGateway productGateway = mock(ProductGateway.class);
  private final SearchProduct searchProduct = new SearchProduct(productGateway);

  @Test
  void shouldSearchProductBySkuSuccessfully() {
    final var sku = "BOLA-123-ABC";
    final var product = validResponse();

    when(productGateway.findBySku(sku)).thenReturn(Optional.of(product));

    final var response = searchProduct.execute(sku);

    assertThat(response).isPresent();
    assertThat(response.get()).usingRecursiveComparison().isEqualTo(product);

    verify(productGateway).findBySku(sku);
  }

  @Test
  void shouldReturnEmptyWhenProductNotFound() {
    final var sku = "BOLA-123-ABC";

    when(productGateway.findBySku(sku)).thenReturn(Optional.empty());

    final var response = searchProduct.execute(sku);

    assertThat(response).isEmpty();

    verify(productGateway).findBySku(sku);
  }
}
