package com.spakai.index;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class PrimaryHashIndex<K,V> implements Index<K,V> {

  private Map<K, V> index = new HashMap<K, V>();

  public Future<V> exactMatch(K key) {

    Callable<V> task = () -> {
      V result =  index.get(key);
      return result;
    };

    final FutureTask<V> futureTask = new FutureTask<V>(task);

    //TODO run the task in an executor

    return futureTask;

  }
}


