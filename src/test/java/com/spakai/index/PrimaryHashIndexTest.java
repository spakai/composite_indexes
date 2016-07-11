package com.spakai.index;
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

public class PrimaryHashIndexTest {

    PrimaryHashIndex<String> index;
    ExecutorService pool;
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void setup() {
        pool = Executors.newFixedThreadPool(1);
        index = new PrimaryHashIndex<String>(pool);
        index.load("5","Local");
    }

    @Test
    public void GetAValueFromIndexThatExists() {
        try {
            assertThat(index.exactMatch("5").get().iterator().next(), is("Local"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void GetAValueFromIndexThatDoesNotExist() throws Exception {
        thrown.expect(ExecutionException.class);
        thrown.expectMessage("No match found");

        index.exactMatch("4").get();
    }
}

