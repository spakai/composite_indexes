package com.spakai.index;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import com.spakai.exception.*;

public class PrimaryHashIndex<K,V> implements Index<K,V> {

  private Map<K, V> index = new HashMap<>();

  private final ExecutorService pool;

  public PrimaryHashIndex(ExecutorService pool) {
      this.pool = pool;
  }

  public CompletableFuture<Set<V>> exactMatch(K key) {
      return CompletableFuture.supplyAsync(
              () -> {
                V result = index.get(key);
                if (result != null) {
                  Set results = new HashSet();
                  results.add(result);
                  return results;
                } else {
                  throw new NoMatchException("No match found");
                }
              },pool);

  }

  //TODO temporary way to load data
  public void load(K key, V value) {
    index.put(key, value);
  }

}


