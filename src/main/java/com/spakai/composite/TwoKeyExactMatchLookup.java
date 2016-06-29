package com.spakai.composite;

import com.spakai.index.Index;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class TwoKeyExactMatchLookup<K,V> extends Composite<K,V> {

    private final Index<K,V> callingNumberIndex;
    private final Index<K,V> calledNumberIndex;
    
    public TwoKeyExactMatchLookup(Index<K,V> callingNumberIndex,  Index<K,V> calledNumberIndex) {
        this.callingNumberIndex = callingNumberIndex;
        this.calledNumberIndex = calledNumberIndex;
    }

    public CompletableFuture<Set<V>> lookup(K callingNumber, K calledNumber) {
        CompletableFuture<Set<V>> calling = callingNumberIndex.exactMatch(callingNumber);
        CompletableFuture<Set<V>> called = calledNumberIndex.exactMatch(calledNumber);
        return calling.thenCombineAsync(called, (s1, s2) -> intersection(s1, s2));
    }
}
