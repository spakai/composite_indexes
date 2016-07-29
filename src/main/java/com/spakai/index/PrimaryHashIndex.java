package com.spakai.index;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.spakai.exception.NoMatchException;

public class PrimaryHashIndex<V> extends Index<V> {

  private final Map<String,V> index;
  
  public PrimaryHashIndex(ExecutorService pool) {
    super(pool);
    this.index = new HashMap<>();
  }
    
  @Override
  public CompletableFuture<Set<V>> asyncExactMatch(String key) {
      return CompletableFuture.supplyAsync(
              () -> {
                V result = index.get(key);
                if (result != null) {
                  Set results = new HashSet(1);
                  results.add(result);
                  return results;
                } else {
                  throw new NoMatchException("No match found");
                }
              }, pool);

  }
  
  @Override
  public Set<V> syncExactMatch(String key) {
        V result = index.get(key);
        if (result != null) {
            Set results = new HashSet(1);
            results.add(result);
            return results;
        } else {
            throw new NoMatchException("No match found");
        }
  }

  @Override
  public CompletableFuture<Set<V>> asyncBestMatch(String key) {
      throw new NoMatchException("Best match is not supported");
  }
  
  @Override
  public Set<V> syncBestMatch(String key) {
      throw new NoMatchException("Best match is not supported");
  }

  @Override
  public void load(String key, V value) {
    index.put(key, value);
  }

}
