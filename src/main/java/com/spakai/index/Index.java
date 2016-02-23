package com.spakai.index;

import java.util.concurrent.Future;

public interface Index<K,V> {
  public Future<V> exactMatch(K key);
}
