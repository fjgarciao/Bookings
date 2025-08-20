package org.example.lrucache;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class LRUCacheAdvConcurrency<K, V> {

  private final int capacity;
  private final ConcurrentHashMap<K, V> cache;
  private final ConcurrentLinkedDeque<K> deque;
  private final ReentrantLock dequeLock;

  public LRUCacheAdvConcurrency(int capacity) {
    this.capacity = capacity;
    this.cache = new ConcurrentHashMap<>(capacity);
    this.deque = new ConcurrentLinkedDeque<>();
    this.dequeLock = new ReentrantLock();
  }

  public V get(K key) {
    V value = cache.get(key);
    if (value != null) {
      dequeLock.lock();
      try {
        deque.remove(key);
        deque.addLast(key);
      } finally {
        dequeLock.unlock();
      }
    }
    return value;
  }

  public void put(K key, V value) {
    cache.compute(key, (k, v) -> {
      dequeLock.lock();
      try {
        if (v != null) {
          deque.remove(key);
        } else if (cache.size() >= capacity) {
          K eldestKey = deque.pollFirst();
          if (eldestKey != null) {
            cache.remove(eldestKey);
          }
        }
        deque.addLast(key);
      } finally {
        dequeLock.unlock();
      }
      return value;
    });
  }

  public Set<Map.Entry<K,V>> entrySet() {
    dequeLock.lock();
    try {
      return deque.stream().
        map(key -> new AbstractMap.SimpleEntry<>(key, cache.get(key))).
        collect(Collectors.toCollection(LinkedHashSet::new));
    } finally {
      dequeLock.unlock();
    }
  }
}
