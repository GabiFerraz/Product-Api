package com.api.product.infra.gateway;

import static com.api.product.infra.gateway.fixture.ProductGatewayImplTestFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.api.product.infra.gateway.exception.GatewayException;
import com.api.product.infra.persistence.entity.ProductEntity;
import com.api.product.infra.persistence.repository.ProductRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class ProductGatewayImplTest {

  private final ProductRepository productRepository = mock(ProductRepository.class);
  private final ProductGatewayImpl productGateway = new ProductGatewayImpl(productRepository);

  @Test
  void shouldSaveProductSuccessfully() {
    final var entity = productEntity();
    final var entityResponse = productEntityResponse();
    final var product = productDomain();
    final ArgumentCaptor<ProductEntity> entityCaptor = ArgumentCaptor.forClass(ProductEntity.class);

    when(productRepository.save(entityCaptor.capture())).thenReturn(entityResponse);

    final var response = productGateway.save(product);

    assertThat(response.getId()).isEqualTo(entityResponse.getId().toString());
    assertThat(response.getName()).isEqualTo(entityResponse.getName());
    assertThat(response.getSku()).isEqualTo(entityResponse.getSku());
    assertThat(response.getPrice()).isEqualTo(entityResponse.getPrice());

    final var entityCaptured = entityCaptor.getValue();
    verify(productRepository).save(entityCaptured);

    assertThat(entityCaptured.getId()).isNull();
    assertThat(entityCaptured.getName()).isEqualTo(entity.getName());
    assertThat(entityCaptured.getSku()).isEqualTo(entity.getSku());
    assertThat(entityCaptured.getPrice()).isEqualTo(entity.getPrice());
  }

  @Test
  void shouldThrowExceptionWhenOccursErrorSavingProduct() {
    final var product = productDomain();

    when(productRepository.save(any())).thenThrow(IllegalArgumentException.class);

    assertThatThrownBy(() -> productGateway.save(product))
        .isInstanceOf(GatewayException.class)
        .hasMessage("Error saving product with sku=[" + product.getSku() + "].");

    verify(productRepository).save(any());
  }

  @Test
  void shouldFindProductSuccessfully() {
    final var sku = "BOLA-123-ABC";
    final var entity = productEntityResponse();

    when(productRepository.findBySku(sku)).thenReturn(Optional.of(entity));

    final var response = productGateway.findBySku(sku);

    assertThat(response).isPresent();
    assertThat(response.get().getId()).isEqualTo(entity.getId().toString());
    assertThat(response.get().getName()).isEqualTo(entity.getName());
    assertThat(response.get().getSku()).isEqualTo(entity.getSku());
    assertThat(response.get().getPrice()).isEqualTo(entity.getPrice());

    verify(productRepository).findBySku(sku);
  }

  @Test
  void shouldReturnEmptyWhenProductNotFound() {
    final var sku = "BOLA-123-ABC";

    when(productRepository.findBySku(sku)).thenReturn(Optional.empty());

    final var response = productGateway.findBySku(sku);

    assertThat(response).isEmpty();

    verify(productRepository).findBySku(sku);
  }

  @Test
  void shouldThrowExceptionWhenOccursErrorFindingProduct() {
    final var sku = "BOLA-123-ABC";

    when(productRepository.findBySku(sku)).thenThrow(IllegalArgumentException.class);

    assertThatThrownBy(() -> productGateway.findBySku(sku))
        .isInstanceOf(GatewayException.class)
        .hasMessage("Product with sku=[" + sku + "] not found.");

    verify(productRepository).findBySku(sku);
  }

  @Test
  void shouldUpdateProductSuccessfully() {
    final var entityFound = productEntityResponse();
    final var entityUpdated = productEntityUpdate();
    final var productUpdate = productDomainUpdate();
    final ArgumentCaptor<ProductEntity> entityCaptor = ArgumentCaptor.forClass(ProductEntity.class);

    doReturn(Optional.of(entityFound)).when(productRepository).findBySku(productUpdate.getSku());
    when(productRepository.save(entityCaptor.capture())).thenReturn(entityUpdated);

    final var response = productGateway.update(productUpdate);

    assertThat(response.getId()).isEqualTo(entityUpdated.getId().toString());
    assertThat(response.getName()).isEqualTo(entityUpdated.getName());
    assertThat(response.getSku()).isEqualTo(entityUpdated.getSku());
    assertThat(response.getPrice()).isEqualTo(entityUpdated.getPrice());

    verify(productRepository).findBySku(productUpdate.getSku());

    final var entityCaptured = entityCaptor.getValue();
    verify(productRepository).save(entityCaptured);

    assertThat(entityCaptured.getId()).isEqualTo(entityFound.getId());
    assertThat(entityCaptured.getName()).isEqualTo(entityFound.getName());
    assertThat(entityCaptured.getSku()).isEqualTo(entityFound.getSku());
    assertThat(entityCaptured.getPrice()).isEqualTo(productUpdate.getPrice());
  }

  @Test
  void shouldThrowExceptionWhenUpdateProductAndProductNotFound() {
    final var productUpdate = productDomainUpdate();

    when(productRepository.findBySku(any())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> productGateway.update(productUpdate))
        .isInstanceOf(GatewayException.class)
        .hasMessage("Product with sku=[" + productUpdate.getSku() + "] not found.");

    verify(productRepository).findBySku(any());
    verifyNoMoreInteractions(productRepository);
  }

  @Test
  void shouldThrowExceptionWhenOccursErrorUpdatingProduct() {
    final var entityFound = productEntityResponse();
    final var productUpdate = productDomainUpdate();

    doReturn(Optional.of(entityFound)).when(productRepository).findBySku(any());
    when(productRepository.save(any())).thenThrow(IllegalArgumentException.class);

    assertThatThrownBy(() -> productGateway.update(productUpdate))
        .isInstanceOf(GatewayException.class)
        .hasMessage("Error updating product with sku=[" + productUpdate.getSku() + "].");

    verify(productRepository).findBySku(any());
    verify(productRepository).save(any());
  }

  @Test
  void shouldDeleteProductSuccessfully() {
    final var sku = "BOLA-123-ABC";

    doNothing().when(productRepository).deleteBySku(sku);

    productGateway.deleteBySku(sku);

    verify(productRepository).deleteBySku(sku);
  }

  @Test
  void shouldThrowExceptionWhenOccursErrorDeletingProduct() {
    final var sku = "BOLA-123-ABC";

    doThrow(IllegalArgumentException.class).when(productRepository).deleteBySku(sku);

    assertThatThrownBy(() -> productGateway.deleteBySku(sku))
        .isInstanceOf(GatewayException.class)
        .hasMessage("Error deleting product with sku=[" + sku + "].");

    verify(productRepository).deleteBySku(sku);
  }
}
