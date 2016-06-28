package com.spakai.index;

import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;

import com.spakai.exception.NoMatchException;
import java.util.Collections;

public class PrimaryTreeIndex<K,V> implements Index<K,V> {

  private final TreeMap<K, V> index = new TreeMap<>();

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
      return CompletableFuture.supplyAsync(
              () -> {
                  Entry<K,V> entry = index.headMap(key).entrySet()
                                       .stream()
                                       .sorted(Collections.reverseOrder())
                                       .filter(e-> key.toString().startsWith(e.getKey().toString()))
                                       .findFirst()
                                       .orElseThrow(() -> new NoMatchException("No match found"));
                  
                   Set results = new HashSet();
                   results.add(entry.getValue());
                   return results;
                  
              }
      );
  } 

  //TODO temporary way to load data
  @Override
  public void load(K key, V value) {
    index.put(key, value);
  }

}
