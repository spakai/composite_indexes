package com.spakai.composite;

import com.spakai.index.Index;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class TwoKeyLookup<K,V> implements Composite<K,V> {

    private Index<K,V> callingNumberIndex;
    private Index<K,V> calledNumberIndex;

    public TwoKeyLookup(Index callingNumberIndex, Index calledNumberIndex) {
        this.callingNumberIndex = callingNumberIndex;
        this.calledNumberIndex = calledNumberIndex;
    }

    public CompletableFuture<Set<V>> lookup(K callingNumber, K calledNumber) {
        CompletableFuture<Set<V>> calling = callingNumberIndex.exactMatch(callingNumber);
        CompletableFuture<Set<V>> called = calledNumberIndex.exactMatch(calledNumber);
        return calling.thenCombine(called, (rate1, rate2) -> findCommonMatch(rate1, rate2));
    }

    public Set<V> findCommonMatch(Set<V> rate1, Set<V> rate2) {
        Set<V> intersection = new HashSet<V>(rate1);
        intersection.retainAll(rate2);

        return intersection;
    }
}
