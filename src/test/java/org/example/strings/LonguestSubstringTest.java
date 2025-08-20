package org.example.strings;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThat;

class LonguestSubstringTest {

  @ParameterizedTest
  @NullSource
  void longuestSubstring_whenNullInput_returnEmptyString(String input) {
    assertThat(LonguestSubstring.longuestSubstring(input)).isEqualTo("");
  }

  @ParameterizedTest
  @EmptySource
  void longuestSubstring_whenEmptyInput_returnEmptyString(String input) {
    assertThat(LonguestSubstring.longuestSubstring(input)).isEqualTo("");
  }

  @ParameterizedTest
  @CsvSource({
    "abcada,bcad"
  })
  void longuestSubstring(String str, String expected) {
    assertThat(LonguestSubstring.longuestSubstring(str)).isEqualTo(expected);
  }
}
