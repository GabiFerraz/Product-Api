package com.api.product.infra.gateway;

import com.api.product.core.domain.Product;
import com.api.product.core.gateway.ProductGateway;
import com.api.product.infra.gateway.exception.GatewayException;
import com.api.product.infra.persistence.entity.ProductEntity;
import com.api.product.infra.persistence.repository.ProductRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductGatewayImpl implements ProductGateway {

  private static final String SAVE_ERROR_MESSAGE = "Error saving product with sku=[%s].";
  private static final String FIND_ERROR_MESSAGE = "Product with sku=[%s] not found.";
  private static final String UPDATE_ERROR_MESSAGE = "Error updating product with sku=[%s].";
  private static final String DELETE_ERROR_MESSAGE = "Error deleting product with sku=[%s].";

  private final ProductRepository productRepository;

  @Override
  public Product save(final Product product) {
    try {
      final var entity =
          ProductEntity.builder()
              .name(product.getName())
              .sku(product.getSku())
              .price(product.getPrice())
              .build();

      final var saved = this.productRepository.save(entity);

      return this.toResponse(saved);
    } catch (IllegalArgumentException e) {
      throw new GatewayException(String.format(SAVE_ERROR_MESSAGE, product.getSku()));
    }
  }

  @Override
  public Optional<Product> findBySku(final String sku) {
    try {
      final var entity = this.productRepository.findBySku(sku);

      return entity.map(this::toResponse);
    } catch (IllegalArgumentException e) {
      throw new GatewayException(String.format(FIND_ERROR_MESSAGE, sku));
    }
  }

  @Override
  public Product update(final Product product) {
    try {
      final var entity =
          this.productRepository
              .findBySku(product.getSku())
              .orElseThrow(
                  () -> new GatewayException(String.format(FIND_ERROR_MESSAGE, product.getSku())));

      entity.setName(product.getName());
      entity.setPrice(product.getPrice());

      final var updatedEntity = this.productRepository.save(entity);

      return this.toResponse(updatedEntity);
    } catch (IllegalArgumentException e) {
      throw new GatewayException(String.format(UPDATE_ERROR_MESSAGE, product.getSku()));
    }
  }

  @Override
  public void deleteBySku(final String sku) {
    try {
      this.productRepository.deleteBySku(sku);
    } catch (IllegalArgumentException e) {
      throw new GatewayException(String.format(DELETE_ERROR_MESSAGE, sku));
    }
  }

  private Product toResponse(final ProductEntity entity) {
    return new Product(
        entity.getId().toString(), entity.getName(), entity.getSku(), entity.getPrice());
  }
}
