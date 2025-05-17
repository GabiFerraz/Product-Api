package com.api.product.infra.gateway.stock;

import static java.lang.String.format;

import com.api.product.core.gateway.StockApiGateway;
import com.api.product.core.gateway.response.StockDetailsResponse;
import com.api.product.infra.gateway.exception.GatewayException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class StockApiServiceGateway implements StockApiGateway {

  @Value("${app.stock-api.base-url}")
  private String stockApiBaseUrl;

  private final WebClient.Builder webClientBuilder;

  @Override
  public StockDetailsResponse getStockDetails(final String sku) {
    try {
      final String url = stockApiBaseUrl + "/" + sku;

      return callService(url);
    } catch (Exception e) {
      throw new GatewayException(format("Failed to access Product API=[%s]", e.getMessage()));
    }
  }

  private StockDetailsResponse callService(final String url) {
    WebClient webClient = webClientBuilder.baseUrl(url).build();

    return webClient.get().retrieve().bodyToMono(StockDetailsResponse.class).block();
  }
}
