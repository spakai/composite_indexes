package com.spakai.composite;

import com.spakai.exception.NoMatchException;

import java.util.HashSet;
import java.util.Set;

public abstract class Composite<K,V> {

    public Set<V> intersection(Set<V> s1, Set<V> s2) {
        Set<V> set = new HashSet<V>(s1);
        set.retainAll(s2);

        if (set.isEmpty()) {
            throw new NoMatchException("No match found");
        }

        return set;
    }
}