package com.spakai.index;

import java.util.concurrent.CompletableFuture;
import java.util.Set;

public interface Index<String,V> {
  public CompletableFuture<Set<V>> exactMatch(String key);
  public CompletableFuture<Set<V>> bestMatch(String key);
  public void load(String key, V value);
}
