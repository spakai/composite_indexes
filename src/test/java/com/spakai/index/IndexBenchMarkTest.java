package com.spakai.index;


import com.spakai.index.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
    }

    @Test
    public void performanceTestSync() {
        Instant start = Instant.now();
        for(Integer i=0; i< 50001 ; i++) {
            hashIndex.syncExactMatch(i.toString());
        }
        
        Duration between = Duration.between(start, Instant.now());
        System.out.println("Time taken in ms:: " +  between.toMillis());
    }
    
    @Test
    public void performanceTestASync() {
        Instant start = Instant.now();
        for(Integer i=0; i< 50001 ; i++) {
            try {
                hashIndex.asyncExactMatch(i.toString()).get();
            } catch (InterruptedException ex) {
                //
            } catch (ExecutionException ex) {
                //
            }
        }
        
        Duration between = Duration.between(start, Instant.now());
        System.out.println("Time taken in ms:: " +  between.toMillis());
    }
    
}

