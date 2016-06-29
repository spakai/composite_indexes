package com.spakai.index;

import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import com.spakai.exception.NoMatchException;

public class PrimaryTreeIndex<String,V> implements Index<String,V> {

  private final TreeMap<String, V> index = new TreeMap<>();

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

    /**
     *
     * @param key
     * @return
     */
    @Override
  public CompletableFuture<Set<V>> bestMatch(String key) {
      
      return CompletableFuture.supplyAsync(
              () -> {
                  Entry<String,V> entry = index.headMap(key, true).descendingMap().entrySet()
                                            .stream()
                                            .filter(e -> key.toString().startsWith(e.getKey().toString()) 
                                                   || key.toString().equals(e.getKey().toString()))
                                            .findFirst()
                                            .orElseThrow(() -> new NoMatchException("No match found"));
                  
                   Set results = new HashSet();
                   results.add(entry.getValue());
                   return results;
                  
              }
      );
  } 

  @Override
  public void load(String key, V value) {
    index.put(key, value);
  }

}
