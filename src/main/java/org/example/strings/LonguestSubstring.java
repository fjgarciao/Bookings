package org.example.strings;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LonguestSubstring {

  private static final Logger LOG = LoggerFactory.getLogger(LonguestSubstring.class);

  public static String longuestSubstring(String input) {
    if (input == null || input.isBlank()) return "";

    Map<Character, Integer> positionsMap = new HashMap<>();
    int start = 0;
    int maxLength = 0;
    int bestStart = 0;

    for (int end = 0; end < input.length(); end++) {
      char c = input.charAt(end);
      LOG.info("Current char: {}", c);

      if (positionsMap.containsKey(c)) {
        start = Math.max(start, positionsMap.get(c) + 1);
        LOG.info("Char '{}' already found. New start set to {}", c, start);
      } else {
        LOG.info("Char '{}' not found", c);
      }

      positionsMap.put(c, end);

      int newLength = end - start + 1;
      if (newLength > maxLength) {
        LOG.info("Updating pointers: newLength = {} (= {} - {} + 1) > currentMaxLength = {}, newMaxLength = {}, bestStart = {}", newLength, end, start, maxLength, newLength, start);
        maxLength = newLength;
        bestStart = start;
      } else {
        LOG.info("Pointers unchanged: newLength = {} (= {} - {} + 1) <= maxLength = {}", newLength, end, start, maxLength);
      }
    }

    LOG.info("Final pointers: bestStart = {}, maxLenght = {}", bestStart, maxLength);
    return input.substring(bestStart, bestStart + maxLength);
  }
}
