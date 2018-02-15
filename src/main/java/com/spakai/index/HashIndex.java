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
    Set<String> val = index.get(key);
    if(val == null) {
        Set<String> item = new HashSet<>();
        item.add(value);
    } else {
        val.add(value);
    }
  }

   @Override
   public String toString() {
        return "HashIndex{" +
                "index=" + index +
                '}';
    }


}
