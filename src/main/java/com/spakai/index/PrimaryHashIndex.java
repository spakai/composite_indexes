package com.spakai.index;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class PrimaryHashIndex<K,V> implements Index<K,V> {

  private Map<K, V> index = new HashMap<>();

  public CompletableFuture<Set<V>> exactMatch(K key) {

    CompletableFuture<Set<V>> futureLookup = new CompletableFuture<>();

    //TODO Change to executor later
    new Thread(
        () -> {
            V result = index.get(key);
            if (result != null) {
              Set results = new HashSet();
              results.add(result);

              futureLookup.complete(results);
            } else {
              futureLookup.completeExceptionally(new Exception("No match found"));
            }
        }
    ).start();

    return futureLookup;
  }

  //TODO temporary way to load data
  public void load(K key, V value) {
    index.put(key, value);
  }

}


