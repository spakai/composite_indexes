package com.spakai.composite;

import com.spakai.index.Index;
import com.spakai.exception.*;
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

    public CompletableFuture<Set<V>> lookup(K callingNumber, K calledNumber) throws NoMatchException {
        CompletableFuture<Set<V>> calling = callingNumberIndex.exactMatch(callingNumber);
        CompletableFuture<Set<V>> called = calledNumberIndex.exactMatch(calledNumber);
        return calling.thenCombine(called, this::findCommonMatch);
    }

    public Set<V> findCommonMatch(Set<V> s1, Set<V> s2) throws NoMatchException {
        Set<V> intersection = new HashSet<V>(s1);
        intersection.retainAll(s2);

        if (intersection.isEmpty()) {
          throw new NoMatchException("No match found");
        }

        return intersection;
    }
}
