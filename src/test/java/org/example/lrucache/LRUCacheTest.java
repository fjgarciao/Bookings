package org.example.lrucache;

import java.util.AbstractMap;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LRUCacheTest {

  @Test
  void testCache() {
    LRUCache<Integer, String> cache = new LRUCache<>(3);

    cache.put(1, "A");
    cache.put(2, "B");
    cache.put(3, "C");

    assertThat(cache.entrySet()).containsExactly(
      new AbstractMap.SimpleEntry<>(1, "A"),
      new AbstractMap.SimpleEntry<>(2, "B"),
      new AbstractMap.SimpleEntry<>(3, "C")
    );

    cache.get(1);
    assertThat(cache.entrySet()).containsExactly(
      new AbstractMap.SimpleEntry<>(2, "B"),
      new AbstractMap.SimpleEntry<>(3, "C"),
      new AbstractMap.SimpleEntry<>(1, "A")
    );

    cache.put(4, "D");
    assertThat(cache.entrySet()).containsExactly(
      new AbstractMap.SimpleEntry<>(3, "C"),
      new AbstractMap.SimpleEntry<>(1, "A"),
      new AbstractMap.SimpleEntry<>(4, "D")
    );
  }
}
