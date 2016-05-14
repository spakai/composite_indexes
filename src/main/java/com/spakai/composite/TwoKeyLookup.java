package com.spakai.composite;

import com.spakai.index.Index;
import com.spakai.index.PrimaryHashIndex;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class TwoKeyLookup<K,V> extends Composite<K,V> {

    private final Index<K,V> callingNumberIndex;
    private final Index<K,V> calledNumberIndex;
    
    public TwoKeyLookup(int numberOfThreads) {
        this.callingNumberIndex = new PrimaryHashIndex<>();
        this.calledNumberIndex = new PrimaryHashIndex<>();
    }

    public CompletableFuture<Set<V>> lookup(K callingNumber, K calledNumber) {
        CompletableFuture<Set<V>> calling;
        calling = callingNumberIndex.exactMatch(callingNumber);
        CompletableFuture<Set<V>> called;
        called = calledNumberIndex.exactMatch(calledNumber);
        return calling.thenCombineAsync(called, (s1, s2) -> intersection(s1, s2));
    }

    //temp solution to load data
    public Index<K,V>  getIndexOne() {
        return callingNumberIndex;
    }

    public Index<K,V>  getIndexTwo() {
        return calledNumberIndex;
    }
}
