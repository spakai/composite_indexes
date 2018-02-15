package com.spakai.index;

import java.util.Set;

public interface Index {
  public Set<String> exactMatch(String key);
  public Set<String> bestMatch(String key);
  public void insert(String key, String value);
}
