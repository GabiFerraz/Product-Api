package com.api.product.infra.persistence.repository;

import com.api.product.infra.persistence.entity.ProductEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

  Optional<ProductEntity> findBySku(final String sku);

  void deleteBySku(final String sku);
}
