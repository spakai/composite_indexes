package com.spakai.index;

import java.util.*;
import com.spakai.exception.NoMatchException;

public class HashIndex implements Index {

  private final Map<String,Set<String>> index = new HashMap<>();

  @Override
  public Set<String> exactMatch(String key) {
    Objects.requireNonNull(key);

    Set<String> retrieved = index.get(key);
    if(retrieved == null) {
        throw new NoMatchException("No match found for" + key);
    }

    return retrieved;
  }

  @Override
  public Set<String> bestMatch(String key) {
      throw new UnsupportedOperationException("Best match is not supported");
  }

  @Override
  public void insert(String key, String value) {
    Objects.requireNonNull(key);
    Objects.requireNonNull(value);

    Set<String> retrievedSet = index.get(key);
    if(retrievedSet == null) {
        add(key,value);
    } else {
        update(retrievedSet,key,value);
    }
  }

  private void add(String key, String value) {
      Set<String> newSet = new HashSet<>();
      newSet.add(value);
      index.put(key, newSet);
  }

  private void update(Set<String> retrieved, String key,String value) {
      retrieved.add(value);
      index.put(key, retrieved);
  }
   @Override
   public String toString() {
        return "HashIndex{" +
                "index=" + index +
                '}';
    }


}
