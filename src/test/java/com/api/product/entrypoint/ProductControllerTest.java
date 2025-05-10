package com.api.product.entrypoint;

import static com.api.product.entrypoint.fixture.ProductControllerTestFixture.*;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.api.product.core.dto.ProductDto;
import com.api.product.core.dto.UpdateProductDto;
import com.api.product.core.usecase.CreateProduct;
import com.api.product.core.usecase.DeleteProduct;
import com.api.product.core.usecase.SearchProduct;
import com.api.product.core.usecase.UpdateProduct;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ProductControllerTest {

  private static final String BASE_URL = "/api/product";
  private static final String BASE_URL_WITH_SKU = BASE_URL + "/%s";

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockitoBean private CreateProduct createProduct;
  @MockitoBean private SearchProduct searchProduct;
  @MockitoBean private UpdateProduct updateProduct;
  @MockitoBean private DeleteProduct deleteProduct;

  @Test
  void shouldCreateProductSuccessfully() throws Exception {
    final var request = validRequest();
    final var response = validResponse();

    when(createProduct.execute(any(ProductDto.class))).thenReturn(response);

    mockMvc
        .perform(
            post(BASE_URL)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(response.getId()))
        .andExpect(jsonPath("$.name").value(response.getName()))
        .andExpect(jsonPath("$.sku").value(response.getSku()))
        .andExpect(jsonPath("$.price").value(response.getPrice()));

    final ArgumentCaptor<ProductDto> productCaptor = ArgumentCaptor.forClass(ProductDto.class);
    verify(createProduct).execute(productCaptor.capture());

    final var productCaptured = productCaptor.getValue();
    assertThat(productCaptured).usingRecursiveComparison().isEqualTo(request);
  }

  @Test
  void shouldSearchProductBySkuSuccessfully() throws Exception {
    final var sku = "BOLA-123-ABC";
    final var response = validResponse();

    when(searchProduct.execute(sku)).thenReturn(Optional.of(response));

    mockMvc
        .perform(get(format(BASE_URL_WITH_SKU, sku)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(response.getId()))
        .andExpect(jsonPath("$.name").value(response.getName()))
        .andExpect(jsonPath("$.sku").value(response.getSku()))
        .andExpect(jsonPath("$.price").value(response.getPrice()));

    verify(searchProduct).execute(sku);
  }

  @Test
  void shouldSearchProductBySkuAndReturnNotFound() throws Exception {
    final var sku = "BOLA-123-ABC";

    when(searchProduct.execute(sku)).thenReturn(Optional.empty());

    mockMvc
        .perform(get(format(BASE_URL_WITH_SKU, sku)))
        .andExpect(status().isNotFound())
        .andExpect(content().json(expectedProductNotFoundBySkuResponse()));

    verify(searchProduct).execute(sku);
  }

  @Test
  void shouldUpdateProductSuccessfully() throws Exception {
    final var sku = "BOLA-123-ABC";
    final var request = validUpdateRequest();
    final var response = validProductUpdated();

    when(updateProduct.execute(eq(sku), any(UpdateProductDto.class))).thenReturn(response);

    mockMvc
        .perform(
            put(format(BASE_URL_WITH_SKU, sku))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(response.getId()))
        .andExpect(jsonPath("$.name").value(response.getName()))
        .andExpect(jsonPath("$.sku").value(response.getSku()))
        .andExpect(jsonPath("$.price").value(response.getPrice()));

    final ArgumentCaptor<UpdateProductDto> productCaptor =
        ArgumentCaptor.forClass(UpdateProductDto.class);
    verify(updateProduct).execute(eq(sku), productCaptor.capture());

    final var productCaptured = productCaptor.getValue();
    assertThat(productCaptured).usingRecursiveComparison().isEqualTo(request);
  }

  @Test
  void shouldDeleteProductSuccessfully() throws Exception {
    final var sku = "BOLA-123-ABC";

    doNothing().when(deleteProduct).execute(sku);

    mockMvc.perform(delete(format(BASE_URL_WITH_SKU, sku))).andExpect(status().isNoContent());

    verify(deleteProduct).execute(sku);
  }
}
