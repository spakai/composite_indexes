package com.spakai.index;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import com.spakai.exception.NoMatchException;

public class PrimaryHashIndex<String,V> implements Index<String,V> {

  private final Map<String, V> index = new HashMap<>();

  @Override
  public CompletableFuture<Set<V>> exactMatch(String key) {
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
  public CompletableFuture<Set<V>> bestMatch(String key) {
      throw new NoMatchException("Best match is not supported");
  }

  @Override
  public void load(String key, V value) {
    index.put(key, value);
  }

}
