package com.spakai.index;

import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import com.spakai.exception.NoMatchException;
import java.util.concurrent.ExecutorService;

public class PrimaryTreeIndex<V> extends Index<V> {

  private final TreeMap<String, V> index;

    public PrimaryTreeIndex(ExecutorService pool) {
        super(pool);
        this.index = new TreeMap<>();
    }
  
  @Override
  public CompletableFuture<Set<V>> asyncExactMatch(String key) {
      return CompletableFuture.supplyAsync(
              () -> {
                V result = index.get(key);
                if (result != null) {
                  Set<V> results = new HashSet<>(1);
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
         Set<V> results = new HashSet<>(1);
         results.add(result);
         return results;
       } else {
         throw new NoMatchException("No match found");
       }
  }
  
    /**
     *
     * @param key
     * @return
     */
    @Override
  public CompletableFuture<Set<V>> asyncBestMatch(String key) {
     
      return CompletableFuture.supplyAsync(
              () -> {
                  Entry<String,V> entry = index.headMap(key, true).descendingMap().entrySet()
                                            .stream()
                                            .filter(e -> isPartiallyOrFullyMatching(key, e.getKey()))
                                            .findFirst()
                                            .orElseThrow(() -> new NoMatchException("No match found"));
                  
                   Set<V> results = new HashSet<>();
                   results.add(entry.getValue());
                   return results;
                  
              }
      );
  } 
  
  
  @Override
  public Set<V> syncBestMatch(String key) {
      return null;
      
  }
  
  public boolean isPartiallyOrFullyMatching(String requestedKey, String currentKeyInMap) {
      return (requestedKey.equals(currentKeyInMap) || (requestedKey.startsWith(currentKeyInMap)));
  }
  
  @Override
  public void load(String key, V value) {
    index.put(key, value);
  }

}