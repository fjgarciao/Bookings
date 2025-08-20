package org.example.bookings;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BookingsTest {

  @Test
  void applyCurrencyConversion() {
    BigDecimal convertedValue = Bookings.applyCurrencyConversion(BigDecimal.valueOf(1d), 0.95d);
    assertThat(convertedValue).isEqualByComparingTo(BigDecimal.valueOf(0.95d));
  }

  @Test
  void applyCurrencyConversion_JPY() {
    BigDecimal convertedValue = Bookings.applyCurrencyConversion(BigDecimal.valueOf(90000d), 0.0070d);
    assertThat(convertedValue).isEqualByComparingTo(BigDecimal.valueOf(630));
  }

  @Test
  void applyDiscount() {
    BigDecimal discountedValue = Bookings.applyDiscount(BigDecimal.valueOf(100d), 0.1d);
    assertThat(discountedValue).isEqualByComparingTo(BigDecimal.valueOf(90.0d));
  }

  @Test
  void calculateTotalPrice_whenCurrencyConversionIsMissing_throwsException() {
    Map<String, Double> currencyConversions = Map.of(
      "USD", 1.0d   // base
    );

    Map<String, Double> partnerDiscounts = Map.of(
      "partner1", 0.10d,
      "partner2", 0.20d,
      "partner3", 0.30d
    );

    List<Bookings.Booking> bookings = List.of(
      new Bookings.Booking("1", "partner1", LocalDate.of(2025, 8, 31), LocalDate.of(2025, 9, 5), BigDecimal.valueOf(185d), "EUR")
    );

    assertThatThrownBy(() -> Bookings.calculateTotalPrice(bookings, partnerDiscounts, currencyConversions))
      .isInstanceOf(RuntimeException.class);
  }

  @Test
  void calculateTotalPrice_whenPartnerDiscountIsMissing_useZeroDiscount() {
    Map<String, Double> currencyConversions = Map.of(
      "USD", 1.0d   // base
    );

    Map<String, Double> partnerDiscounts = Map.of();

    List<Bookings.Booking> bookings = List.of(
      new Bookings.Booking("1", "partner1", LocalDate.of(2025, 8, 31), LocalDate.of(2025, 9, 5), BigDecimal.valueOf(185d), "USD")
    );

    BigDecimal totalPrice = Bookings.calculateTotalPrice(bookings, partnerDiscounts, currencyConversions);
    assertThat(totalPrice).isEqualByComparingTo(BigDecimal.valueOf(185d));
  }

  @Test
  void calculateTotalPrice() {
    Map<String, Double> currencyConversions = Map.of(
      "USD", 1.0d,   // base
      "EUR", 1.10d,   // 1 EUR = 1.10 USD
      "JPY", 0.0070d, // 1 JPY = 0.0070 USD
      "GBP", 1.30d   // 1 GBP = 1.30 USD
    );

    Map<String, Double> partnerDiscounts = Map.of(
      "partner1", 0d,
      "partner2", 0.10d,
      "partner3", 0.15d
    );

    List<Bookings.Booking> bookings = List.of(
      new Bookings.Booking("1", "partner1", LocalDate.of(2025, 8, 31), LocalDate.of(2025, 9, 5), BigDecimal.valueOf(185d), "EUR"),
      new Bookings.Booking("1", "partner2", LocalDate.of(2025, 8, 31), LocalDate.of(2025, 9, 5), BigDecimal.valueOf(110.05d), "USD"),
      new Bookings.Booking("1", "partner3", LocalDate.of(2025, 8, 31), LocalDate.of(2025, 9, 5), BigDecimal.valueOf(9000d), "JPY"),
      new Bookings.Booking("1", "partner4", LocalDate.of(2025, 8, 31), LocalDate.of(2025, 9, 5), BigDecimal.valueOf(90.75d), "GBP")
    );

    BigDecimal totalPrice = Bookings.calculateTotalPrice(bookings, partnerDiscounts, currencyConversions);
    assertThat(totalPrice).isEqualByComparingTo(BigDecimal.valueOf(474.08d));
  }
}
