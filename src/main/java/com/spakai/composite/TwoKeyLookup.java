package com.spakai.composite;

import com.spakai.index.Index;
import com.spakai.exception.*;
import com.spakai.index.PrimaryHashIndex;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TwoKeyLookup<K,V> extends Composite<K,V> {

    private Index<K,V> callingNumberIndex;
    private Index<K,V> calledNumberIndex;
    private final ExecutorService pool;

    public TwoKeyLookup(int numberOfThreads) {
        pool = Executors.newFixedThreadPool(numberOfThreads);
        this.callingNumberIndex = new PrimaryHashIndex<>(pool);
        this.calledNumberIndex = new PrimaryHashIndex<>(pool);
    }

    public CompletableFuture<Set<V>> lookup(K callingNumber, K calledNumber) {
        CompletableFuture<Set<V>> calling = callingNumberIndex.exactMatch(callingNumber);
        CompletableFuture<Set<V>> called = calledNumberIndex.exactMatch(calledNumber);
        return calling.thenCombineAsync(called, (s1, s2) -> intersection(s1, s2),pool);
    }

    //temp solution to load data
    public Index<K,V>  getIndexOne() {
        return callingNumberIndex;
    }

    public Index<K,V>  getIndexTwo() {
        return calledNumberIndex;
    }
}
