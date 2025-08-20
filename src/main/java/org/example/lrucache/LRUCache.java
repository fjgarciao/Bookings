package org.example.lrucache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class LRUCache<K, V> {

  private final LinkedHashMap<K, V> cache;

  public LRUCache(int capacity) {
    this(capacity, 0.75f);
  }

  public LRUCache(int capacity, float loadFactor) {
    this.cache = new LinkedHashMap<>(capacity, loadFactor, true) {
      @Override
      protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > capacity;
      }
    };
  }

  public V get(K key) {
    return cache.get(key);
  }

  public void put(K key, V value) {
    cache.put(key, value);
  }

  public Set<Map.Entry<K,V>> entrySet() {
    return cache.entrySet();
  }

  public String toString() {
    return cache.toString();
  }
}
