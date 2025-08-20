package org.example.bookings;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class Bookings {

  public static BigDecimal calculateTotalPrice(List<Booking> bookings, Map<String, Double> partnerDiscounts, Map<String, Double> currencyConversions) {
    return bookings.stream().map(booking -> {
      if (!currencyConversions.containsKey(booking.currency)) {
        throw new RuntimeException(format("Currency %s not available in conversions map", booking.currency));
      }

      double conversionRate = currencyConversions.get(booking.currency);
      double partnerDiscount = partnerDiscounts.getOrDefault(booking.partnerId, 0.0d);

      BigDecimal priceInUSD = applyCurrencyConversion(booking.price, conversionRate);
      return applyDiscount(priceInUSD, partnerDiscount);
    }).reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  static BigDecimal applyCurrencyConversion(BigDecimal base, double conversionRate) {
    return base.multiply(BigDecimal.valueOf(conversionRate));
  }

  static BigDecimal applyDiscount(BigDecimal base, double discount) {
    return base.subtract(base.multiply(BigDecimal.valueOf(discount))).setScale(2, RoundingMode.HALF_UP);
  }

  public record Booking(String bookingId, String partnerId, LocalDate checkInDate, LocalDate checkOutDate, BigDecimal price, String currency) {
  }
}
