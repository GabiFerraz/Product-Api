package com.api.product.core.usecase;

import static com.api.product.core.usecase.fixture.CreateProductTestFixture.validRequest;
import static com.api.product.core.usecase.fixture.CreateProductTestFixture.validResponse;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.api.product.core.domain.Product;
import com.api.product.core.gateway.ProductGateway;
import com.api.product.core.usecase.exception.ProductAlreadyExistsException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class CreateProductTest {

  private final ProductGateway productGateway = mock(ProductGateway.class);
  private final CreateProduct createProduct = new CreateProduct(productGateway);

  @Test
  void shouldCreateProductSuccessfully() {
    final var request = validRequest();
    final var gatewayResponse = validResponse();
    final ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);

    when(productGateway.findBySku(any())).thenReturn(empty());
    when(productGateway.save(productCaptor.capture())).thenReturn(gatewayResponse);

    final var response = this.createProduct.execute(request);

    assertThat(response).usingRecursiveComparison().isEqualTo(gatewayResponse);

    verify(productGateway).findBySku(any());

    final var productCaptured = productCaptor.getValue();
    verify(productGateway).save(productCaptured);

    assertThat(productCaptured.getName()).isEqualTo(request.name());
    assertThat(productCaptured.getSku()).isEqualTo(request.sku());
    assertThat(productCaptured.getPrice()).isEqualTo(request.price());
  }

  @Test
  void shouldNotCreateProductWithExistingSku() {
    final var request = validRequest();
    final var gatewayResponse = validResponse();

    when(productGateway.findBySku(request.sku())).thenReturn(Optional.of(gatewayResponse));

    assertThatThrownBy(() -> this.createProduct.execute(request))
        .isInstanceOf(ProductAlreadyExistsException.class)
        .hasMessage("Product with sku=[" + request.sku() + "] already exists.");

    verify(productGateway).findBySku(request.sku());
    verifyNoMoreInteractions(productGateway);
  }
}
