package com.spakai.index;


import com.spakai.index.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IndexBenchMarkTest {

    PrimaryHashIndex<String> hashIndex;
    PrimaryTreeIndex<String> treeIndex;
    ExecutorService pool;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void setup() {
        pool = Executors.newFixedThreadPool(3);
        
        hashIndex = new PrimaryHashIndex<>(pool);
        for(Integer i =0 ; i < 1000001 ; i++) {
            hashIndex.load(i.toString(), i.toString());
        }
        
        treeIndex = new PrimaryTreeIndex<>(pool);
        for(Integer i = 0; i < 1000001 ; i++) {
            treeIndex.load(i.toString(), i.toString());
        }
    }

    @Test
    public void performanceTestExactSync() {
        Instant start = Instant.now();
        for(Integer i=0; i< 50001 ; i++) {
            hashIndex.syncExactMatch(i.toString());
        }
        
        Duration between = Duration.between(start, Instant.now());
        System.out.println("Time taken in ms:: " +  between.toMillis());
    }
    
    @Test
    public void performanceTestExactASync() {
        List<CompletableFuture<Set<String>>> hold = new ArrayList<>(50000);
        Instant start = Instant.now();
        for(Integer i=0; i< 50001 ; i++) {
            hold.add(hashIndex.asyncExactMatch(i.toString()));
        }
        
        Duration between = Duration.between(start, Instant.now());
        System.out.println("Time taken in ms:: " +  between.toMillis());
    }
    
      @Test
    public void performanceTestBestSync() {
        Instant start = Instant.now();
        for(Integer i=0; i< 50001 ; i++) {
            treeIndex.syncBestMatch(i.toString());
        }
        
        Duration between = Duration.between(start, Instant.now());
        System.out.println("Time taken in ms:: " +  between.toMillis());
    }
    
}

