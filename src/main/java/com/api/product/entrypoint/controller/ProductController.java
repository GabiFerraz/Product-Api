package com.api.product.entrypoint.controller;

import static java.lang.String.format;

import com.api.product.core.domain.Product;
import com.api.product.core.dto.ProductDto;
import com.api.product.core.dto.UpdateProductDto;
import com.api.product.core.usecase.CreateProduct;
import com.api.product.core.usecase.DeleteProduct;
import com.api.product.core.usecase.SearchProduct;
import com.api.product.core.usecase.UpdateProduct;
import com.api.product.presenter.ErrorPresenter;
import com.api.product.presenter.ProductPresenter;
import com.api.product.presenter.response.ProductPresenterResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

  private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product with sku=[%s] not found.";

  private final CreateProduct createProduct;
  private final SearchProduct searchProduct;
  private final UpdateProduct updateProduct;
  private final DeleteProduct deleteProduct;
  private final ProductPresenter presenter;
  private final ErrorPresenter errorPresenter;

  @PostMapping
  public ResponseEntity<ProductPresenterResponse> create(
      @Valid @RequestBody final Product request) {
    final var product = this.createProduct.execute(this.toProductDto(request));

    return new ResponseEntity<>(presenter.parseToResponse(product), HttpStatus.CREATED);
  }

  @GetMapping("/{sku}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Object> search(
      @Validated
          @NotBlank(message = "The field 'sku' is required")
          @Pattern(regexp = "^[A-Z0-9\\-]{5,20}$", message = "The field 'sku' is invalid")
          @PathVariable("sku")
          final String sku) {
    final var response = this.searchProduct.execute(sku);

    return response
        .<ResponseEntity<Object>>map(
            product -> ResponseEntity.ok(presenter.parseToResponse(product)))
        .orElseGet(
            () ->
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                        errorPresenter.toPresenterErrorResponse(
                            format(PRODUCT_NOT_FOUND_MESSAGE, sku))));
  }

  @PutMapping("/{sku}")
  public ResponseEntity<ProductPresenterResponse> update(
      @Validated
          @NotBlank(message = "The field 'sku' is required")
          @Pattern(regexp = "^[A-Z0-9\\-]{5,20}$", message = "The field 'sku' is invalid")
          @PathVariable("sku")
          final String sku,
      @Valid @RequestBody final UpdateProductDto request) {
    final var product = this.updateProduct.execute(sku, request);

    return new ResponseEntity<>(presenter.parseToResponse(product), HttpStatus.OK);
  }

  @DeleteMapping("/{sku}")
  public ResponseEntity<Void> delete(
      @Validated
          @NotBlank(message = "The field 'sku' is required")
          @Pattern(regexp = "^[A-Z0-9\\-]{5,20}$", message = "The field 'sku' is invalid")
          @PathVariable("sku")
          final String sku) {
    this.deleteProduct.execute(sku);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  private ProductDto toProductDto(final Product product) {
    return new ProductDto(product.getName(), product.getSku(), product.getPrice());
  }
}
