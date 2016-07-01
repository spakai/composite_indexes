package com.spakai.composite;

import com.spakai.exception.NoMatchException;
import com.spakai.index.Index;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class TwoKeyExactMatchLookup<V> {

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
    
        public Set<V> intersection(Set<V> s1, Set<V> s2) {
        Set<V> set;
        set = new HashSet<>(s1);
        set.retainAll(s2);

        if (set.isEmpty()) {
            throw new NoMatchException("No match found");
        }

        return set;
    }
}
