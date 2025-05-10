package com.api.product.core.domain;

import static java.lang.String.format;

import com.api.product.core.domain.exception.DomainException;
import com.api.product.core.domain.valueobject.ValidationDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Product {

  private static final String DOMAIN_MESSAGE_ERROR = "by domain client";
  private static final String BLANK_MESSAGE_ERROR = "Field=[%s] should not be empty or null";
  private static final String PATTERN_ERROR_MESSAGE =
      "The field=[%s] is null or has an invalid pattern";
  private static final String NEGATIVE_PRICE_ERROR = "Field=[price] should not be negative";
  private static final Predicate<String> PATTERN_SKU =
      n -> n == null || !Pattern.compile("^[A-Z0-9\\-]{5,20}$").matcher(n).matches();

  private String id;
  private String name;
  private String sku;
  private Double price;

  public Product() {}

  public Product(final String id, final String name, final String sku, final Double price) {

    validateDomain(name, sku, price);

    this.id = id;
    this.name = name;
    this.sku = sku;
    this.price = price;
  }

  public static Product createProduct(final String name, final String sku, final Double price) {

    validateDomain(name, sku, price);

    return new Product(null, name, sku, price);
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getSku() {
    return sku;
  }

  public Double getPrice() {
    return price;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setPrice(final Double price) {
    this.price = price;
  }

  private static void validateDomain(final String name, final String sku, final Double price) {
    final List<ValidationDomain<?>> rules =
        List.of(
            new ValidationDomain<>(
                name,
                format(BLANK_MESSAGE_ERROR, "name"),
                List.of(Objects::isNull, String::isBlank)),
            new ValidationDomain<>(sku, format(PATTERN_ERROR_MESSAGE, "sku"), List.of(PATTERN_SKU)),
            new ValidationDomain<>(
                price, format(BLANK_MESSAGE_ERROR, "price"), List.of(Objects::isNull)),
            new ValidationDomain<>(price, NEGATIVE_PRICE_ERROR, List.of(p -> p != null && p < 0)));

    final var errors = validate(rules);

    if (!errors.isEmpty()) {
      throw new DomainException(errors);
    }
  }

  private static List<String> validate(final List<ValidationDomain<?>> validations) {
    return validations.stream()
        .filter(Product::isInvalid)
        .map(it -> format("%s %s", it.message(), DOMAIN_MESSAGE_ERROR))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  private static <T> boolean isInvalid(final ValidationDomain<T> domain) {
    return domain.predicates().stream().anyMatch(predicate -> predicate.test(domain.field()));
  }
}
