package com.spakai.index;

import java.util.concurrent.CompletableFuture;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public abstract class Index<V> {
    
  protected final ExecutorService pool;
    
  public Index(ExecutorService pool) {
      this.pool = pool;
  }
  
  public abstract CompletableFuture<Set<V>> asyncExactMatch(String key);
  public abstract Set<V> syncExactMatch(String key);
  public abstract CompletableFuture<Set<V>> asyncBestMatch(String key);
  public abstract Set<V> syncBestMatch(String key);
  public abstract void load(String key, V value);
}
