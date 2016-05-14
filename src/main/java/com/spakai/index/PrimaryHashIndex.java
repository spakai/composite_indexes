package com.spakai.index;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import com.spakai.exception.NoMatchException;

public class PrimaryHashIndex<K,V> implements Index<K,V> {

  private final Map<K, V> index = new HashMap<>();

  @Override
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
              });

  }

  @Override
  public CompletableFuture<Set<V>> bestMatch(K key) {
      throw new NoMatchException("Best match is not supported");
  }


  //TODO temporary way to load data
  @Override
  public void load(K key, V value) {
    index.put(key, value);
  }

}
