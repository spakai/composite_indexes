package com.spakai.index;

import java.util.Set;

public interface Index {
  Set<String> exactMatch(String key);
  Set<String> bestMatch(String key);
  void insert(String key, String value);
}
