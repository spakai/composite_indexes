package com.spakai.index;

import java.util.concurrent.CompletableFuture;
import java.util.Set;

public interface Index<K,V> {
  public CompletableFuture<Set<V>> exactMatch(K key);
  public void load(K key, V value);
}
