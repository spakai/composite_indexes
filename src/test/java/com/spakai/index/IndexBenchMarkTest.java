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

public class IndexBenchMarkTest {

    PrimaryHashIndex<String> index;
    ExecutorService pool;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void setup() {
        pool = Executors.newFixedThreadPool(1);
        index = new PrimaryHashIndex<>(pool);
        for(Integer i =0 ; i < 1000001 ; i++) {
            index.load(i.toString(), i.toString());
        }
        
    }

    @Test
    public void performanceTest() {
        //warm up
        for(Integer i=0; i< 500 ; i++) {
            index.exactMatch(i.toString());
        }
        
        Instant start = Instant.now();
        for(Integer i=0; i< 50001 ; i++) {
            index.exactMatch(i.toString());
        }
        
        Duration between = Duration.between(start, Instant.now());
        System.out.println("Time taken in ms:: " +  between.toMillis());
    }
    
}

