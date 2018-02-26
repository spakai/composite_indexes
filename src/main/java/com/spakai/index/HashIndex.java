package com.spakai.index;

import java.util.*;
import com.spakai.exception.NoMatchException;

public class HashIndex implements Index {

  private final Map<String,Set<String>> index = new HashMap<>();

  @Override
  public Set<String> exactMatch(String key) {
    Set<String> response = index.get(key);
    return response;
  }

  @Override
  public Set<String> bestMatch(String key) {
      throw new UnsupportedOperationException("Best match is not supported");
  }

  @Override
  public void insert(String key, String value) {
    Set<String> retrievedSet = index.get(Objects.requireNonNull(key));
    if(retrievedSet == null) {
        add(key,value);
    } else {
        update(retrievedSet,key,value);
    }
  }

  private Set<String> add(String key, String value) {
      Set<String> newSet = new HashSet<>();
      newSet.add(value);
      index.put(key, newSet);
      return newSet;
  }

  private void update(Set<String> retrievedSet, String key,String value) {
      retrievedSet.add(value);
      index.put(key, retrievedSet);
  }
   @Override
   public String toString() {
        return "HashIndex{" +
                "index=" + index +
                '}';
    }


}
