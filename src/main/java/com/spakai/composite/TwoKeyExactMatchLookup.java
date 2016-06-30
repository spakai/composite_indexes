package com.spakai.composite;

import com.spakai.index.Index;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class TwoKeyExactMatchLookup<V> extends Composite<V> {

    private final Index<V> callingNumberIndex;
    private final Index<V> calledNumberIndex;
    
    public TwoKeyExactMatchLookup(Index<V> callingNumberIndex,  Index<V> calledNumberIndex) {
        this.callingNumberIndex = callingNumberIndex;
        this.calledNumberIndex = calledNumberIndex;
    }

    public CompletableFuture<Set<V>> lookup(String callingNumber, String calledNumber) {
        CompletableFuture<Set<V>> calling = callingNumberIndex.exactMatch(callingNumber);
        CompletableFuture<Set<V>> called = calledNumberIndex.exactMatch(calledNumber);
        return calling.thenCombineAsync(called, (s1, s2) -> intersection(s1, s2));
    }
}
