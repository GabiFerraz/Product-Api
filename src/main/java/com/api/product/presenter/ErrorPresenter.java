package com.api.product.presenter;

import com.api.product.presenter.response.ErrorPresenterResponse;
import org.springframework.stereotype.Component;

@Component
public class ErrorPresenter {

  public ErrorPresenterResponse toPresenterErrorResponse(final String errorMessage) {
    return ErrorPresenterResponse.builder().errorMessage(errorMessage).build();
  }
}
