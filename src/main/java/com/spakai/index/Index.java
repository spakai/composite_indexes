package com.spakai.index;

import java.util.concurrent.CompletableFuture;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public abstract class Index<V> {
    
  protected final ExecutorService pool;
    
  public Index(ExecutorService pool) {
      this.pool = pool;
  }
  
  public abstract CompletableFuture<Set<V>> exactMatch(String key);
  public abstract CompletableFuture<Set<V>> bestMatch(String key);
  public abstract void load(String key, V value);
}
